package it.unifi.rc.httpserver.m5655418;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

import it.unifi.rc.httpserver.HTTPProtocolException;
import it.unifi.rc.httpserver.HTTPReply;

/**
 * Class that implements {@link HTTPReply}
 * 
 * @author Daniel Zanchi, 5655418
 *
 */

public class MyHTTPReply implements HTTPReply {

	private String version;
	private String statusCode;
	private String statusMessage;
	private String data;
	private Map<String, String> parameters;

	/**
	 * Default end line string used to get parameters from the header line
	 */
	private static String endString = "\r\n";

	/**
	 * Constructor that uses the parameter to craete a reply.
	 * 
	 * @param statusLine of the current reply
	 * @param headerLines of the current reply
	 * @param data of the current reply
	 * @throws HTTPProtocolException that could be created in case of an error
	 */
	public MyHTTPReply(String statusLine, String headerLines, String data) throws HTTPProtocolException {
		setupStatusLine(statusLine);
		setupHeaderLines(headerLines);
		this.data = data;
	}
	
	/**
	 * Constructor created from the exception.
	 * 
	 * @param exception of the current reply
	 * @param version of the current reply
	 */
	public MyHTTPReply(MyHTTPProtocolException exception, String version) {
		this.version = version;
		this.statusCode = Integer.toString(exception.getErrorCode());
		this.statusMessage = exception.getErrorMessage();
	}

	/**
	 * Create a Map of parameters from the header lines
	 * 
	 * @param header
	 * @throws HTTPProtocolException
	 */
	private void setupHeaderLines(String header) throws HTTPProtocolException {
		if (header != null && !header.isEmpty()) {
			if (header.contains(endString)) {
				parameters = new LinkedHashMap<>();
				final String[] headerLinesArray = header.split("\r\n");
				for (String parameter : headerLinesArray) {
					if (parameter.contains(":")) {
						String key = new String(parameter.substring(0, parameter.indexOf(':')));
						String value = new String(parameter.substring(parameter.indexOf(' ') + 1));
						parameters.put(key, value);
					} else {
						throw new MyHTTPProtocolException(400, "Bad Request", "':' no found in headLines " + parameter);
					}
				}
			} else {
				throw new MyHTTPProtocolException(400, "Bad Request", "'\r\n' not found in header lines of HTTPReply");
			}
		} else {
			throw new MyHTTPProtocolException(400, "Bad Request", "header lines for HTTPReply was empty");
		}
	}

	/**
	 * Extracts the parameters from the status line and assigns the version, statusCode and statusMessage
	 * 
	 * @param status that we want to extract
	 * @throws HTTPProtocolException
	 */
	private void setupStatusLine(String status) throws HTTPProtocolException {
		String[] parameters = status.split(" ", 3);
		try {
			version = parameters[0];
			statusCode = parameters[1];
			statusMessage = parameters[2].replace(endString, "");
		} catch (Exception e) {
			throw new MyHTTPProtocolException(400, "Bad Reply",
					"Error while building status line of reply message" + e.getStackTrace());
		}
	}
	
	/**
	 * 
	 * @return {@link StringBuilder} containing the standards headers as "Date"
	 */
	public static StringBuilder getStdHeaders() {
		StringBuilder string = new StringBuilder();
		string.append("Date: ");
		String date = DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now());
		string.append(date);
		string.append(endString);
		return string;
	}

	/**
	 * @return {@link version}
	 */
	@Override
	public String getVersion() {
		return version;
	}

	/**
	 * @return {@link statusCode}
	 */
	@Override
	public String getStatusCode() {
		return statusCode;
	}

	/**
	 * @return {@link statusMessage}
	 */
	@Override
	public String getStatusMessage() {
		return statusMessage;
	}

	/**
	 * @return {@link data}
	 */
	@Override
	public String getData() {
		return data;
	}

	/**
	 * @return {@link parameters}
	 */
	@Override
	public Map<String, String> getParameters() {
		return parameters;
	}

}
