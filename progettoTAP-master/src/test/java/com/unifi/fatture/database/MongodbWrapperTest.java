package com.unifi.fatture.database;

import org.slf4j.LoggerFactory;

import com.github.fakemongo.Fongo;
import com.mongodb.MongoClient;
import com.unifi.fatture.helptool.TestHelperTool;
import com.unifi.fatture.wrappers.MongoWrapper;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;

public class MongodbWrapperTest extends AbstractWrapperTest {
	@Override
	public void init() {
		Fongo fongo = new Fongo("mongo server 1");
		MongoClient mongoClient = fongo.getMongo();
		LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
		ch.qos.logback.classic.Logger rootLogger = loggerContext.getLogger("com.mongodb.FongoDBCollection");
		rootLogger.setLevel(Level.OFF);
		testHelper = new TestHelperTool();
		testHelper.setUpMongoClient(mongoClient);
		database = new MongoWrapper(mongoClient);
	}
}