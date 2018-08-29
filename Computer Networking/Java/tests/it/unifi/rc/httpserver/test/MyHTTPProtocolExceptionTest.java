package it.unifi.rc.httpserver.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

import it.unifi.rc.httpserver.m5655418.MyHTTPProtocolException;

public class MyHTTPProtocolExceptionTest {

	MyHTTPProtocolException exception;
	
	private int errorCode = 400;
	private String errorMessage = "Error message";
	private String verboseMessage = "Verbose message";
	
	@Before
	public void init() {
		exception = new MyHTTPProtocolException(errorCode, errorMessage, verboseMessage);
	}
	
	@Test
	public void okErrorCodeTest() {
		assertEquals(errorCode, exception.getErrorCode());
	}
	
	@Test
	public void wrongErrorCodeTest() {
		assertNotEquals(401, exception.getErrorCode());
	}
	
	@Test
	public void okErrorMessageTest() {
		assertEquals(errorMessage, exception.getErrorMessage());
	}
	
	@Test
	public void wrongErrorMessageTest() {
		assertNotEquals("wrong message", exception.getErrorMessage());
	}
	
	@Test
	public void okVerboseMessageTest() {
		assertEquals(verboseMessage, exception.getVerboseMessage());
	}
	
	@Test
	public void wrongVerboseMessage() {
		assertNotEquals("wrong verbose message", exception.getVerboseMessage());
	}
}
