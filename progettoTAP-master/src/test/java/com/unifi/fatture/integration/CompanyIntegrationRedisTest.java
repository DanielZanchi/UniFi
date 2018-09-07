package com.unifi.fatture.integration;

import java.io.IOException;

import org.junit.After;
import org.slf4j.LoggerFactory;

import com.unifi.fatture.app.AppController;
import com.unifi.fatture.app.Database;
import com.unifi.fatture.helptool.TestHelperTool;

import ch.qos.logback.classic.LoggerContext;
import redis.embedded.RedisServer;

public class CompanyIntegrationRedisTest extends AbstractCompanyIntegrationTest {
	private RedisServer redisServer;

	public void init() throws IOException {
		redisServer = new RedisServer(6379);
		redisServer.start();
		testHelper = new TestHelperTool();
		Database database = testHelper.usingRedis();
		companyController = new AppController(database);
		LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
		loggerContext.stop();
	}

	@After
	public void stopDBServer() {
		redisServer.stop();
	}
}