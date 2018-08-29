package it.unifi.rc.httpserver.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import it.unifi.rc.httpserver.HTTPReply;
import it.unifi.rc.httpserver.m5655418.MyHTTPProtocolException;

public class MyHTTPReplyTest {
	private HTTPReply reply;
	private TestObj test;

	@Before
	public void init() {
		test = new TestObj();
	}

	@Test
	public void httpVersionTest() {
		try {
			reply = test.createReplyFromStatusLine("HTTP/1.1 200 OK\r\n");
			assertEquals("HTTP/1.1", reply.getVersion());
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void httpStatusCodeTest() {
		try {
			reply = test.createReplyFromStatusLine("HTTP/1.1 200 OK\r\n");
			assertEquals("200", reply.getStatusCode());
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void httpStatusMessageTest() {
		try {
			reply = test.createReplyFromStatusLine("HTTP/1.1 200 OK\r\n");
			assertEquals("OK", reply.getStatusMessage());
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void httpWrongStatusLine() {
		try {
			reply = test.createReplyFromStatusLine("HTTP/1.1 200OK");
		} catch (Exception e) {
			assertEquals(400, ((MyHTTPProtocolException) e).getErrorCode());
			return;
		}
		fail();
	}

	@Test
	public void httpHeaderTest() {
		try {
			reply = test.createReplyFromHeaderLines("Server: Apache/2.0.52 (CentOS)\r\n");
			assertEquals("Apache/2.0.52 (CentOS)", reply.getParameters().get("Server"));
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void httpWrongHeaderLineTest() {
		try {
			reply = test.createReplyFromHeaderLines("Server= Apache/2.0.52 (CentOS)\r\n");
		} catch (Exception e) {
			assertEquals(400, ((MyHTTPProtocolException) e).getErrorCode());
			return;
		}
		fail();
	}

	@Test
	public void httpWrongHeaderLinesEndlLineTest() {
		try {
			reply = test.createReplyFromHeaderLines("Server= Apache/2.0.52 (CentOS)\n\n");
		} catch (Exception e) {
			assertEquals(400, ((MyHTTPProtocolException) e).getErrorCode());
			return;
		}
		fail();
	}

	@Test
	public void httpDataTest() {
		try {
			reply = test.createReplyFromData("<img>test.png</img>");
			assertEquals("<img>test.png</img>", reply.getData());
		} catch (Exception e) {
			fail();
		}
	}
}
