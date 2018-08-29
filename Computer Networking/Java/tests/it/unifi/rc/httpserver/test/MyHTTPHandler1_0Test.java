package it.unifi.rc.httpserver.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.Before;
import org.junit.Test;

import it.unifi.rc.httpserver.HTTPProtocolException;
import it.unifi.rc.httpserver.HTTPReply;

public class MyHTTPHandler1_0Test {
	private HTTPReply reply;
	private TestObj test;
	
	private String path = "tests/src_root";

	@Before
	public void init() {
		test = new TestObj();
	}

	// Test the HEAD method
	@Test
	public void headRequestTest() throws HTTPProtocolException {
		reply = test.requestWithHandler1_0(path, "HEAD /some_path HTTP/1.0");
		assertEquals("200", reply.getStatusCode());
		assertEquals("OK", reply.getStatusMessage());
	}

	// Test the GET method
	@Test
	public void getFileTest() throws HTTPProtocolException {
		reply = test.requestWithHandler1_0(path, "GET /File.html HTTP/1.0");
		assertEquals("200", reply.getStatusCode());
		assertEquals("OK", reply.getStatusMessage());
		assertEquals(true, reply.getData().toString().contains(
				"<!DOCTYPE html><html><body><h1>My First Heading</h1><p>My first paragraph.</p></body></html>\n"));
	}

	@Test
	public void getFileInSubTest() throws HTTPProtocolException {
		reply = test.requestWithHandler1_0(path, "GET /sub/File.html HTTP/1.0");
		assertEquals("200", reply.getStatusCode());
		assertEquals("OK", reply.getStatusMessage());
		assertEquals(true, reply.getData().toString().contains(
				"<!DOCTYPE html><html><body><h1>My First Heading</h1><p>My first paragraph.</p></body></html>\n"));
	}

	@Test
	public void getFileInWrongRootTest() throws HTTPProtocolException {
		reply = test.requestWithHandler1_0("tests/src_root_wrong", "GET /sub/File.html HTTP/1.0");
		assertEquals("404", reply.getStatusCode());
		assertEquals("Not Found", reply.getStatusMessage());
	}

	// Test the POST method
	@Test
	public void postFileToPathTest() throws HTTPProtocolException {
		try {
			OutputStream outputStream = new FileOutputStream("tests/src_root/sub/File_from_POST");
			outputStream.write("".getBytes());
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		reply = test.requestWithHandler1_0(path, "POST /sub/File_from_POST HTTP/1.0");
		assertEquals("204", reply.getStatusCode());
		assertEquals("No Content", reply.getStatusMessage());
	}

	@Test
	public void postFileToWrongPathTest() throws HTTPProtocolException {
		try {
			OutputStream outputStream = new FileOutputStream("tests/src_root/sub/File_from_POST");
			outputStream.write("".getBytes());
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		reply = test.requestWithHandler1_0("tests/src_root_wrong", "POST /sub/File_from_POST HTTP/1.0");
		assertEquals("404", reply.getStatusCode());
		assertEquals("Not Found", reply.getStatusMessage());
	}
	
	// Generic tests on handler

	@Test
	public void noRootTest() throws HTTPProtocolException {
		reply = test.requestWithHandler1_0("", "GET /Path HTTP/1.0");
		assertEquals("404", reply.getStatusCode());
		assertEquals("Not Found", reply.getStatusMessage());
	}
	
	@Test
	public void unsupportedVersionTest() throws HTTPProtocolException {
		reply = test.requestWithHandler1_0(path, "GET /Path HTTP/1.1");
		assertEquals("505", reply.getStatusCode());
		assertEquals("HTTP version not supported", reply.getStatusMessage());
	}
	
	@Test
	public void unsupportedMethodTest() throws HTTPProtocolException {
		reply = test.requestWithHandler1_0(path, "DELETE /Path HTTP/1.0");
		assertEquals("501", reply.getStatusCode());
		assertEquals("Not Implemented", reply.getStatusMessage());
	}
	
	@Test
	public void inexistentMethodTest() throws HTTPProtocolException {
		reply = test.requestWithHandler1_0(path, "DOWNLOAD /Path HTTP/1.0");
		assertEquals("501", reply.getStatusCode());
		assertEquals("Not Implemented", reply.getStatusMessage());
	}
	
	
	@Test
	public void hostTest() throws HTTPProtocolException {
		reply = test.requestWithHandler1_0("wrong host", path, "GET /File.html HTTP/1.0");
		assertNull(reply);
	}
	
	@Test
	public void sameHostTest() throws HTTPProtocolException {
		reply = test.requestWithHandler1_0("www.google.it", path, "GET /File.html HTTP/1.0");
		assertNotNull(reply);
	}
	
	@Test
	public void emptyHostTest() throws HTTPProtocolException {
		reply = test.requestWithHandler1_0("", path, "GET /File.html HTTP/1.0");
		assertNull(reply);
	}
}
