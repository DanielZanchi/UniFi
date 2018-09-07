package com.unifi.fatture.integration;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;

import org.junit.Before;
import org.junit.Test;

import com.unifi.fatture.app.AddClientToDatabaseParameter;
import com.unifi.fatture.app.AddCompanyToDatabaseParameter;
import com.unifi.fatture.app.Client;
import com.unifi.fatture.app.Company;
import com.unifi.fatture.app.DatabaseUiComunication;
import com.unifi.fatture.app.Invoice;
import com.unifi.fatture.app.PDFCreator;

public abstract class AbstractDatabaseUiComunicationTest {
	protected DatabaseUiComunication myDatabaseUiComunication;

	public abstract void setUpDatabase() throws UnknownHostException;

	@Before
	public void setUp() throws UnknownHostException {
		setUpDatabase();
	}

	@Test
	public void addClientToDbTest() {
		assertEquals(true, addTestClient());
	}

	@Test
	public void clientsCountWithNoClientsTest() {
		assertEquals(0, myDatabaseUiComunication.getClientsCount());
	}

	@Test
	public void clientsCountWithOneClientTest() {
		addTestClient();
		assertEquals(1, myDatabaseUiComunication.getClientsCount());
	}

	@Test
	public void testGetListSizeOfClientsWithNoClients() {
		assertEquals(0, myDatabaseUiComunication.getSavedClients().size());
	}

	@Test
	public void testGetListSizeOfClientsWithOneClient() {
		addTestClient();
		assertEquals(1, myDatabaseUiComunication.getSavedClients().size());
	}

	@Test
	public void testGetListOfClientsWithOneClientCheckName() {
		addTestClient();
		assertEquals("name", myDatabaseUiComunication.getSavedClients().get(0).getName());
	}

	@Test 
	public void testGetCurrentSelectedClientWithNoClientSelected() {
		assertEquals(null, myDatabaseUiComunication.getCurrentSelectedClient());
	}

	@Test 
	public void testGetCurrentSelectedClientWithClientSelected() {
		Client client = setTestClientToCurrentSelected();
		assertEquals(myDatabaseUiComunication.getCurrentSelectedClient(), client);
	}

	@Test
	public void getCurrentSelectedClientWithClientSelectedNameTest() {
		Client client = setTestClientToCurrentSelected();
		assertEquals(myDatabaseUiComunication.getCurrentSelectedClient().getName(), client.getName());
	}

	private boolean addTestClient() {
		return myDatabaseUiComunication.addClientToDatabase("name", "fiscalCode", new AddClientToDatabaseParameter("residence", "city", "zip", "country"), "phone", "email");
	}

	private Client setTestClientToCurrentSelected() {
		Client client = new Client("1", "name", "fiscalCode", "residence", "city", "zip", "country");
		client.setExtraParameters("phone", "email");
		myDatabaseUiComunication.setCurrentSelectedClient(client);
		return client;
	}

	//Company
	@Test 
	public void getCurrentSelectedCompanyWithNoCompanySelected() {
		assertEquals(null, myDatabaseUiComunication.getCurrentSelectedCompany());
	}

	@Test 
	public void getCurrentSelectedCompanyWithCompanySelected() {
		Company company = setTestCompanyToCurrentSelected();
		assertEquals(myDatabaseUiComunication.getCurrentSelectedCompany(), company);
	}

	private Company setTestCompanyToCurrentSelected() {
		Company company = new Company("1", "nameComp", "vatCodeComp", "addressComp", "cityComp", "zipCodeComp", "countryComp");
		company.setExtraParameters("phoneComp", "emailComp");
		myDatabaseUiComunication.setCurrentSelectedCompany(company);
		return company;
	}

	@Test 
	public void getCurrentSelectedCompanyWithCompanySelectedNameTest() {
		Company company = setTestCompanyToCurrentSelected();
		assertEquals(myDatabaseUiComunication.getCurrentSelectedCompany().getName(), company.getName());
	}

	@Test (expected = NullPointerException.class)
	public void editCompanyWhenNoCompanyInDBTest() {
		myDatabaseUiComunication.editCompanyFromDatabase("nameEdited", "vatCodeEdited",  new AddCompanyToDatabaseParameter("addressEdited", "cityEdited", "zipCodeEdited", "countryEdited"), "phoneEdited", "emailEdited");
	}

	@Test
	public void addCompanyToDbTest() {
		assertEquals(true, addTestCompany());
	}

	@Test
	public void companiesCountWithNoCompaniesTest() {
		assertEquals(0, myDatabaseUiComunication.getCompaniesCount());
	}

	@Test
	public void companiesCountWithOneCompanyTest() {
		addTestCompany();
		assertEquals(1, myDatabaseUiComunication.getCompaniesCount());
	}

	@Test
	public void testGetListSizeOfCompaniesWithNoCompanies() {
		assertEquals(0, myDatabaseUiComunication.getSavedCompanies().size());
	}

	@Test
	public void testGetListSizeOfCompaniesWithOneCompany() {
		addTestCompany();
		assertEquals(1, myDatabaseUiComunication.getSavedCompanies().size());
	}

	@Test
	public void testGetListOfCompaniesWithOneCompanyCheckName() {
		addTestCompany();
		assertEquals("name", myDatabaseUiComunication.getSavedCompanies().get(0).getName());
	}

	private boolean addTestCompany() {
		return myDatabaseUiComunication.addCompanyToDatabase("name", "vatCode", new AddCompanyToDatabaseParameter("address", "city", "zip", "country"), "phone", "email");
	}

	//Invoice
	@Test 
	public void getCurrentSelectedInvoiceWithNoInvoiceSelected() {
		assertEquals(null, myDatabaseUiComunication.getCurrentSelectedInvoice());
	}

	@Test 
	public void getCurrentSelectedInvoiceWithInvoiceSelected() {
		Invoice invoice = setTestInvoiceToCurrentSelected();
		assertEquals(myDatabaseUiComunication.getCurrentSelectedInvoice(), invoice);
	}

	private Invoice setTestInvoiceToCurrentSelected() {
		Invoice invoice = new Invoice("1", "invoiceName", "15", "invoiceDescription");
		myDatabaseUiComunication.setCurrentSelectedInvoice(invoice);
		return invoice;
	}

	@Test 
	public void getCurrentSelectedInvoiceWithInvoiceSelectedNameTest() {
		Invoice invoice = setTestInvoiceToCurrentSelected();
		assertEquals(myDatabaseUiComunication.getCurrentSelectedInvoice().getName(), invoice.getName());
	}

	@Test
	public void addInvoiceToDbTest() {
		assertEquals(true, addTestInvoice());
	}

	@Test
	public void invoicesCountWithNoInvoicesTest() {
		assertEquals(0, myDatabaseUiComunication.getInvoicesCount());
	}

	@Test
	public void invoicesCountWithOneInvoiceTest() {
		addTestInvoice();
		assertEquals(1, myDatabaseUiComunication.getInvoicesCount());
	}

	@Test
	public void testGetListSizeOfInvoicesWithNoInvoices() {
		assertEquals(0, myDatabaseUiComunication.getSavedInvoices().size());
	}

	@Test
	public void testGetListSizeOfInvoicesWithOneInvoice() {
		addTestInvoice();
		assertEquals(1, myDatabaseUiComunication.getSavedInvoices().size());
	}

	@Test
	public void testGetListOfInvoicesWithOneInvoiceCheckName() {
		addTestInvoice();
		assertEquals("name", myDatabaseUiComunication.getSavedInvoices().get(0).getName());
	}

	private boolean addTestInvoice() {
		return myDatabaseUiComunication.addInvoiceToDatabase("name", "description", "10");
	}

	@Test 
	public void printSelectedWithNoSelectedTest() throws Exception {
		assertEquals(false, myDatabaseUiComunication.printSelected());
	}

	@Test
	public void printSelectedWithSelectedTest() throws Exception {
		setTestCompanyToCurrentSelected();
		setTestClientToCurrentSelected();
		setTestInvoiceToCurrentSelected();
		boolean created = myDatabaseUiComunication.printSelected();
		assertEquals(true, created);
		if(created) {
			new File("Invoice.pdf").delete();
		}
	}

	@Test
	public void afterPrintingCompanyCreatedInvoicesValueIncreasesTest() throws Exception {
		Company company = setTestCompanyToCurrentSelected();
		setTestClientToCurrentSelected();
		setTestInvoiceToCurrentSelected();
		boolean created = myDatabaseUiComunication.printSelected();
		assertEquals(2, company.getCreatedInvoices());
		if(created) {
			new File("Invoice.pdf").delete();
		}
	}

	@Test
	public void beforeCompanyCreatedInvoicesValueIsOneTest() throws Exception {
		Company company = setTestCompanyToCurrentSelected();
		setTestClientToCurrentSelected();
		setTestInvoiceToCurrentSelected();
		assertEquals(1, company.getCreatedInvoices());
	}

	@Test (expected = Exception.class)
	public void printSelectedWithNullClient() throws IOException {
		Client client = null;
		myDatabaseUiComunication.setCurrentSelectedClient(client);
		Company company = setTestCompanyToCurrentSelected();
		Invoice invoice = setTestInvoiceToCurrentSelected();
		new PDFCreator(company, client, invoice);
	}

	@Test (expected = Exception.class)
	public void printSelectedWithNullCompany() throws IOException {
		Company company = null;
		myDatabaseUiComunication.setCurrentSelectedCompany(company);
		Client client = setTestClientToCurrentSelected();
		Invoice invoice = setTestInvoiceToCurrentSelected();
		new PDFCreator(company, client, invoice);
	}

	@Test (expected = Exception.class)
	public void printSelectedWithNullInvoice() throws IOException {
		Invoice invoice = null;
		myDatabaseUiComunication.setCurrentSelectedInvoice(invoice);
		Company company = setTestCompanyToCurrentSelected();
		Client client = setTestClientToCurrentSelected();
		new PDFCreator(company, client, invoice);
	}
}