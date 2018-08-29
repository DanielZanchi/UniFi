package it.unifi.rc.httpserver.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;

import org.junit.Before;
import org.junit.Test;

import it.unifi.rc.httpserver.HTTPOutputStream;
import it.unifi.rc.httpserver.HTTPReply;
import it.unifi.rc.httpserver.HTTPRequest;
import it.unifi.rc.httpserver.m5655418.MyHTTPOutputStream;
import it.unifi.rc.httpserver.m5655418.MyHTTPReply;
import it.unifi.rc.httpserver.m5655418.MyHTTPRequest;

public class MyHTTPOutputStremTest {
	private ByteArrayOutputStream outputStream;
	private HTTPOutputStream httpOutputStream;
	
	@Before
	public void init() {
		outputStream = new ByteArrayOutputStream();
		httpOutputStream = new MyHTTPOutputStream(outputStream);
	}
	
	//TEST THE REQUEST
	@Test
	public void writeRequestTest() {
		try {
			HTTPRequest request = new MyHTTPRequest("GET /index.html HTTP/1.1", "Host: www.google.it\r\nConnection: keep-alive", "entity body");
			httpOutputStream.writeHttpRequest(request);
			assertEquals("GET /index.html HTTP/1.1\r\nHost: www.google.it\r\nConnection: keep-alive\r\n\r\nentity body", outputStream.toString());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	//wrong http version
	@Test
	public void writeWrongVersionRequestTest() {
		try {
			HTTPRequest request = new MyHTTPRequest("GET /index.html HTTP/1.1", "Host: www.google.it\r\nConnection: keep-alive", "entity body");
			httpOutputStream.writeHttpRequest(request);
			assertNotEquals("GET /index.html HTTP/1.0\r\nHost: www.google.it\r\nConnection: keep-alive\r\n\r\nentity body", outputStream.toString());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	//wrong Host
	@Test
	public void writeWrongHostRequestTest() {
		try {
			HTTPRequest request = new MyHTTPRequest("GET /index.html HTTP/1.1", "Host: www.google.it\r\nConnection: keep-alive", "entity body");
			httpOutputStream.writeHttpRequest(request);
			assertNotEquals("GET /index.html HTTP/1.1\r\nHost: www.gooogle.it\r\nConnection: keep-alive\r\n\r\nentity body", outputStream.toString());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	//wrong body
	@Test
	public void writeWrongBodyRequestTest() {
		try {
			HTTPRequest request = new MyHTTPRequest("GET /index.html HTTP/1.1", "Host: www.google.it\r\nConnection: keep-alive", "entity body");
			httpOutputStream.writeHttpRequest(request);
			assertNotEquals("GET /index.html HTTP/1.1\r\nHost: www.google.it\r\nConnection: keep-alive\r\n\r\nentity body wrong", outputStream.toString());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	//TEST THE REPLY
	
	@Test
	public void writeReplyTest() {
		try {
			HTTPReply reply = new MyHTTPReply("HTTP/1.1 200 OK", "Date: Wed, 20 Jun 2018 18:08 GTM\r\nConnection: Keep-Alive\r\n", "data");
			httpOutputStream.writeHttpReply(reply);
			assertEquals("HTTP/1.1 200 OK\r\nDate: Wed, 20 Jun 2018 18:08 GTM\r\nConnection: Keep-Alive\r\n\r\ndata", outputStream.toString());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	//wrong status line
	@Test
	public void writeWrongStatusReplyTest() {
		try {
			HTTPReply reply = new MyHTTPReply("HTTP/1.1 200 OK", "Date: Wed, 20 Jun 2018 18:08 GTM\r\nConnection: Keep-Alive\r\n", "data");
			httpOutputStream.writeHttpReply(reply);
			assertNotEquals("HTTP/1.1 300 OK\r\nDate: Wed, 20 Jun 2018 18:08 GTM\r\nConnection: Keep-Alive\r\n\r\ndata", outputStream.toString());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	//wrong header line
	@Test
	public void writeWrongHeaderReplyTest() {
		try {
			HTTPReply reply = new MyHTTPReply("HTTP/1.1 200 OK", "Date: Wed, 20 Jun 2018 18:08 GTM\r\nConnection: Keep-Alive\r\n", "data");
			httpOutputStream.writeHttpReply(reply);
			assertNotEquals("HTTP/1.1 200 OK\r\nDate: Wed, 20 Jun 2018 18:08 GTM\r\nConnection:Keep-Alive\r\n\r\ndata", outputStream.toString());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	//wrong data
	@Test
	public void writeWrongDataReplyTest() {
		try {
			HTTPReply reply = new MyHTTPReply("HTTP/1.1 200 OK", "Date: Wed, 20 Jun 2018 18:08 GTM\r\nConnection: Keep-Alive\r\n", "data");
			httpOutputStream.writeHttpReply(reply);
			assertNotEquals("HTTP/1.1 300 OK\r\nDate: Wed, 20 Jun 2018 18:08 GTM\r\nConnection: Keep-Alive\r\n\r\nWrong data", outputStream.toString());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}	
}
