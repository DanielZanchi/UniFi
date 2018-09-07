package com.unifi.fatture.wrappers;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.springframework.data.redis.core.RedisTemplate;

import com.unifi.fatture.app.Client;
import com.unifi.fatture.app.Company;
import com.unifi.fatture.app.Database;
import com.unifi.fatture.app.Invoice;

public class RedisWrapper implements Database {
	private RedisTemplate<String, Object> redisTemplate;

	private static final String CLIENTKEY = "client_key_redis"; 
	private static final String COMPANYKEY = "company_key_redis"; 
	private static final String INVOICEKEY = "invoice_key_redis"; 

	public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	// Client
	@Override
	public Client findClientById(String id) {
		return (Client) redisTemplate.opsForHash().get(CLIENTKEY, id);
	}

	@Override
	public void saveClient(Client client) {
		redisTemplate.opsForHash().put(CLIENTKEY, client.getId(), client);
	}

	@Override
	public List<Client> getAllClientsList() {
		LinkedList<Client> clients = new LinkedList<>();
		Iterator<Object> iterator = redisTemplate.opsForHash().entries(CLIENTKEY).keySet().iterator();
		while(iterator.hasNext()) {
			clients.add((Client) redisTemplate.opsForHash().entries(CLIENTKEY).get(iterator.next()));
		}
		return clients;
	}

	@Override
	public void removeClientById(String id) {
		redisTemplate.opsForHash().delete(CLIENTKEY, id);
	}

	//Company
	@Override
	public Company findCompanyById(String id) {
		return (Company) redisTemplate.opsForHash().get(COMPANYKEY, id);
	}

	@Override
	public void saveCompany(Company company) {
		redisTemplate.opsForHash().put(COMPANYKEY, company.getId(), company);
	}

	@Override
	public List<Company> getAllCompaniesList() {
		LinkedList<Company> companies = new LinkedList<>();
		Iterator<Object> iterator = redisTemplate.opsForHash().entries(COMPANYKEY).keySet().iterator();
		while(iterator.hasNext()) {
			companies.add((Company) redisTemplate.opsForHash().entries(COMPANYKEY).get(iterator.next()));
		}
		return companies;
	}

	@Override
	public void removeCompanyById(String id) {
		redisTemplate.opsForHash().delete(COMPANYKEY, id);
	}

	//Invoice
	@Override
	public Invoice findInvoiceById(String id) {
		return (Invoice) redisTemplate.opsForHash().get(INVOICEKEY, id);
	}

	@Override
	public void saveInvoice(Invoice invoice) {
		redisTemplate.opsForHash().put(INVOICEKEY, invoice.getId(), invoice);
	}

	@Override
	public List<Invoice> getAllInvoicesList() {
		LinkedList<Invoice> invoices = new LinkedList<>();
		Iterator<Object> iterator = redisTemplate.opsForHash().entries(INVOICEKEY).keySet().iterator();
		while(iterator.hasNext()) {
			invoices.add((Invoice) redisTemplate.opsForHash().entries(INVOICEKEY).get(iterator.next()));
		}
		return invoices;
	}

	@Override
	public void removeInvoiceById(String id) {
		redisTemplate.opsForHash().delete(INVOICEKEY, id);
	}
}