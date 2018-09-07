package com.unifi.fatture.ui;

import org.slf4j.LoggerFactory;

import com.github.fakemongo.Fongo;
import com.mongodb.MongoClient;
import com.unifi.fatture.app.Database;
import com.unifi.fatture.app.DatabaseUiComunication;
import com.unifi.fatture.ui.MainWindowUI;
import com.unifi.fatture.wrappers.MongoWrapper;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;

public class MainWindowUsingFongo {
	protected MainWindowUI frame;

	public void init() {
		Fongo fongo = new Fongo("mongo server 1");
		MongoClient mongoClient = fongo.getMongo();
		LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
		ch.qos.logback.classic.Logger rootLogger = loggerContext.getLogger("com.mongodb.FongoDBCollection");
		rootLogger.setLevel(Level.OFF);
		Database database = new MongoWrapper(mongoClient);
		frame = new MainWindowUI(new DatabaseUiComunication(database));
	}
}