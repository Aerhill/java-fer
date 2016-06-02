package hr.fer.zemris.java.webserver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * A web server is a computer system that processes requests via HTTP, the basic
 * network protocol used to distribute information on the World Wide Web. The
 * term can refer to the entire system, or specifically to the software that
 * accepts and supervises the HTTP requests. This HTTP server uses TCP transfer
 * protocol that works in a way that it transfers bytes between server and
 * client using {@link Socket}.
 * 
 * @author Ante Spajic
 */
public class SmartHttpServer {
	
	/**
	 * Starts the server and waits for 'stop' string to stop the server.
	 *
	 * @param args path to server properties file
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
            throw new IllegalArgumentException("Must provide a single argument: path to server.properties.");
        }

        SmartHttpServer server = new SmartHttpServer(args[0]);
        server.start();
        Scanner sc = new Scanner(System.in);
		while(true){
        	String l = sc.nextLine();
        	if(l.equals("stop")){
        		break;
        	}
        }
        server.stop();
		sc.close();
	}
	
	/** The disallowed characters for cookie name and value. */
	private final Pattern cookiePattern = Pattern.compile("[\\[\\]\\(\\)=,\"\\/?@:;]+");
	
	/** The server address. */
	private String address;
	
	/** The server port. */
	private int port;
	
	/** The worker threads. */
	private int workerThreads;
	
	/** The session timeout. */
	private int sessionTimeout;
	
	/** The mime types map. */
	private Map<String, String> mimeTypes = new HashMap<String, String>();
	
	/** The server thread. */
	private ServerThread serverThread;
	
	/** The client thread pool of this server. */
	private ExecutorService threadPool;
	
	/** The document root of this server. */
	private Path documentRoot;

	/** The map of webworkers. */
	private Map<String,IWebWorker> workersMap;
	
	/** Map of active sessions of this erver. */
	private Map<String, SessionMapEntry> sessions = new HashMap<String, SessionMapEntry>();
	
	/** The session generator. */
	private Random sessionRandom = new Random();
	
	/** The session collector of this server. */
	private SessionCollector sc = new SessionCollector();
	
	/**
	 * SessionMapEntry class represents a data binded to session.
	 */
	private static class SessionMapEntry {
		
		/** The sessionID. */
		String sid;
		
		/** Time when this session expires. */
		long validUntil;
		
		/** Parameters of this session */
		Map<String,String> map;
	}
	
	/**
	 * Initializes a new smart http server.
	 *
	 * @param configFileName the server config file path
	 */
	public SmartHttpServer(String configFileName) {
		Properties properties = new Properties();
		try {
			properties.load(Files.newInputStream(Paths.get(configFileName)));
		} catch (IOException e) {
			throw new RuntimeException("Couldn't load config file");
		}
		loadProperties(properties);
	}

	/**
	 * loads server properties from server config file .
	 *
	 * @param properties Propeties loader
	 */
	private void loadProperties(Properties properties) {
		address = properties.getProperty(PropertyKeys.ADDRESS);
		port = Integer.parseInt(properties.getProperty(PropertyKeys.PORT));
		workerThreads = Integer.parseInt(properties.getProperty(PropertyKeys.WORKER_THREADS));
		sessionTimeout = Integer.parseInt(properties.getProperty(PropertyKeys.SESSION_TIMEOUT));
		documentRoot = Paths.get(properties.getProperty(PropertyKeys.DOCUMENT_ROOT));
		
		String workersConfigPath = properties.getProperty(PropertyKeys.WORKERS_CONFIG);
		String mimeTypesPath = properties.getProperty(PropertyKeys.MIME_CONFIG);
		
		loadMimeTypes(mimeTypesPath);
		loadWorkers(workersConfigPath);
	}

	/**
	 * Reads mime types config file and puts them into a map.
	 *
	 * @param mimeTypesPath path to config file for mime types
	 */
	private void loadMimeTypes(String mimeTypesPath) {
		Properties properties = new Properties();
		try {
			properties.load(Files.newInputStream(Paths.get(mimeTypesPath)));
			for (Object key : properties.keySet()) {
				mimeTypes.put(key.toString(), properties.get(key).toString());
			}
		} catch (IOException e) {
			throw new RuntimeException("Couldn't load mime config file");
		}
	}

	/**
	 * Reads workers config file and puts them into a map.
	 *
	 * @param workersConfigPath path to config file for workers
	 */
	private void loadWorkers(String workersConfigPath) {
		Properties properties = new Properties();
		workersMap = new HashMap<>();
		try {
			properties.load(Files.newInputStream(Paths.get(workersConfigPath)));
			for (Object key : properties.keySet()) {
				String path = key.toString();
				String fqcn = properties.get(key).toString();
				Class<?> referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
				Object newObject = referenceToClass.newInstance();
				IWebWorker iww = (IWebWorker)newObject;
				workersMap.put(path, iww);
			}
		} catch (IOException e) {
			throw new RuntimeException("Couldn't load workers config file");
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Starts the http smart server.
	 */
	protected synchronized void start() {
		threadPool = Executors.newFixedThreadPool(workerThreads);
		if (serverThread == null) {
			serverThread = new ServerThread();
			serverThread.setDaemon(true);
			sc.setDaemon(true);
		}
		if (!serverThread.isAlive()) {
			serverThread.start();
			sc.start(); 
		}
	}

	/**
	 * Stops the server.
	 */
	protected synchronized void stop() {
		serverThread.terminate();
		threadPool.shutdown();
	}

	/**
	 * ServerThread represents a thread that waits for requests and serves
	 * proper responses.
	 *
	 * @author Ante Spajic
	 */
	protected class ServerThread extends Thread {
		
		/**  flag used to shut down the server thread. */
		private volatile boolean running = true;
		
		/**
		 * Terminates the server thread.
		 */
		public void terminate() {
			running = false;
		}
		
		@Override
		public void run() {
			try {
				ServerSocket serverSocket = new ServerSocket();
				serverSocket.bind(new InetSocketAddress(address, port));
				serverSocket.setSoTimeout(2500);
				while (running) {
					try {
						Socket client = serverSocket.accept();
						ClientWorker cw = new ClientWorker(client);
						threadPool.submit(cw);
					} catch (SocketTimeoutException ignore) {}
				}
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * This thread periodically iterates through all sessions and deletes
	 * timed-out sessions in order to avoid excessive memory consumption by
	 * expired sessions.
	 * 
	 * @author Ante Spajic
	 *
	 */
	private class SessionCollector extends Thread {
		/** The Constant PERIOD represents a period after which this thread scans for expired sessions. */
		// check dead sessions every 5 minutes
		private static final int PERIOD = 300000;
		
		@Override
		public void run() {
			while(true) {
				try {
					Thread.sleep(PERIOD);
					synchronized(sessions){
						Iterator<Map.Entry<String,SmartHttpServer.SessionMapEntry>> iter = sessions.entrySet().iterator();
						while(iter.hasNext()) {
							Map.Entry<String,SmartHttpServer.SessionMapEntry> entry = iter.next();
							if (entry.getValue().validUntil < (System.currentTimeMillis()/1000)) {
								iter.remove();
							}
						}
					}
				} catch (InterruptedException yo) {}
			}
		}
	}
	
	/**
	 * Client worker thread is dispatched every time a new request is sent to a
	 * server, server then forwards the request to client thread to serve it.
	 * 
	 * @author Ante Spajic
	 */
	private class ClientWorker implements Runnable {
		
		/** The cleint socket. */
		private Socket csocket;
		
		/** The input stream from client. */
		private PushbackInputStream istream;
		
		/** The ouput stream to client. */
		private OutputStream ostream;
		
		/** The version of HTTP. */
		private String version;
		
		/** The method of request. */
		private String method;
		
		/** The parameters map. */
		private Map<String, String> params = new HashMap<String, String>();
		
		/** Permanent parameters. */
		private Map<String, String> permParams = null;
		
		/** The output cookies to be set to browser. */
		private List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();
		
		/** The sessionID. */
		private String SID;
		
		/** The host address. */
		private String hostAddress;
		
		/**
		 * Instantiates a new client worker.
		 *
		 * @param csocket the client socket
		 */
		public ClientWorker(Socket csocket) {
			super();
			this.csocket = csocket;
		}

		@Override
		public void run() {
			try {
				istream = new PushbackInputStream(csocket.getInputStream());
				ostream = csocket.getOutputStream();
				
				List<String> request = readRequest();
				if (request == null || request.size() < 1) {
					sendError(400,"Bad request");
					return;
				}
				String firstLine = request.get(0);
				String[] info = firstLine.split(" ");
				if(checkInvalidHeader(info)) return;
				
				String[] reqPath = info[1].split("\\?");
				String path = reqPath[0].substring(1);
				if(reqPath.length > 1) {
					String paramString = reqPath[1];
					parseParameters(paramString);
				}
				Path requestedPath = documentRoot.resolve(path);
				String mimeType = "application/octet-stream";
				String rp = requestedPath.toString();
				String ext = rp.substring(rp.lastIndexOf('.')+1, rp.length());
				
				checkForAddress(request);
				checkForCookies(request,path);
				RequestContext rc = new RequestContext(ostream, params, permParams, outputCookies);
				if ((path).startsWith("ext/")){
					// lazy loading
					String klas = path.substring("ext/".length());
					String fqcn = "hr.fer.zemris.java.webserver.workers." + klas;
					Class<?> referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
					Object newObject = referenceToClass.newInstance();
					IWebWorker iww = (IWebWorker)newObject;
					iww.processRequest(rc);
				}else if (workersMap.containsKey("/"+path)) {
					// preloaded maps, slower server start
					workersMap.get("/"+path).processRequest(rc);
				} else if (!requestedPath.startsWith(documentRoot)) {
					sendError(403, "Forbidden!");
					return;
				} else if (!Files.exists(requestedPath) || !Files.isReadable(requestedPath)) {
					sendError(404, "File not found");
					return;
				} else if (ext.equals("smscr")) {
					String documentBody = new String(Files.readAllBytes(requestedPath));
					new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(), rc).execute();
				} else {
					String mime = mimeTypes.get(ext);
					mimeType = mime == null ? mimeType : mime;
					byte[] data = Files.readAllBytes(requestedPath);
					rc.addAdditionalHeader("Content-Length: " + data.length);
					rc.setMimeType(mimeType);
					rc.setStatusCode(200);
					rc.write(data);
				}
			} catch (Exception e ){
				e.printStackTrace();
			} finally {
				try { csocket.close(); } catch (IOException e) {e.printStackTrace();}
			}
		}

		/**
		 * Scans through http header for host address
		 * 
		 * @param request htpp request headers
		 */
		private void checkForAddress(List<String> request) {
			for (String line : request) {
				if(!line.startsWith("Host:")) continue;
				hostAddress = line.substring("Host:".length(), line.lastIndexOf(":")).trim();
				break;
			}
		}

		/**
		 * Checks if a first line of header is correct, a correct header would
		 * have GET method, HTTP/1.1 version and a correct path within a webroot
		 * server.
		 *
		 * @param info
		 *            first line separated in string array
		 * @return true if any part of header is invalid, false otherwise
		 * @throws IOException
		 *             Signals that an I/O exception has occurred.
		 */
		private boolean checkInvalidHeader(String[] info) throws IOException {
			if(info.length != 3) {
				sendError(400, "Bad request");
				return true;
			}
			method = info[0].toUpperCase();
			if(!method.equals("GET")) {
				sendError(405, "Method not allowed");
				return true;
			}
			version = info[2].toUpperCase();
			if(!version.equals("HTTP/1.1")){
				sendError(505, "HTTP version not supported");
				return true;
			}
			return false;
		}

		/**
		 * Method that checks if any cookies have been set by the request, it
		 * scans them and stores them for further use.
		 *
		 * @param request
		 *            the http request header
		 * @param path
		 *            the path header has provided
		 */
		private void checkForCookies(List<String> request, String path) {
			String sidCandidate = "";
			for (String headerLine : request) {
				if(!headerLine.startsWith("Cookie:")) continue;
				headerLine = headerLine.substring("Cookie:".length()).trim();
				String[] cewkies = headerLine.split(";");
				for (int i = 0; i < cewkies.length; i++) {
					String[] pair = cewkies[i].split("=");
					if (pair.length != 2) continue;
					String name = pair[0].trim();
					String value = pair[1].trim();
					if (invalidCookie(name,value)) continue;
					if (name.equals("sid")) {
						sidCandidate = value;
					} else {
						RCCookie cook = new RCCookie(name, value, null, hostAddress, "/"+path);
						outputCookies.add(cook);
					}
				}
			}
			checkSession(sidCandidate);
		}

		/**
		 * Method that checks if there is a session associated with a sessionID,
		 * provided within cookies, still active, if not a new sessionID is
		 * provided or current is prolonged for a preset timeout.
		 *
		 * @param sidCandidate
		 *            the sessionID candidate obtained from cookies header line
		 */
		private synchronized void checkSession(String sidCandidate) {
			if (sidCandidate.isEmpty() || !sessions.containsKey(sidCandidate)){
				String newSID = SID = sidGenerator();
				createEntry(newSID);
			} else {
				SessionMapEntry entry = sessions.get(sidCandidate);
				if (entry.validUntil < (System.currentTimeMillis() /1000)){
					sessions.remove(entry);
					String newSID = SID = sidGenerator();
					createEntry(newSID);
				} else {
					SID = sidCandidate;
					entry.validUntil = (System.currentTimeMillis()/1000) + sessionTimeout;
				}
			}
			permParams = sessions.get(SID).map;
		}

		/**
		 * When a new session is created a proper entry that will hold relevant
		 * information about that session is also created by this method.
		 *
		 * @param newSessionID
		 *            the session id associated with entry
		 */
		private void createEntry(String newSessionID) {
			SessionMapEntry ret = new SessionMapEntry();
			ret.sid = newSessionID;
			ret.validUntil = System.currentTimeMillis() / 1000 + sessionTimeout;
			ret.map = new ConcurrentHashMap<>();
			ret.map.put("sid", ret.sid);
			for (RCCookie currentCookie : outputCookies) {
				ret.map.put(currentCookie.getName(), currentCookie.getValue());
			}
			sessions.put(newSessionID, ret);
			// Microsoft Edge/IE browsers for some reason dont work with domain "localhost"
			// if a domain is null browser interprets it as loopback and works on all browsers
			// just to clarify that i had this "microsoft" bug in mind but I left it as noted in
			// assignment, if its accessed from localhost then domain name is localhost and not null
			// hostAddress = hostAddress.equals("localhost")? null : hostAddress;
			outputCookies.add(new RCCookie("sid", newSessionID, null, hostAddress, "/",true));
		}

		/**
		 * This method generates a new session ID composed of 20 random upper
		 * case characters.
		 *
		 * @return a new sessionID
		 */
		private String sidGenerator() {
			char[] rand = new char[20];
			for (int i = 0; i < rand.length; i++) {
				rand[i] = (char) (65 + sessionRandom.nextInt(26));
			}
			return new String(rand);
		}
		
		/**
		 * Method that checks if a cookie name or value has invalid characters
		 * inside.
		 *
		 * @param name
		 *            cookie name
		 * @param value
		 *            cookie value
		 * @return true, if a cookie is invalid
		 */
		private boolean invalidCookie(String name, String value) {
			Matcher matcher = cookiePattern.matcher(name);
			boolean invalid = false;
			if(matcher.find()) invalid = true;
			matcher = cookiePattern.matcher(value);
			if(matcher.find()) invalid = true;
			return invalid;
		}

		/**
		 * Method that composes and sends an error response.
		 *
		 * @param statusCode the status code of error
		 * @param statusText the status text
		 * @throws IOException Signals that an I/O exception has occurred.
		 */
		private void sendError(int statusCode, String statusText) throws IOException {
			ostream.write(("HTTP/1.1 " + statusCode + " " + statusText + "\r\n" +
					"Server: Smart HTTP server\r\n" +
					"Content-Type: text/html;charset=UTF-8\r\n"+
					"Content-Length: 0\r\n"+
					"Connection: close\r\n"+
					"\r\n"
					).getBytes(StandardCharsets.US_ASCII));
			ostream.flush();
		}

		/**
		 * Method that parses the request parameters.
		 *
		 * @param paramString the parameters string obtained from request
		 */
		private void parseParameters(String paramString) {
			String[] groups = paramString.split("&");
			for (String group : groups) {
				String[] nameVal = group.trim().split("=");
				params.put(nameVal[0].trim(),nameVal[1].trim());
			}
		}

		/**
		 * Method that reads a http request header and returns all lines in a list.
		 *
		 * @return the list with all header lines
		 * @throws IOException Signals that an I/O exception has occurred.
		 */
		private List<String> readRequest() throws IOException {
			byte[] request = readRequest(istream);
			if(request == null) {
				sendError(400,"Bad request");
				return null;
			}
			String requestHeader = new String(request,StandardCharsets.US_ASCII);
			List<String> headers = new ArrayList<>();
			String currentLine = null;
			for(String s : requestHeader.split("\n")) {
				if(s.isEmpty()) break;
				char c = s.charAt(0);
				if (c==9 || c==32) {
					currentLine += s;
				} else {
					if(currentLine != null) {
						headers.add(currentLine);
					}
					currentLine = s;
				}
			}
			if(!currentLine.isEmpty()) {
				headers.add(currentLine);
			}
			return headers;
		}

		/**
		 * This method reads all bytes from a request until it reaches a '\r\n\r\n'.
		 *
		 * @param is the input stream to read the request from
		 * @return the byte[] array of all the read bytes
		 * @throws IOException Signals that an I/O exception has occurred.
		 */
		private byte[] readRequest(PushbackInputStream is) throws IOException {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int state = 0;
l:			while (true) {
				int b = is.read();
				if(b == -1) return null;
				if(b != 13){
					bos.write(b);
				}
				switch (state) {
				case 0:
					if(b==13) { state=1; } else if(b==10) state=4;
					break;
				case 1:
					if(b==10) { state=2; } else state=0;
					break;
				case 2:
					if(b==13) { state=3; } else state=0;
					break;
				case 3:
					if(b==10) { break l; } else state=0;
					break;
				case 4:
					if(b==10) { break l; } else state=0;
					break;
				}
			}
			return bos.toByteArray();
		}
	}
}
