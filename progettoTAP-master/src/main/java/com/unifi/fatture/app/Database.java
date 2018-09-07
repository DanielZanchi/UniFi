package com.unifi.fatture.app;

import java.util.List;

public interface Database {
	public List<Client> getAllClientsList();

	public Client findClientById(String id);

	public void saveClient(Client client);

	public List<Company> getAllCompaniesList();

	public Company findCompanyById(String id);

	public void saveCompany(Company company);

	public List<Invoice> getAllInvoicesList();

	public Invoice findInvoiceById(String id);

	public void saveInvoice(Invoice invoice);

	public void removeCompanyById(String id);

	public void removeClientById(String id);

	public void removeInvoiceById(String id);
}