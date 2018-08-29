package it.unifi.rc.httpserver.m5655418;

import it.unifi.rc.httpserver.HTTPProtocolException;

/**
 * Class that extends {@link HTTPProtocolException}
 * 
 * @author Daniel Zanchi, 5655418
 *
 */

public class MyHTTPProtocolException extends HTTPProtocolException {
	private static final long serialVersionUID = 6569609913675290966L;
	
	/**
	 * Code of the error of the {@link MyHTTPProtocolException}
	 */
	private final int errorCode;
	
	/**
	 * Message of the error of the {@link MyHTTPProtocolException}
	 */
	private final String errorMessage;
	
	/**
	 * Verbose message of the error of the {@link MyHTTPProtocolException}
	 */
	private final String verboseMessage;
	
	/**
	 * Constructor that initializes {@link errorCode}, {@link errorMessage} and {@link verboseMessage}
	 * @param errorCode of the current exception
	 * @param errorMessage of the current exception
	 * @param verboseMessage of the current exception
	 */
	public MyHTTPProtocolException(int errorCode, String errorMessage, String verboseMessage) {
		super(errorCode + " " + errorMessage);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.verboseMessage = verboseMessage;
		System.out.println("error" + verboseMessage);
	}
	
	/**
	 * 
	 * @return {@link errorCode}
	 */
	public int getErrorCode() {
		return errorCode;
	}
	
	/**
	 * 
	 * @return {@link errorMessage}
	 */
	public String getErrorMessage() {
		return errorMessage;
	}
	
	/**
	 * 
	 * @return {@link verboseMessage}
	 */
	public String getVerboseMessage() {
		return verboseMessage;
	}
}
