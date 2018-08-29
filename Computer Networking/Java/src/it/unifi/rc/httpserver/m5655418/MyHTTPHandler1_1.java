package it.unifi.rc.httpserver.m5655418;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Map;

import it.unifi.rc.httpserver.HTTPProtocolException;
import it.unifi.rc.httpserver.HTTPReply;
import it.unifi.rc.httpserver.HTTPRequest;

/**
 * Class that extends {@link MyHTTPHandler1_0}
 * 
 * @author Daniel Zanchi, 5655418
 *
 */
public class MyHTTPHandler1_1 extends MyHTTPHandler1_0 {

	private String supportedVersion = "HTTP/1.0 and HTTP/1.1";
	private String[] availableMethods = { "HEAD", "GET", "POST", "PUT", "DELETE", "CONNECT", "OPTIONS", "TRACE" };

	
	/**
	 * Constructor that creates the handler with the {@link root} as parameter, host will be null recalling the superclass {@link MyHTTPHandler1_0} constructor
	 * 
	 * @param root to assign to the current handler
	 */
	public MyHTTPHandler1_1(File root) {
		super(root);
	}

	/**
	 * Constructor that creates the handler with the {@link host} and {@link root} as parameters recalling the superclass {@link MyHTTPHandler1_0} constructor
	 * 
	 * @param host to assign to the current handler
	 * @param root to assign to the current handler
	 */
	public MyHTTPHandler1_1(String host, File root) {
		super(host, root);
	}

	/**
	 * Constructor that creates the handler with the {@link host} and {@link root} as parameters
	 * 
	 * @return the supported version by the {@link MyHTTPHandler1_1}
	 */
	private String getSupportedVersion() {
		return supportedVersion;
	}

	/**
	 * 
	 * @return an array of supported methods by the {@link MyHTTPHandler1_1}
	 */
	private String[] getAvailableMethod() {
		return availableMethods;
	}

	/**
	 * Handles the {@code request} checking for an eventual not supported request (method or http version)
	 * 
	 * @param request to create a response 
	 * @return HTTPReply
	 */
	public HTTPReply handle(HTTPRequest request) {
		if (!(Arrays.asList(getAvailableMethod()).contains(request.getMethod()))) {
			return new MyHTTPReply(new MyHTTPProtocolException(501, "Not Implemented", "The method" + request.getMethod() + "is not implemented from the handler used."), getSupportedVersion());
		}
		if (!(getSupportedVersion().contains(request.getVersion()))) {
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
			case "PUT":
				return runPUTMethod(request.getPath(), request.getEntityBody());
			case "DELETE":
				return runDELETEMethod(request.getPath(), request.getParameters());
			}
			throw new MyHTTPProtocolException(501, "Not Implemented", "The medoth" + request.getMethod() + "is not implemented from the handler used");
		} catch (HTTPProtocolException ex) {
			return new MyHTTPReply((MyHTTPProtocolException)ex, getSupportedVersion());
		}
	}
	
	/**
	 * Run the PUT method
	 * 
	 * @param path
	 * @param entityBody
	 * @return HTTPReply
	 * @throws HTTPProtocolException
	 */
	private HTTPReply runPUTMethod(String path, String entityBody) throws HTTPProtocolException {
		StringBuilder stringBuilder = getHeaders(path);
		HTTPReply reply;
		if (new File(root.getAbsolutePath() + path).exists()) {
			reply = new MyHTTPReply("HTTP/1.1 204 No Content", stringBuilder.toString(), "");
		}
		else {
			reply = new MyHTTPReply("HTTP/1.1 201 Created", stringBuilder.toString(), "<html><body><p>Hello World</p></body></html");
		}
		try {
			OutputStream oStream = new FileOutputStream(new File(root.getAbsolutePath() + path));
			oStream.write(entityBody.getBytes());
			oStream.close();
			return reply;
		} catch (IOException e) {
			throw new MyHTTPProtocolException(500, "Internal Server Error", "There was a problem creating the response from " + path);
		}
	}
	
	/**
	 * Run the DELETE method
	 * 
	 * @param path
	 * @param parameters
	 * @return HTTPReply
	 * @throws HTTPProtocolException
	 */
	private HTTPReply runDELETEMethod(String path, Map<String, String> parameters) throws HTTPProtocolException {
		String password = parameters.get("Authentication");
		if (password == null || !password.equals("1234")) {
			return new MyHTTPReply("HTTP/1.1 401 Unauthorized", getHeaders(path).toString(), "");
		}
		if (new File(root.getAbsolutePath() + path).delete()) {
			return new MyHTTPReply("HTTP/1.1 202 Accepted", getHeaders(path).toString(), "");
		}
		else {
			return new MyHTTPReply("HTTP/1.1 204 No Content", getHeaders(path).toString(), "");
		}
	}
	
	/**
	 * 
	 * @param path of the resource
	 * @return a {@link StringBuilder} with the headers of the {@link MyHTTPReply}
	 */
	private StringBuilder getHeaders(String path) {
		StringBuilder stringBuilder = MyHTTPReply.getStdHeaders();
		stringBuilder.append("Content-Location: ");
		stringBuilder.append("\r\n");
		stringBuilder.append("Connection: close");
		stringBuilder.append("\r\n");
		return stringBuilder;
	}
}
