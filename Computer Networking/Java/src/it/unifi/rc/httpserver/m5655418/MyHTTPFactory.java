package it.unifi.rc.httpserver.m5655418;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;

import it.unifi.rc.httpserver.HTTPFactory;
import it.unifi.rc.httpserver.HTTPHandler;
import it.unifi.rc.httpserver.HTTPInputStream;
import it.unifi.rc.httpserver.HTTPOutputStream;
import it.unifi.rc.httpserver.HTTPServer;

/**
 * Class that implements HTTPFactory
 * 
 * @author Daniel Zanchi, 5655418
 *
 */
public class MyHTTPFactory implements HTTPFactory {

	/**
	 * 
	 * @param is inputStream to return
	 * @return {@link MyHTTPInputStream} created by the costructor
	 */
	@Override
	public HTTPInputStream getHTTPInputStream(InputStream is) {
		return new MyHTTPInputStream(is);
	}

	/**
	 * 
	 * @param os outputStream to return
	 * @return {@link MyHTTPOutputStream} created by the costructor
	 */
	@Override
	public HTTPOutputStream getHTTPOutputStream(OutputStream os) {
		return new MyHTTPOutputStream(os);
	}

	@Override
	public HTTPServer getHTTPServer(int port, int backlog, InetAddress address, HTTPHandler... handlers) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 * @return {@link MyHTTPHandler1_0} with {@code root} as parameter.
	 */
	@Override
	public HTTPHandler getFileSystemHandler1_0(File root) {
		return new MyHTTPHandler1_0(root);
	}

	/**
	 * 
	 * @return {link MyHTTPHandler1_0} with {@code host} and {@code root} as parameters.
	 */
	@Override
	public HTTPHandler getFileSystemHandler1_0(String host, File root) {
		return new MyHTTPHandler1_0(host, root);
	}

	/**
	 * @return {@link MyHTTPHandler1_1} with {@code root} as parameter.
	 */
	@Override
	public HTTPHandler getFileSystemHandler1_1(File root) {
		return new MyHTTPHandler1_1(root);
	}

	/**
	 * @return {link MyHTTPHandler1_1} with {@code host} and {@code root} as parameters.
	 */
	@Override
	public HTTPHandler getFileSystemHandler1_1(String host, File root) {
		return new MyHTTPHandler1_1(host, root);
	}

	@Override
	public HTTPHandler getProxyHandler() {
		// TODO Auto-generated method stub
		return null;
	}

}
