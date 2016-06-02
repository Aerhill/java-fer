package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Encapsulates information about an HTTP request that matches a defined route.
 * The RequestContext class contains information about the HTTP request in the
 * HttpContext property.
 * 
 * @author Ante Spajic
 */
public class RequestContext {

	/**
	 * The Class RCCookie represents a request context cookie.
	 */
	public static class RCCookie {
		
		/** The name of cookie */
		private String name;
		
		/** The value of cookie */
		private String value;
		
		/** The domain where the cookie is used. */
		private String domain;
		
		/** The path where this cookie is used */
		private String path;
		
		/** The max age of a cookie, expires after the maxage */
		private Integer maxAge;
		
		/** HttpOnly flag, true for httpOnly cookies */
		private boolean httpOnly;
		
		/**
		 * Instantiates a new normal RC cookie.
		 *
		 * @param name cookie name
		 * @param value cookie value
		 * @param maxAge cookie expiry time
		 * @param domain cookie domain
		 * @param path cookie path
		 */
		public RCCookie(String name, String value, Integer maxAge, String domain, String path) {
			this(name,value,maxAge,domain,path,false);
		}

		/**
		 * Creates a new RCCookie with preset httpOnly flag
		 * 
		 * @param name cookie name
		 * @param value cookie value
		 * @param maxAge cookie expiry time
		 * @param domain cookie domain
		 * @param path cookie path
		 * @param httpOnly true if a cookie is a httponly
		 */
		public RCCookie(String name, String value, Integer maxAge, String domain, String path, boolean httpOnly) {
			if (name == null || value == null) {
				throw new IllegalArgumentException("Invalid cookie argument");
			}
			this.name = name;
			this.value = value;
			this.maxAge = maxAge;
			this.domain = domain;
			this.path = path;
			this.httpOnly = httpOnly;
		}

		public String getName() {
			return name;
		}

		public String getValue() {
			return value;
		}
		
		
	}

	/** The output stream. */
	private OutputStream outputStream;
	
	/** The charset for request. */
	private Charset charset;
	
	/** The encoding for request. */
	private String encoding = "UTF-8";
	
	/** The status code. */
	private int statusCode = 200;
	
	/** The status text. */
	private String statusText = "OK";
	
	/** The mime type. */
	private String mimeType = "text/html";
	
	/** The parameters of this request. */
	private final Map<String, String> parameters;
	
	/** The temporary parameters of this request. */
	private Map<String, String> temporaryParameters;
	
	/** The persistent parameters of this request. */
	private Map<String, String> persistentParameters;
	
	/** The output cookies. */
	private List<RCCookie> outputCookies;
	
	/** The header generated. */
	private boolean headerGenerated;
	
	/** The additional headers. */
	private List<String> additionalHeaders;
	
	/**
	 * Instantiates a new request context.
	 *
	 * @param outputStream output stream to write the context
	 * @param parameters the parameters
	 * @param persistentParameters the persistent parameters
	 * @param outputCookies the output cookies
	 */
	public RequestContext(
			OutputStream outputStream,
			Map<String,String> parameters,
			Map<String,String> persistentParameters,
			List<RCCookie> outputCookies) {
		if (outputStream == null) {
			throw new IllegalArgumentException("Output stream must not be null");
		}
		this.outputStream = outputStream;
		this.parameters = parameters == null ? new HashMap<>(): parameters;
		this.persistentParameters = persistentParameters == null ? new HashMap<>(): persistentParameters;
		this.outputCookies = outputCookies == null ? new ArrayList<>(): outputCookies;
	}

	/**
	 * Sets encoding of this request context.
	 * 
	 * @param encoding encoding to be set
	 */
	public void setEncoding(String encoding) {
		if (headerGenerated) {
			throw new RuntimeException("Header has already been generated");
		}
		this.encoding = encoding;
	}

	/**
	 * Sets status code for this request context.
	 * 
	 * @param statusCode HTTP status code to be set
	 */
	public void setStatusCode(int statusCode) {
		if (headerGenerated) {
			throw new RuntimeException("Header has already been generated");
		}
		this.statusCode = statusCode;
	}

	/**
	 * Sets status text of this request.
	 * 
	 * @param statusText status text for this request
	 */
	public void setStatusText(String statusText) {
		if (headerGenerated) {
			throw new RuntimeException("Header has already been generated");
		}
		this.statusText = statusText;
	}

	/**
	 * Sets a mime type for this request.
	 * 
	 * @param mimeType mimetype to be set
	 */
	public void setMimeType(String mimeType) {
		if (headerGenerated) {
			throw new RuntimeException("Header has already been generated");
		}
		this.mimeType = mimeType;
	}
	
	/**
	 * Retrieves value from parameters map or null if no assiciation exists.
	 * 
	 * @param name name of the parameter
	 * @return parameter value
	 */
	public String getParameter(String name) {
		return parameters.get(name);
	}
	
	/**
	 * Retrieves names of all parameters in parameters map.
	 * 
	 * @return unmodifiable set of parameter names
	 */
	public Set<String> getParameterNames() {
		return Collections.unmodifiableSet(parameters.keySet());
	}
	
	/**
	 * Retrieves value from persistent parameters map or null if no association exists.
	 * 
	 * @param name name of the paramter
	 * @return parameter value
	 */
	public String getPersistentParameter(String name) {
		return persistentParameters.get(name);
	}
	
	/**
	 * Retrieves names of all persistent parameters.
	 * 
	 * @return unmodifiable set of persistent parameter names
	 */
	public Set<String> getPersistentParameterNames() {
		return Collections.unmodifiableSet(persistentParameters.keySet());
	}
	
	/**
	 * Stores a value to persistent parameters map
	 * 
	 * @param name name of the parameter
	 * @param value value of the parameter
	 */
	public void setPersistentParameter(String name, String value) {
		persistentParameters.put(name, value);
	}
	
	/**
	 * Remove a persistent parameter with associated name and value
	 * 
	 * @param name the name of the parameter
	 */
	public void removePersistentParameter(String name) {
		persistentParameters.remove(name);
	}
	
	/**
	 * Retrieves the temporary parameter associated with provided name.
	 *
	 * @param name the name of the temporary parameter
	 * @return the value associated with provided name
	 */
	public String getTemporaryParameter(String name) {
		return temporaryParameters.get(name);
	}
	
	/**
	 * Retrieves names of all temporary parameters.
	 * 
	 * @return unmodifiable set of temporary parameter names
	 */
	public Set<String> getTemporaryParameterNames() {
		return Collections.unmodifiableSet(temporaryParameters.keySet());
	}
	
	/**
	 * Stores a value to temporary parameters map
	 *
	 * @param name the name of the parameter
	 * @param value the value associated with that parameter
	 */ 
	public void setTemporaryParameter(String name, String value) {
		if (temporaryParameters == null) {
			this.temporaryParameters = new HashMap<>();
		}
		temporaryParameters.put(name, value);
	}
	
	/**
	 * Removes a value from temporary parameters associated with provided name.
	 * 
	 * @param name name of the parameter
	 */
	public void removeTemporaryParameter(String name) {
		temporaryParameters.remove(name);
	}
	
	public RequestContext write(byte[] data) throws IOException {
		if (!headerGenerated) {
			charset = Charset.forName(encoding);
			generateHeader();
		}
		outputStream.write(data);
		outputStream.flush();
		return this;
	}
	
	/**
	 * Method that generates a header
	 * 
	 * @throws IOException if an I/O error occurs 
	 */
	private void generateHeader() throws IOException {
		outputStream.write(("HTTP/1.1 " + statusCode + " " + statusText + "\r\n" +
				"Content-Type:" + mimeType + (mimeType.trim().startsWith("text/") ? "; charset=UTF-8": "") + "\r\n"+
				(additionalHeaders!=null ? additionalHeaders(): "") +
				(!outputCookies.isEmpty() ? cookiesToStr() : "") +
				"\r\n"
				).getBytes(StandardCharsets.ISO_8859_1));
		outputStream.flush();
		headerGenerated = true;
	}

	private String additionalHeaders() {
		StringBuilder sb = new StringBuilder();
		for (String header : additionalHeaders) {
			sb.append(header).append("\r\n");
		}
		return sb.toString();
	}

	/**
	 * generates a string of output cookies to be written on output stream
	 * 
	 * @return cookies to be written
	 */
	private String cookiesToStr() {
		StringBuilder sb = new StringBuilder();
		for (RCCookie r : outputCookies) {
			sb.append("Set-Cookie: ")
			.append(r.name).append("=").append(r.value);
			if(r.domain != null) {
				sb.append("; Domain=").append(r.domain);
			}
			if (r.path != null) {
				sb.append("; Path=").append(r.path);
			}
			if (r.maxAge != null) {
				sb.append("; Max-Age=").append(r.maxAge.toString());
			}
			if (r.httpOnly) {
				sb.append("; HttpOnly");
			}
			sb.append("\r\n");
		}
		return sb.toString();
	}

	/**
	 * Writes a provided text to ouputstream
	 * 
	 * @param text text to be written by this request
	 * @return this request context
	 * @throws IOException
	 */
	public RequestContext write(String text) throws IOException {
		charset = Charset.forName(encoding);
		return write(text.getBytes(charset));
	}

	/**
	 * Add a cookie to this request context
	 * 
	 * @param rcCookie cookie to be added
	 */
	public void addRCCookie(RCCookie rcCookie) {
		outputCookies.add(rcCookie);
	}
	
	/**
	 * Adds the additional header.
	 *
	 * @param header the header
	 */
	public void addAdditionalHeader(String header) {
		if (headerGenerated) {
			throw new RuntimeException("Header has already been generated");
		}
		if (additionalHeaders == null) {
			additionalHeaders = new ArrayList<>();
		}
		additionalHeaders.add(header);
	}
 }
