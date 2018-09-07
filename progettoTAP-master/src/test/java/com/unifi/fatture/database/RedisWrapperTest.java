package com.unifi.fatture.database;

import java.io.IOException;
import java.net.UnknownHostException;

import org.junit.After;
import org.slf4j.LoggerFactory;

import com.unifi.fatture.helptool.TestHelperTool;

import ch.qos.logback.classic.LoggerContext;
import redis.embedded.RedisServer;

public class RedisWrapperTest extends AbstractWrapperTest {
	private RedisServer redisServer;

	@Override
	public void init() throws UnknownHostException {
		try {
			redisServer = new RedisServer(6379);
			redisServer.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
		loggerContext.stop();
		testHelper = new TestHelperTool();
		database = testHelper.usingRedis();
	}

	@After
	public void stopRedis() {
		redisServer.stop();
	}
}