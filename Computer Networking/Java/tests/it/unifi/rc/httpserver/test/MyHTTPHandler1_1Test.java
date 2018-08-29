package it.unifi.rc.httpserver.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.Before;
import org.junit.Test;

import it.unifi.rc.httpserver.HTTPProtocolException;
import it.unifi.rc.httpserver.HTTPReply;
import it.unifi.rc.httpserver.HTTPRequest;
import it.unifi.rc.httpserver.m5655418.MyHTTPRequest;

public class MyHTTPHandler1_1Test {
	private HTTPReply reply;
	private TestObj test;

	private String path = "tests/src_root";
	
	@Before
	public void init() {
		test = new TestObj();
	}

	// Test GET method
	@Test
	public void getFileOnRootTest() throws HTTPProtocolException {
		reply = test.requestWithHandler1_1(path, "GET /File.html HTTP/1.1");
		assertEquals("200", reply.getStatusCode());
		assertEquals("OK", reply.getStatusMessage());
		assertEquals(true, reply.getData().contains(
				"<!DOCTYPE html><html><body><h1>My First Heading</h1><p>My first paragraph.</p></body></html>\n"));
	}

	@Test
	public void getFileOnRoot1_0Test() throws HTTPProtocolException {
		reply = test.requestWithHandler1_1(path, "GET /File.html HTTP/1.0");
		assertEquals("200", reply.getStatusCode());
		assertEquals("OK", reply.getStatusMessage());
		assertEquals(true, reply.getData().contains(
				"<!DOCTYPE html><html><body><h1>My First Heading</h1><p>My first paragraph.</p></body></html>\n"));
	}

	@Test
	public void getFileOnSubdirectoryTest() throws HTTPProtocolException {
		reply = test.requestWithHandler1_1(path, "GET /sub/File.html HTTP/1.1");
		assertEquals("200", reply.getStatusCode());
		assertEquals("OK", reply.getStatusMessage());
		assertEquals(true, reply.getData().contains(
				"<!DOCTYPE html><html><body><h1>My First Heading</h1><p>My first paragraph.</p></body></html>\n"));
	}

	@Test
	public void getFileInWrongRootTest() throws HTTPProtocolException {
		reply = test.requestWithHandler1_1("tests/src_root_wrong", "GET /File.html HTTP/1.1");
		assertEquals("404", reply.getStatusCode());
		assertEquals("Not Found", reply.getStatusMessage());
	}

	// Test HEAD method

	@Test
	public void headRequestTest() throws HTTPProtocolException {
		reply = test.requestWithHandler1_1("test/src_root", "HEAD /some_path HTTP/1.1");
		assertEquals("200", reply.getStatusCode());
	}

	// Test POST method

	@Test
	public void postFileToPathTest() throws HTTPProtocolException {
		try {
			OutputStream outputStream = new FileOutputStream("tests/src_root/sub/File_from_POST");
			outputStream.write("".getBytes());
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		reply = test.requestWithHandler1_1(path, "POST /sub/File_from_POST HTTP/1.1");
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

		reply = test.requestWithHandler1_1("tests/src_root_wrong", "POST /sub/File_from_POST HTTP/1.1");
		assertEquals("404", reply.getStatusCode());
		assertEquals("Not Found", reply.getStatusMessage());
	}
	
	// Test PUT method
	
	@Test
	public void putFileToPathNoContentTest() throws HTTPProtocolException {
		reply = test.requestWithHandler1_1(path, "PUT /File_from_PUT HTTP/1.1");
		assertEquals("204", reply.getStatusCode());
		assertEquals("No Content", reply.getStatusMessage());
	}
	
	@Test
	public void putFileToPathTest() throws HTTPProtocolException {
		String file = "/File_from_PUT";
		if (new File(path + file).exists())
			new File(path + file).delete();
		
		reply = test.requestWithHandler1_1(path, "PUT /File_from_PUT HTTP/1.1");
		assertEquals("201", reply.getStatusCode());
		assertEquals("Created", reply.getStatusMessage());
	}
	
	// Test DELETE method
	
	@Test
	public void deleteFileInRootTest() throws HTTPProtocolException {
		createFile(path + "/File_to_delete.html");
		try {
			HTTPRequest request = new MyHTTPRequest("DELETE /File_to_delete.html HTTP/1.1", "Server: Apache/2.5\r\nAuthentication: 1234", "");
			reply = test.deleteFileWithHandler1_1(path, request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals("202", reply.getStatusCode());
		assertEquals("Accepted", reply.getStatusMessage());
	}
	
	@Test 
	public void deleteNotExistingFileTest() throws HTTPProtocolException {
		try {
			HTTPRequest request = new MyHTTPRequest("DELETE /File_to_delete.html HTTP/1.1", "Server: Apache/2.5\r\nAuthentication: 1234", "");
			reply = test.deleteFileWithHandler1_1(path, request);
			assertEquals("204", reply.getStatusCode());
			assertEquals("No Content", reply.getStatusMessage());
		} catch (HTTPProtocolException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test 
	public void deleteFileWrongPasswordTest() throws HTTPProtocolException {
		createFile(path + "/File_to_delete.html");
		try {
			HTTPRequest request = new MyHTTPRequest("DELETE /File_to_delete.html HTTP/1.1", "Server: Apache/2.5\r\nAuthentication: 12345", "");
			reply = test.deleteFileWithHandler1_1(path, request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals("401", reply.getStatusCode());
		assertEquals("Unauthorized", reply.getStatusMessage());
	}
	
	private void createFile(String file) {
		if (!new File(file).exists())
		try {
			new File(file).createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Tests with host
	
	@Test
	public void emptyHostTest() throws HTTPProtocolException {
		reply = test.requestWithHandler1_1("", path, "GET /File.html HTTP/1.0");
		assertNull(reply);
	}
	
	@Test
	public void sameHostTest() throws HTTPProtocolException {
		reply = test.requestWithHandler1_1("www.google.it", path, "GET /File.html HTTP/1.0");
		assertNotNull(reply);
	}
	
	// Generic tests on handler
	
	@Test
	public void unsupportedVersionTest() throws HTTPProtocolException {
		reply = test.requestWithHandler1_1(path, "GET /Path HTTP/1.2");
		assertEquals("505", reply.getStatusCode());
		assertEquals("HTTP version not supported", reply.getStatusMessage());
	}
	

	
	@Test
	public void unsupportedMethodTest() throws HTTPProtocolException {
		reply = test.requestWithHandler1_1(path, "TRACE /Path HTTP/1.0");
		assertEquals("501", reply.getStatusCode());
		assertEquals("Not Implemented", reply.getStatusMessage());
	}
	
	@Test
	public void inexistentMethodTest() throws HTTPProtocolException {
		reply = test.requestWithHandler1_1(path, "DOWNLOAD /Path HTTP/1.0");
		assertEquals("501", reply.getStatusCode());
		assertEquals("Not Implemented", reply.getStatusMessage());
	}
	
	@Test
	public void noRootTest() throws HTTPProtocolException {
		reply = test.requestWithHandler1_1("", "GET /Path HTTP/1.0");
		assertEquals("404", reply.getStatusCode());
		assertEquals("Not Found", reply.getStatusMessage());
	}

}
