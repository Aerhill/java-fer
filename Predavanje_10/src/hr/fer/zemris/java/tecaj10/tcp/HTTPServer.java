package hr.fer.zemris.java.tecaj10.tcp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

public class HTTPServer {

	public static void main(String[] args) throws IOException {
		if(args.length != 1) {
			System.out.println("Očekivao sam port");
			return;
		}
		
		int port = Integer.parseInt(args[0]);
		
		@SuppressWarnings("resource")
		ServerSocket serverSocket = new ServerSocket();
		serverSocket.bind(new InetSocketAddress((InetAddress)null, port));
		
		while(true) {
			Socket toClient = serverSocket.accept();
			serve(toClient);
		}
	}
	
	private static void serve(Socket toClient) throws IOException {
		try {
			InputStream cis = new BufferedInputStream(toClient.getInputStream());
			OutputStream cos = new BufferedOutputStream(toClient.getOutputStream());
			
			byte[] request = readRequest(cis);
			if(request == null) {
				sendError(cos,400,"Bad request");
				return;
			}
			String requestStr = new String(request, StandardCharsets.US_ASCII);
			
			List<String> headers = exractHeaders(requestStr);
			String[] firstLine = headers.isEmpty() ? null : headers.get(0).split(" ");
			if(firstLine == null || firstLine.length != 3) {
				sendError(cos, 400, "Bad request");
				return;
			}
			
			String method = firstLine[0].toUpperCase();
			if(!method.equals("GET")) {
				sendError(cos,405, "Method not allowed");
				return;
			}
			
			String version = firstLine[2].toUpperCase();
			if(!version.equals("HTTP/1.1")){
				sendError(cos,505,"HTTP version not supported");
				return;
			}
			
			String path = firstLine[1];
			
			if(path.equals("/datum")) {
				sendDate(cos);
				return;
			}
			
			composeResponse( cos, path, version, headers.subList(1,  headers.size()));
		} catch (Exception ex ){
			System.out.println("Iznimka: " + ex.getMessage());
		} finally {
			toClient.close();
		}
	}

	private static void composeResponse(OutputStream cos, String path, String version, List<String> headers) throws IOException {
		
		StringBuilder sb = new StringBuilder(
				"<html>\r\n"+
				"	<head>\r\n" +
				"		<title>Ispis zaglavlja</title>\r\n"+
				"	</head>\r\n" + 
				"	<body>\r\n" +
				"		<h1>Informacije o poslanom upit</h1>\r\n"+
				"		<p>Zatražen resurs:	" + path + "</p>\r\n"+
				"		<p>Definirane varijable: </p>\r\n "+
				"		<table border='1'>\r\n");
		for(String redak : headers) {
			int pos = redak.indexOf(':');
			sb.append("		<tr><td>").append(redak.substring(0,pos).trim())
			.append("</td><td>")
			.append(redak.substring(pos+1).trim())
			.append("</td></tr>\r\n");
		}
		sb.append("		</table>\r\n" + "	</body>\r\n" + "</html>\r\n");
		
		byte[] tijeloOdgovora = sb.toString().getBytes(StandardCharsets.UTF_8);
		byte[] zaglavljeOdgovora = (version+" 200 OK\r\n" +
				"Server: Simple java server\r\n" +
				"Content-Type: text/html;charset=UTF-8\r\n"+
				"Content-Length: "+ tijeloOdgovora.length+"\r\n"+
				"Connection: close\r\n"+
				"\r\n"
				).getBytes(StandardCharsets.US_ASCII);
		
		cos.write(zaglavljeOdgovora);
		cos.write(tijeloOdgovora);
		cos.flush();
	}
	
	private static void sendError(OutputStream cos, int statusCode, String statusText) throws IOException {
		cos.write(("HTTP/1.1 " + statusCode + " " + statusText + "\r\n" +
				"Server: Simple java server\r\n" +
				"Content-Type: text/html;charset=UTF-8\r\n"+
				"Content-Length: 0\r\n"+
				"Connection: close\r\n"+
				"\r\n"
				).getBytes(StandardCharsets.US_ASCII));
		cos.flush();
	}
	
	private static void sendDate(OutputStream cos) throws IOException {
		BufferedImage bim = new BufferedImage(400, 200, BufferedImage.TYPE_3BYTE_BGR);
		Graphics g = bim.createGraphics();
		Random rnd = new Random(); 
		g.setColor(new Color(rnd.nextInt(255),rnd.nextInt(255),rnd.nextInt(255)));
		g.fillRect(0, 0, bim.getWidth(), bim.getHeight());
		
		g.setColor(Color.BLACK);
		
		LocalDateTime ldt = LocalDateTime.now();
		String datum = ldt.toString();
		
		g.drawString(datum, 10, bim.getHeight()-10);
		g.dispose();
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ImageIO.write(bim, "png", bos);
		
		byte[] slika = bos.toByteArray();
		
		
		cos.write(("HTTP/1.1 200 OK\r\n" +
				"Server: Simple java server\r\n" +
				"Content-Type: image/png\r\n"+
				"Content-Length: "+ slika.length + "\r\n"+
				"Connection: close\r\n"+
				"\r\n"
				).getBytes(StandardCharsets.US_ASCII));
		cos.write(slika);
		cos.flush();
	}
	
	private static List<String> exractHeaders(String requestHeader) {
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
	
	private static byte[] readRequest(InputStream is) throws IOException {
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		int state = 0;
l:		while (true) {
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
