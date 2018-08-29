package it.unifi.rc.httpserver.m5655418;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import it.unifi.rc.httpserver.HTTPHandler;
import it.unifi.rc.httpserver.HTTPProtocolException;
import it.unifi.rc.httpserver.HTTPReply;
import it.unifi.rc.httpserver.HTTPRequest;

/**
 * Class that implements {@link HTTPHandler}
 * 
 * @author Daniel Zanchi, 5655418
 *
 */
public class MyHTTPHandler1_0 implements HTTPHandler {

	protected File root;
	protected String host;
	
	private String supportedVersion = "HTTP/1.0";
	private String[] availableMethods = {"HEAD", "GET", "POST"};
	
	/**
	 * Constructor that creates the handler with the {@link root}, host will be null
	 * 
	 * @param root to assign to the current handler
	 */
	public MyHTTPHandler1_0(File root) {
		this.root = root;
	}
	
	/**
	 * Constructor that creates the handler with the {@link host} and {@link root}.
	 * 
	 * @param host to assign to the current handler
	 * @param root to assign to the current handler
	 */
	public MyHTTPHandler1_0(String host, File root) {
		this.root = root;
		this.host = host;
	}
	
	/**
	 * Handles the {@code request} checking for an eventual not supported request (method or HTTP version) and it returns a {@link HTTPReply} with a result.
	 * 
	 * @param request to create a response
	 * @return HTTPReply
	 */
	@Override
	public HTTPReply handle(HTTPRequest request) {
		if (!(Arrays.asList(getAvailableMethod()).contains(request.getMethod()))) {
			return new MyHTTPReply(new MyHTTPProtocolException(501, "Not Implemented", "The method" + request.getMethod() + "is not implemented from the handler used."), getSupportedVersion());
		}
		if (!(supportedVersion.equals(request.getVersion()))) {
			return new MyHTTPReply(new MyHTTPProtocolException(505, "HTTP version not supported", request.getVersion() + " not supported by this handler"),  getSupportedVersion());
		}
		if (isHostOk(request) == false) {
			return null;
		}
		try {
			switch (request.getMethod()) {
			case "HEAD":
				return runHEADMethod(request.getPath());
			case "GET":
				return runGETMethod(request.getPath());
			case "POST":
				return runPOSTMethod(request.getPath(), request.getEntityBody());
			default:
				throw new MyHTTPProtocolException(501, "Not Implemented", "The method" + request.getMethod() + "is not implemented from the handler used.");
			}
		} catch (HTTPProtocolException ex) {
			return new MyHTTPReply((MyHTTPProtocolException)ex, getSupportedVersion());
		}
	}


	/**
	 * Check if the host of the client is same to the one indicated in the request
	 * 
	 * @param request
	 * @return false if should not serve the request, true if it should.
	 */
	protected boolean isHostOk(HTTPRequest request) {
		if (host!=null) {
			if (!(request.getParameters().get("Host").equals(host))) {
				return false;
			}
			if (request.getParameters().get("Host")==null) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Set the Host to the {@code Host} passed as parameter
	 * 
	 * @param host to assign to the current handler
	 */
	public void setHost(String host) {
		this.host = host;
	}
	
	/**
	 * 
	 * @return the supported version by the {@link MyHTTPHandler1_0}
	 */
	private String getSupportedVersion() {
		return supportedVersion;
	}
	
	/**
	 * 
	 * @return an array of supported methods by the {@link MyHTTPHandler1_0}
	 */
	private String[] getAvailableMethod() {
		return availableMethods;
	}

	/**
	 * Run the HEAD method
	 * 
	 * @param path
	 * @return HTTPReply
	 * @throws HTTPProtocolException
	 */
	protected HTTPReply runHEADMethod(String path) throws HTTPProtocolException {
		return new MyHTTPReply("HTTP/1.0 200 OK", getHeaders(path), "");
	}
	
	/**
	 * Run the POST method
	 * 
	 * @param path
	 * @param entityBody
	 * @return HTTPReply
	 * @throws HTTPProtocolException
	 */
	protected HTTPReply runPOSTMethod(String path, String entityBody) throws HTTPProtocolException {
		try {
			OutputStream os = new FileOutputStream(new File(root.getAbsolutePath() + path));
			os.write(entityBody.getBytes());
			os.close();
			return new MyHTTPReply("HTTP/1.0 204 No Content", MyHTTPReply.getStdHeaders().toString(), "");
		} catch (IOException e) {
			return new MyHTTPReply("HTTP/1.0 404 Not Found", MyHTTPReply.getStdHeaders().toString(), e.getStackTrace().toString());
		}
	}
	
	/**
	 * Run the GET method
	 * 
	 * @param path
	 * @return HTTPReply
	 * @throws HTTPProtocolException
	 */
	protected HTTPReply runGETMethod(String path) throws HTTPProtocolException {
		return new MyHTTPReply("HTTP/1.0 200 OK", getHeaders(path), getResource(path));
	}
	
	/**
	 * Create a {@link String} getting info from the {@code path}
	 * 
	 * @param path
	 * @return {@link String} with headers like "Last-Modified"
	 */
	private String getHeaders(String path) {
		StringBuilder string = MyHTTPReply.getStdHeaders();
		if (path!=null) {
			string.append("Last-Modified: ");
			File file = new File(root.getAbsolutePath() + path);
			Date date = new Date(file.lastModified());
			String dateFormatted = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").format(date);
			string.append(dateFormatted);
			string.append("\r\n");
		}
		return string.toString();
	}
	
	/**
	 * 
	 * @param path of the rosource
	 * @return a {@link String} with all the resources for the executed method.
	 * @throws MyHTTPProtocolException
	 */
	private String getResource(String path) throws MyHTTPProtocolException {
		StringBuilder stringBuilder = new StringBuilder();
		String resources;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(root.getAbsolutePath() + path));
			char [] buffer = new char[1024];
			int current;
			while ((current = reader.read(buffer)) != -1) {
				stringBuilder.append(buffer, 0, current);
			}
			resources = stringBuilder.toString();
			reader.close();
		} catch (IOException e) {
			throw new MyHTTPProtocolException(404, "Not Found", path + "was not found");
		} catch (NullPointerException e) {
			throw new MyHTTPProtocolException(500, "Internal Server Error", path + Arrays.toString(e.getStackTrace()));
		}
		return resources;
	}
}
