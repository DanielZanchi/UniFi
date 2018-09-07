package com.unifi.fatture.helptool;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.unifi.fatture.app.Client;
import com.unifi.fatture.app.Company;
import com.unifi.fatture.app.Invoice;
import com.unifi.fatture.wrappers.RedisWrapper;

public class TestHelperTool {
	private DBCollection clients;
	private DBCollection companies;
	private DBCollection invoices;
	private boolean usingMongo = true;
	private RedisWrapper redisDatabase;	

	public TestHelperTool () {
	}

	public void setUpMongoClient(MongoClient mongoClient) {
		DB db = mongoClient.getDB("company");
		db.getCollection("client").drop();
		db.getCollection("companies").drop();
		db.getCollection("invoices").drop();
		db.getCollection("printedInvoices").drop();
		clients = db.getCollection("client");
		companies = db.getCollection("companies");
		invoices = db.getCollection("invoices");
	}

	public void addClient(String id, String name, String fiscalCode, String cityResidence, String city, String zip, String country, String phone, String email/*, String birthDay*/) {
		if(usingMongo) {
			BasicDBObject document = new BasicDBObject();
			document.put("id", id);
			document.put("name", name);
			document.put("fiscalCode", fiscalCode);
			document.put("cityResidence", cityResidence);
			document.put("city", city);
			document.put("zip", zip);
			document.put("country", country);
			document.put("phone", phone);
			document.put("email", email);

			clients.insert(document);
		}else {
			Client client=new Client(id, name, fiscalCode, cityResidence, city, zip, country);
			client.setExtraParameters(phone, email);
			redisDatabase.saveClient(client);
		}
	}

	public boolean containsClient(String id, String name, String fiscalCode, String cityResidence, String city, String zip, String country, String phone, String email/*, String birthDay*/) {
		if(usingMongo) {
			BasicDBObject query = new BasicDBObject();
			query.put("id", id);
			query.put("name", name);
			query.put("fiscalCode", fiscalCode);
			query.put("cityResidence", cityResidence);
			query.put("city", city);
			query.put("zip", zip);
			query.put("country", country);
			query.put("phone", phone);
			query.put("email", email);

			return clients.find(query).hasNext();
		}else {
			return redisDatabase.findClientById(id)!=null;
		}
	}

	public void addCompany(String id, String name, String vatCode,
			String address, String city, String zipCode, String country, String phone, String email) {
		if(usingMongo) {
			BasicDBObject document = new BasicDBObject();
			document.put("id", id);
			document.put("name", name);
			document.put("vatCode", vatCode);
			document.put("address", address);
			document.put("city", city);
			document.put("zipCode", zipCode);
			document.put("country", country);
			document.put("phone", phone);
			document.put("email", email);

			companies.insert(document);
		}else {
			Company company=new Company(id, name, vatCode, address, city, zipCode, country);
			company.setExtraParameters(phone, email);
			redisDatabase.saveCompany(company);
		}
	}

	public boolean containsCompany(String id, String name, String vatCode,
			String address, String city, String zipCode, String country, String phone, String email) {
		if(usingMongo) {
			BasicDBObject query = new BasicDBObject();
			query.put("id", id);
			query.put("name", name);
			query.put("vatCode", vatCode);
			query.put("address", address);
			query.put("city", city);
			query.put("zipCode", zipCode);
			query.put("country", country);
			query.put("phone", phone);
			query.put("email", email);
			return companies.find(query).hasNext();
		}else {
			return redisDatabase.findCompanyById(id)!=null;
		}
	}

	public void addInvoice(String id, String name, String price, String description) {
		if(usingMongo) {
			BasicDBObject document = new BasicDBObject();
			document.put("id", id);
			document.put("name", name);
			document.put("price", price);
			document.put("description", description);
			invoices.insert(document);
		}else {
			redisDatabase.saveInvoice(new Invoice(id, name, price, description));
		}		
	}

	public void addTwoClients() {
		addClient("1", "first", "firstFC", "firstCR", "firstCity", "firstZip", "firstCountry", "firstPhone", "firstEmail");
		addClient("2", "second", "secondFC", "secondCR", "secondCity", "secondZip", "secondCountry", "secondPhone", "secondEmail");
	}

	public void addTwoCompanies() {
		addCompany("1", "nameC1", "vatCode1", "address1", "city1", "zipCode1", "country1", "phone1", "email1");
		addCompany("2", "nameC2", "vatCode2", "address2", "city2", "zipCode2", "country2", "phone2", "email2");
	}

	public void addTwoInvoices() {
		addInvoice("1", "nameI1", "100", "basic invoice type1");
		addInvoice("2", "nameI2", "200", "basic invoice type2");
	}

	public boolean containsInvoice(String id, String name, String price, String description) {
		if(usingMongo) {
			BasicDBObject query = new BasicDBObject();
			query.put("id", id);
			query.put("name", name);
			query.put("price", price);
			query.put("description", description);
			return invoices.find(query).hasNext();
		}else {
			return redisDatabase.findInvoiceById(id)!=null;
		}
	}

	public RedisWrapper usingRedis() {
		usingMongo = false;
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new ClassPathResource("spring-configuration.xml").getPath());
		this.redisDatabase = (RedisWrapper)context.getBean("redisWrapper");
		context.close();
		return redisDatabase;
	}
}