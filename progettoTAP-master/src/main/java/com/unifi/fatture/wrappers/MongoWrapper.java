package com.unifi.fatture.wrappers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.jongo.Jongo;
import org.jongo.MongoCollection;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.unifi.fatture.app.Client;
import com.unifi.fatture.app.Company;
import com.unifi.fatture.app.Database;
import com.unifi.fatture.app.Invoice;

public class MongoWrapper implements Database {	
	private MongoCollection clients;
	private MongoCollection companies;
	private MongoCollection invoices;
	private static final String ID = "{id: #}";

	public MongoWrapper(MongoClient mc) {
		DB db = mc.getDB("company");

		Jongo jongo = new Jongo(db);
		clients = jongo.getCollection("client");
		companies = jongo.getCollection("companies");
		invoices = jongo.getCollection("invoices");
	}	

	//Clients
	@Override
	public List<Client> getAllClientsList() {
		Iterable<Client> iterable = clients.find().as(Client.class);
		return StreamSupport.
				stream(iterable.spliterator(), false).
				collect(Collectors.toList());
	}

	@Override
	public Client findClientById(String id) {
		return clients.findOne(ID, id).as(Client.class);
	}

	@Override
	public void saveClient(Client client) {
		clients.save(client);
	}

	@Override
	public void removeClientById(String id) {
		clients.remove(ID, id);
	}

	//Company
	@Override
	public List<Company> getAllCompaniesList() {
		Iterable<Company> iterable = companies.find().as(Company.class);
		return StreamSupport.
				stream(iterable.spliterator(), false).
				collect(Collectors.toList());
	}

	@Override
	public Company findCompanyById(String id) {
		return companies.findOne(ID, id).as(Company.class);
	}

	@Override
	public void saveCompany(Company company) {
		companies.save(company);
	}

	@Override
	public void removeCompanyById(String id) {
		companies.remove(ID, id);
	}

	//Invoice
	@Override
	public List<Invoice> getAllInvoicesList() {
		Iterable<Invoice> iterable = invoices.find().as(Invoice.class);
		return StreamSupport.
				stream(iterable.spliterator(), false).
				collect(Collectors.toList());
	}

	@Override
	public Invoice findInvoiceById(String id) {
		return invoices.findOne(ID, id).as(Invoice.class);
	}

	@Override
	public void saveInvoice(Invoice invoice) {
		invoices.save(invoice);
	}

	@Override
	public void removeInvoiceById(String id) {
		invoices.remove(ID, id);
	}
}