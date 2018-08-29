package it.unifi.rc.httpserver.m5655418;

import java.util.LinkedHashMap;
import java.util.Map;

import it.unifi.rc.httpserver.HTTPProtocolException;
import it.unifi.rc.httpserver.HTTPRequest;

/**
 * Class that implements the interface {@link HTTPRequest}.
 * 
 * @author Daniel Zanchi, 5655418
 */
public class MyHTTPRequest implements HTTPRequest {

	private String version;
	private String method;
	private String entityBody;
	private String url;
	private Map<String, String> parameters;
	
	/**
	 * Default end line string used to get parameters from the header line
	 */
	private static String endString = "\r\n";
	
	public MyHTTPRequest(String requestLine, String headerLines, String entityBody) throws HTTPProtocolException {
		this.entityBody = entityBody;
		setupRequestLineParameters(requestLine);
		setupHeaderParameters(headerLines);
	}
	
	/**
	 * @return {@link #version}
	 */
	@Override
	public String getVersion() {
		return this.version;
	}

	/**
	 * @return the HTTP method
	 */
	@Override
	public String getMethod() {
		return this.method;
	}

	/**
	 * @return the url of the resource requested
	 */
	@Override
	public String getPath() {
		return this.url;
	}

	/**
	 * @return {@link #entityBody}
	 */
	@Override
	public String getEntityBody() {
		return this.entityBody;
	}

	/**
	 * @return the parameters
	 */
	@Override
	public Map<String, String> getParameters() {
		return parameters;
	}
	
	/**
	 * Receives the request lines and it sets the version, method and url parameters
	 * 
	 * @param requestLine
	 * @throws MyHTTPProtocolException
	 */
	private void setupRequestLineParameters(String requestLine) throws MyHTTPProtocolException {
		String[] parameters = requestLine.split(" ", 3);
		try {
			method = parameters[0];
			url = parameters[1];
			version = parameters[2].replace(endString, "");
		} catch (Exception e) {
			throw new MyHTTPProtocolException(400, "Bad Request", "Request line contains an error" + e.getStackTrace());
		}
	}
	
	/**
	 * Receives the header lines and it sets the parameters checking for an eventual bad request
	 * 
	 * @param headerLines
	 * @throws HTTPProtocolException
	 */
	private void setupHeaderParameters(String headerLines) throws HTTPProtocolException {
		if (headerLines!= null && !headerLines.isEmpty()) {
			if (headerLines.contains(endString)) {
				final String[] headerLinesArray = headerLines.split("\r\n");
				parameters = new LinkedHashMap<>();
				for (String parameter : headerLinesArray) {
					if (parameter.contains(":")) {
						String key = new String(parameter.substring(0, parameter.indexOf(':')));
						String value = new String(parameter.substring(parameter.indexOf(' ') + 1));
						parameters.put(key, value);
					}
					else {
						throw new MyHTTPProtocolException(400, "Bad Request", "':' not found in headerlines");
					}
				}
			}
			else {
				throw new MyHTTPProtocolException(400, "Bad Request", " '\r\n' not found in headerlines");
			}
		}
		else {
			throw new MyHTTPProtocolException(400, "Bad Request", "The header line was empty");
		}
	}
	
}
