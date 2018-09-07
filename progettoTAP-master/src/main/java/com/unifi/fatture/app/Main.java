package com.unifi.fatture.app;

import java.io.IOException;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

import com.mongodb.MongoClient;
import com.unifi.fatture.ui.MainWindowUI;
import com.unifi.fatture.wrappers.MongoWrapper;
import com.unifi.fatture.wrappers.RedisWrapper;

public class Main {
	private static final Logger LOGGER = Logger.getLogger(Main.class);
	public static void main(String[] args) throws IOException {
		DatabaseUiComunication mongoUiComunication;
		String mongoHost = "localhost";
		BasicConfigurator.configure();

		if(args.length==1 && args[0].equals("redis")) {//redis
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new ClassPathResource("spring-configuration.xml").getPath());
			Database database = (RedisWrapper)context.getBean("redisWrapper");
			context.close();
			mongoUiComunication = new DatabaseUiComunication(database);

		} else {//mongo
			if (args.length > 0) {
				mongoHost = args[0];			
			}
			MongoClient mongoClient = new MongoClient(mongoHost, 27017);
			Database database = new MongoWrapper(mongoClient);
			mongoUiComunication = new DatabaseUiComunication(database);
		}

		//Launch UI
		MainWindowUI mainWindowUI = null;
		try {
			mainWindowUI = new MainWindowUI(mongoUiComunication);
			mainWindowUI.toString();
		}catch (Exception e) {
			LOGGER.info("In docker container, gui not running");
		}
		LOGGER.info("Fatture-app terminates");
	}
}