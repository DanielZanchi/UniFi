package it.unifi.rc.httpserver.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;

import org.junit.Before;
import org.junit.Test;

import it.unifi.rc.httpserver.HTTPReply;
import it.unifi.rc.httpserver.HTTPRequest;
import it.unifi.rc.httpserver.m5655418.MyHTTPInputStream;

/**
 * 
 * @author Daniel Zanchi, 5655418
 *
 */
public class MyHTTPInputStreamTest {
	private MyHTTPInputStream requestInputStream;
	private MyHTTPInputStream replyInputStream;
	private HTTPRequest request;
	private HTTPReply reply;
	
	@Before
	public void init() {
		requestInputStream = getRequestInputStream();
		replyInputStream = getReplyInputStream();
	}
	
	//TEST THE REQUEST
	@Test
	public void requestMethodNameTest() {
		try {
			request = requestInputStream.readHttpRequest();
			assertEquals("POST", request.getMethod());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void requestVersionTest() {
		try {
			request = requestInputStream.readHttpRequest();
			assertEquals("HTTP/1.1", request.getVersion());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test 
	public void requestParametersTest() {
		try {
			request = requestInputStream.readHttpRequest();
			assertEquals("www.google.it", request.getParameters().get("Host"));
			assertEquals("Firefox/3.6.10", request.getParameters().get("User-Agent"));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void requestBodyTest() {
		try {
			request = requestInputStream.readHttpRequest();
			assertEquals(true, request.getEntityBody().contains("body"));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	//TEST THE REPLY
	
	@Test
	public void replyVersionTest() {
		try {
			reply = replyInputStream.readHttpReply();
			assertEquals("HTTP/1.1", reply.getVersion());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void replyStatusCodeTest() {
		try {
			reply = replyInputStream.readHttpReply();
			assertEquals("200", reply.getStatusCode());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void replyStatusMessageTest() {
		try {
			reply = replyInputStream.readHttpReply();
			assertEquals("OK", reply.getStatusMessage());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void replyHeaderLinesTest() {
		try {
			reply = replyInputStream.readHttpReply();
			assertEquals("Wed, 20 Jun 2018 12:51:21 GMT", reply.getParameters().get("Date"));
			assertEquals("Apache/2.0.52 (CentOS)", reply.getParameters().get("Server"));
			assertEquals("Keep-Alive", reply.getParameters().get("Connection"));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void replyDataTest() {
		try {
			reply = replyInputStream.readHttpReply();
			assertEquals("<html>data</html>", reply.getData());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	 
	private MyHTTPInputStream getRequestInputStream() {
		String requestString = "POST /intex.html HTTP/1.1\r\nHost: www.google.it\r\nUser-Agent: Firefox/3.6.10\r\n\r\nEntity body\r\n";
		return new MyHTTPInputStream(new ByteArrayInputStream(requestString.getBytes()));
	}
	
	private MyHTTPInputStream getReplyInputStream() {
		String replyString = "HTTP/1.1 200 OK\r\nDate: Wed, 20 Jun 2018 12:51:21 GMT\r\nServer: Apache/2.0.52 (CentOS)\r\nConnection: Keep-Alive\r\n\r\n<html>data</html>";
		return new MyHTTPInputStream(new ByteArrayInputStream(replyString.getBytes()));
	}
}
