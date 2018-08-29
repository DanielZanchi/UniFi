package it.unifi.rc.httpserver.test;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import it.unifi.rc.httpserver.HTTPProtocolException;
import it.unifi.rc.httpserver.HTTPRequest;
import it.unifi.rc.httpserver.m5655418.MyHTTPProtocolException;

public class MyHTTPRequestTest {
	private HTTPRequest request;
	private TestObj test;
	
	@Before
	public void init() {
		test = new TestObj();
	}

	@Test
	public void httpVersionTest() {
		try {
			request = test.createRequestFromRequestLine("GET /index.html HTTP/1.0");
			assertEquals("HTTP/1.0", request.getVersion());
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void httpWrongRequestLineTest() {
		try {
			request = test.createRequestFromRequestLine("GET /index.htmlHTTP/1.0");
		} catch (HTTPProtocolException e) {
			assertEquals(400, ((MyHTTPProtocolException) e).getErrorCode());
			return;
		}
		fail();
	}
	
	@Test
	public void httpMethodTest() {
		try {
			request = test.createRequestFromRequestLine("POST /index.html HTTP/1.0");
			assertEquals("POST", request.getMethod());
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void httpMethod2Test() {
		try {
			request = test.createRequestFromRequestLine("GET /index.html HTTP/1.0");
			assertEquals("GET", request.getMethod());
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void httpPathTest() {
		try {
			request = test.createRequestFromRequestLine("GET /index.html HTTP/1.0");
			assertEquals("/index.html", request.getPath());
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void httpEntityBodyTest() {
		try {
			request = test.createRequestFromBody("entity body");
			assertEquals("entity body", request.getEntityBody());
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void httpEmptyEntityBodyTest() {
		try {
			request = test.createRequestFromBody(null);
			assertNull(request.getEntityBody());
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test 
	public void httpParametersTest() {
		try {
		request = test.createRequestFromHeaderLines("Host: www.google.it\r\nUser-Agent: Firefox/3.6.10\r\n");
		assertEquals("www.google.it", request.getParameters().get("Host"));
		assertEquals("Firefox/3.6.10", request.getParameters().get("User-Agent"));
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void httpWrongHeaderLinesTest() {
		try {
		request = test.createRequestFromHeaderLines("Host= www.google.it\r\nUser-Agent= Firefox/3.6.10\r\n");
		} catch (Exception e) {
			assertEquals(400, ((MyHTTPProtocolException) e).getErrorCode());
			return;
		}
		fail();
	}
	
	@Test
	public void httpWrongHeaderLinesEndlLineTest() {
		try {
			request = test.createRequestFromHeaderLines("Host: www.google.it\n\nUser-Agent: Firefox/3.6.10");
		} catch (Exception e) {
			assertEquals(400, ((MyHTTPProtocolException) e).getErrorCode());
			return;
		}
		fail();
	}
}
