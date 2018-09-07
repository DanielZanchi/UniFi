package com.unifi.fatture.integration;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.UnknownHostException;

import org.junit.After;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

import com.unifi.fatture.app.Database;
import com.unifi.fatture.app.DatabaseUiComunication;
import com.unifi.fatture.wrappers.RedisWrapper;

import ch.qos.logback.classic.LoggerContext;
import redis.embedded.RedisServer;

public class DatabaseUiComunicationRedisTest extends AbstractDatabaseUiComunicationTest{
	private RedisServer redisServer;

	@Override
	public void setUpDatabase() throws UnknownHostException {
		try {
			redisServer = new RedisServer(6379);
			redisServer.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
		loggerContext.stop();
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new ClassPathResource("spring-configuration.xml").getPath());
		Database database = (RedisWrapper)context.getBean("redisWrapper");
		context.close();
		myDatabaseUiComunication = new DatabaseUiComunication(database);
	}

	@Test
	public void testDatabaseIsInstanceOfRedisWrapper() {
		assertEquals(RedisWrapper.class, myDatabaseUiComunication.getCurrentDatabaseClass());
	}

	@After
	public void stopRedis() {
		redisServer.stop();
	}
}