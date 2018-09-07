package com.unifi.fatture.app;

import java.util.List;

public class AppController {
	private Database database;

	public AppController(Database database) {
		this.database = database;
	}

	//Client
	public List<Client> getAllClients() {
		return database.getAllClientsList();
	}

	public Client getClientId(String id) {
		return database.findClientById(id);
	}

	public boolean addClient(Client client) {
		if (getClientId(client.getId()) != null)
			return false;
		database.saveClient(client);
		return true;
	}

	public boolean editClient(Client client) {
		if (getClientId(client.getId()) == null)
			return false;
		database.removeClientById(client.getId());
		database.saveClient(client);
		return true;
	}

	//Company
	public List<Company> getAllCompany() {
		return database.getAllCompaniesList();
	}

	public Company getCompanyId(String id) {
		return database.findCompanyById(id);
	}

	public boolean addCompany(Company company) {
		if (getCompanyId(company.getId()) != null)
			return false;
		database.saveCompany(company);
		return true;
	}

	public boolean editCompany(Company company) {
		if (getCompanyId(company.getId()) == null)
			return false;
		database.removeCompanyById(company.getId());
		database.saveCompany(company);
		return true;
	}

	//Invoice
	public List<Invoice> getAllInvoices() {
		return database.getAllInvoicesList();
	}

	public Invoice getInvoiceId(String id) {
		return database.findInvoiceById(id);
	}

	public boolean addInvoice(Invoice invoice) {
		if (getInvoiceId(invoice.getId()) != null)
			return false;

		database.saveInvoice(invoice);
		return true;
	}	

	public boolean editInvoice(Invoice invoice) {
		if (getInvoiceId(invoice.getId()) == null)
			return false;
		database.removeInvoiceById(invoice.getId());
		database.saveInvoice(invoice);
		return true;
	}
}