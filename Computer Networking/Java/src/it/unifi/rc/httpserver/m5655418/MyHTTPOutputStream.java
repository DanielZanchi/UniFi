package it.unifi.rc.httpserver.m5655418;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Map;

import it.unifi.rc.httpserver.HTTPOutputStream;
import it.unifi.rc.httpserver.HTTPReply;
import it.unifi.rc.httpserver.HTTPRequest;

/**
 * Class that extends {@link HTTPOutputStream}
 * 
 * @author Daniel Zanchi, 5655418
 *
 */
public class MyHTTPOutputStream extends HTTPOutputStream {

	private BufferedOutputStream outputStream;
	private String endString = "\r\n";
	
	/**
	 * Constructor that recalls the superclass {@link HTTPOutputStream}s constructor to setup the outputstream
	 *
	 * @param os to assign to the current outputStream
	 */
	public MyHTTPOutputStream(OutputStream os) {
		super(os);
	}
	
	/**
	 * Initializes {@link #outputStream} with a new {@link OutputStream} {@code os}.
	 */
	@Override
	protected void setOutputStream(OutputStream os) {
		outputStream = new BufferedOutputStream(os);
	}

	/**
	 * It writes a {@link HTTPReply} creating a {@link StringBuilder} then it writes on the {@link #outputStream}.
	 * 
	 * @param reply to create the correct outpustream
	 */
	@Override
	public void writeHttpReply(HTTPReply reply) {
		StringBuilder stringBuilder = new StringBuilder();
		createStatusLine(reply, stringBuilder);
		createHeaderLines(reply.getParameters(), stringBuilder);
		stringBuilder.append(reply.getData());
		try {
			outputStream.write(stringBuilder.toString().getBytes(), 0, stringBuilder.toString().length());
			outputStream.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	/**
	 * It writes a {@link HTTPRequest} creating a {@link StringBuilder} then it writes on the {@code #outputStream}
	 * 
	 *  @param request to create the outputstream
	 */
	@Override
	public void writeHttpRequest(HTTPRequest request) {
		StringBuilder stringBuilder = new StringBuilder();
		createRequestLine(request, stringBuilder);
		createHeaderLines(request.getParameters(), stringBuilder);
		stringBuilder.append(request.getEntityBody());
		try {
			outputStream.write(stringBuilder.toString().getBytes(), 0, stringBuilder.toString().length());
			outputStream.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * It closes the {@link #outputStream}
	 */
	@Override
	public void close() throws IOException {
		outputStream.close();
	}
	
	/**
	 * Appends the Request Line parameters to {@code stringBuilder} such as method, path and version.
	 * 
	 * @param request
	 * @param stringBuilder
	 */
	private void createRequestLine(HTTPRequest request, StringBuilder stringBuilder) {
		stringBuilder.append(request.getMethod());
		stringBuilder.append(" ");
		stringBuilder.append(request.getPath());
		stringBuilder.append(" ");
		stringBuilder.append(request.getVersion());
		stringBuilder.append(endString);
	}

	/**
	 * Appends the header lines parameters to {@code stringBuilder} with the template "key: value\r\n". It will end with two end strings "\r\n\r\n".
	 * 
	 * @param request
	 * @param stringBuilder
	 */
	private void createHeaderLines(Map<String, String> parameters, StringBuilder stringBuilder) {
		Iterator<String> keyIt = parameters.keySet().iterator();
		while (keyIt.hasNext()) {
			String key = keyIt.next();
			String value = parameters.get(key);
			stringBuilder.append(key);
			stringBuilder.append(": ");
			stringBuilder.append(value);
			stringBuilder.append(endString);
		}
		stringBuilder.append(endString);
	}
	
	/**
	 * Appends the status line parameters to {@code stringBuilder} such as version, status code and status message of the reply message.
	 * @param reply
	 * @param stringBuilder
	 */
	private void createStatusLine(HTTPReply reply, StringBuilder stringBuilder) {
		stringBuilder.append(reply.getVersion());
		stringBuilder.append(" ");
		stringBuilder.append(reply.getStatusCode());
		stringBuilder.append(" ");
		stringBuilder.append(reply.getStatusMessage());
		stringBuilder.append(endString);
	}

}
