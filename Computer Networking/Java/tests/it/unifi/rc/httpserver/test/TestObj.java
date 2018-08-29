package it.unifi.rc.httpserver.test;

import java.io.File;

import it.unifi.rc.httpserver.HTTPHandler;
import it.unifi.rc.httpserver.HTTPProtocolException;
import it.unifi.rc.httpserver.HTTPReply;
import it.unifi.rc.httpserver.HTTPRequest;
import it.unifi.rc.httpserver.m5655418.MyHTTPHandler1_0;
import it.unifi.rc.httpserver.m5655418.MyHTTPHandler1_1;
import it.unifi.rc.httpserver.m5655418.MyHTTPReply;
import it.unifi.rc.httpserver.m5655418.MyHTTPRequest;

/**
 * 
 * @author Daniel Zanchi, 5655418
 *
 */

public class TestObj {
	private String requestLine = "GET /index.html HTTP/1.0";
	private String headerLines = "Host: www.google.it\r\n";
	private String entityBody = "html entity body";
	private String statusLine = "\"HTTP/1.1 200 OK";
	private String data = "";
	
	
	public HTTPRequest createRequest(String requestLine, String headerLines, String entityBody) throws HTTPProtocolException {
		return new MyHTTPRequest(requestLine, headerLines, entityBody);
	}
	
	public HTTPRequest createRequestFromRequestLine(String requestLine) throws HTTPProtocolException {
		return createRequest(requestLine, headerLines, entityBody);
	}
	
	public HTTPRequest createRequestFromBody(String entityBody) throws HTTPProtocolException {
		return createRequest(requestLine, headerLines, entityBody);
	}
	
	public HTTPRequest createRequestFromHeaderLines(String headerLines) throws HTTPProtocolException {
		return createRequest(requestLine, headerLines, entityBody);
	}
	
	public HTTPReply createReply(String statusLine, String headerLines, String data) throws HTTPProtocolException {
		return new MyHTTPReply(statusLine, headerLines, data);
	}
	
	public HTTPReply createReplyFromStatusLine(String statusLine) throws HTTPProtocolException {
		return createReply(statusLine, headerLines, data);
	}
	
	public HTTPReply createReplyFromHeaderLines(String headerLines) throws HTTPProtocolException {
		return createReply(statusLine, headerLines, data);
	}
	
	public HTTPReply createReplyFromData(String data) throws HTTPProtocolException {
		return createReply(statusLine, headerLines, data);
	}
	
	public HTTPReply requestWithHandler1_0(String root, String message) throws HTTPProtocolException {
		HTTPHandler handler1_0 = new MyHTTPHandler1_0(new File(root));
		HTTPRequest request = createRequestFromRequestLine(message);
		return handler1_0.handle(request);
	}
	
	public HTTPReply requestWithHandler1_0(String host, String root, String message) throws HTTPProtocolException {
		HTTPRequest request = createRequestFromRequestLine(message);
		return new MyHTTPHandler1_0(host, new File(root)).handle(request);
	}
	
	public HTTPReply requestWithHandler1_1(String root, String message) throws HTTPProtocolException {
		HTTPHandler handler1_1 = new MyHTTPHandler1_1(new File(root));
		HTTPRequest request = createRequestFromRequestLine(message);
		return handler1_1.handle(request);
	}
	
	public HTTPReply requestWithHandler1_1(String host, String root, String message) throws HTTPProtocolException {
		HTTPRequest request = createRequestFromRequestLine(message);
		return new MyHTTPHandler1_1(host, new File(root)).handle(request);
	}
		
	public HTTPReply deleteFileWithHandler1_1(String root, HTTPRequest request) throws HTTPProtocolException {
		HTTPHandler handler1_1 = new MyHTTPHandler1_1(new File(root));
		return handler1_1.handle(request);
	}
}
