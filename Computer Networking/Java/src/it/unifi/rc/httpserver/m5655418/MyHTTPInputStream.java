package it.unifi.rc.httpserver.m5655418;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import it.unifi.rc.httpserver.HTTPInputStream;
import it.unifi.rc.httpserver.HTTPProtocolException;
import it.unifi.rc.httpserver.HTTPReply;
import it.unifi.rc.httpserver.HTTPRequest;

/**
 * Class that extends {@link HTTPInputStream}
 * 
 * @author Daniel Zanchi, 5655418
 *
 */

public class MyHTTPInputStream extends HTTPInputStream {
	private BufferedInputStream inputStream;
	private Scanner stringsScanner;
	private String endString = "\r\n";
	private String endHeaderLine = "\r\n\r\n";

	/**
	 * Constructor that recalls the superclass {@link HTTPInputStream}s constructor
	 * to setup the inputstream.
	 * 
	 * @param is
	 */
	public MyHTTPInputStream(InputStream is) {
		super(is);
	}

	/**
	 * initializes the inputStream with {@code is} then it reads and decodes the
	 * stream using stringsScanner
	 */
	@Override
	protected void setInputStream(InputStream is) {
		inputStream = new BufferedInputStream(is);
		stringsScanner = new Scanner(getStringFromBuffer());
		stringsScanner.useDelimiter("\r\n");
	}

	/**
	 * @return {@link MyHTTPRequest} parsed from the stringsScanner
	 */
	@Override
	public HTTPRequest readHttpRequest() throws HTTPProtocolException {
		String requestLine = readLineWithEnd(endString);
		String headerLines = readLineWithEnd(endHeaderLine);
		String entityBody = readEntityBody();
		return new MyHTTPRequest(requestLine, headerLines, entityBody);
	}

	/**
	 * @return {@link MyHTTPReply} parsed from the stringsScanner
	 */
	@Override
	public HTTPReply readHttpReply() throws HTTPProtocolException {
		String statusLine = readLineWithEnd(endString);
		String headerLines = readLineWithEnd(endHeaderLine);
		String data = readEntityBody();
		return new MyHTTPReply(statusLine, headerLines, data);
	}

	/**
	 * Closes the inputStream and the stringsScanner
	 */
	@Override
	public void close() throws IOException {
		inputStream.close();
		stringsScanner.close();
	}

	/**
	 * Read and decodes data from inputStream
	 * 
	 * @return a {@link String} that buffers the data from the inputStream
	 */
	private String getStringFromBuffer() {
		byte[] buffer = new byte[1024];
		int read;
		try {
			read = inputStream.read(buffer);
			return new String(buffer, 0, read);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * Splits the content of the {@link #stringsScanner} using the endline string
	 * 
	 * @param endLine
	 * @return a String from the StringScanner using the {@code endline}
	 */
	private String readLineWithEnd(String endLine) {
		stringsScanner.useDelimiter(endLine);
		String line = "";
		if (stringsScanner.hasNext()) {
			line = stringsScanner.next();
		}
		stringsScanner.skip(endLine);
		return line;
	}

	/**
	 * Puts the body into a string using the {@link #stringsScanner}
	 * 
	 * @return a {@link String} with the entityBody
	 */
	private String readEntityBody() {
		StringBuilder body = new StringBuilder();
		while (stringsScanner.hasNextLine()) {
			body.append(stringsScanner.nextLine());
		}
		return body.toString();
	}
}
