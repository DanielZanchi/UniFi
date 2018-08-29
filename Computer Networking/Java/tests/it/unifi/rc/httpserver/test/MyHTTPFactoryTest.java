package it.unifi.rc.httpserver.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;

import org.junit.Before;
import org.junit.Test;

import it.unifi.rc.httpserver.m5655418.MyHTTPFactory;

public class MyHTTPFactoryTest {

	private MyHTTPFactory httpFactory;
	
	@Before
	public void init() {
		httpFactory = new MyHTTPFactory();
	}
	
	@Test
	public void getHTTPServerTest() {
		try {
			assertNull(httpFactory.getHTTPServer(80, 0, InetAddress.getLocalHost(), null));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getHandler1_0NotNullTest() {
		assertNotNull(httpFactory.getFileSystemHandler1_0(new File("root")));
	}
	
	@Test
	public void getHandler1_0WithHostNotNullTest() {
		assertNotNull(httpFactory.getFileSystemHandler1_0("host", new File("root")));
	}
	
	@Test
	public void getHandler1_1NotNullTest() {
		assertNotNull(httpFactory.getFileSystemHandler1_1(new File("root")));
	}
	
	@Test
	public void getHandler1_1WithHostNotNullTest() {
		assertNotNull(httpFactory.getFileSystemHandler1_1("host", new File("root")));
	}
	
	@Test
	public void getProxyHandlerNullTest() {
		assertNull(httpFactory.getProxyHandler());
	}
	
	@Test
	public void getHTTPInputStreamTest() {
		BufferedInputStream bufferedInputStream = getEmptyInputStream();
		assertTrue(httpFactory.getHTTPInputStream(bufferedInputStream) != null);
	}
	
	@Test
	public void getHTTPOutputStreamTest() {
		BufferedOutputStream bufferedOutputStream = getEmptyOutputStream();
		assertTrue(httpFactory.getHTTPOutputStream(bufferedOutputStream)!=null);
	}
	
	
	
	private BufferedInputStream getEmptyInputStream() {
		return new BufferedInputStream(new InputStream() {
			@Override
			public int read() throws IOException {
				return 0;
			}
		});
	}
	
	private BufferedOutputStream getEmptyOutputStream() {
		return new BufferedOutputStream(new OutputStream() {
			@Override
			public void write(int b) throws IOException {
			}
		});
	}
}
