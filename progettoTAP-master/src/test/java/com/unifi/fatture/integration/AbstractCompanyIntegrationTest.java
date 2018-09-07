package com.unifi.fatture.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.unifi.fatture.app.AppController;
import com.unifi.fatture.app.Client;
import com.unifi.fatture.app.Company;
import com.unifi.fatture.app.Invoice;
import com.unifi.fatture.helptool.TestHelperTool;

public abstract class AbstractCompanyIntegrationTest {
	protected AppController companyController;
	protected TestHelperTool testHelper;

	public abstract void init() throws IOException;

	@Before
	public void setUp() throws Exception {
		init();
	}

	//client
	private Client addTestClientToDB() {
		testHelper.addClient("1", "test", "testFC", "testCR", "testCity", "testZip", "testCountry", "testPhone", "testEmail");
		Client client = companyController.getClientId("1");
		assertNotNull(client);
		return client;
	}

	@Test
	public void addTestClientToDBWhenClientAlreadyInDB() {
		Client client=new Client("1", "test", "testFC", "testCR", "testCity", "testZip", "testCountry");
		client.setExtraParameters("testPhone", "testEmail");
		companyController.addClient(client);
		client=new Client("1", "test", "testFC", "testCR", "testCity", "testZip", "testCountry");
		client.setExtraParameters("testPhone", "testEmail");
		boolean added = companyController.addClient(client);
		assertEquals(false, added);
	}

	@Test
	public void testGetAllClientsWhenThereAreNoClients() {
		List<Client> allClients = companyController.getAllClients();
		assertEquals(0, allClients.size());
	}

	@Test
	public void testGetAllClientsWhenThereIsOneClient() {
		testHelper.addClient("1", "test", "testFC", "testCR", "testCity", "testZip", "testCountry", "testPhone", "testEmail");
		List<Client> allClients = companyController.getAllClients();
		assertEquals(1, allClients.size());	
	}

	@Test
	public void testGetClientByIdWhenClientIsNotThere() {
		testHelper.addClient("1", "test", "testFC", "testCR", "testCity", "testZip", "testCountry", "testPhone", "testEmail");
		Client client = companyController.getClientId("2");
		assertNull(client);
	}

	@Test
	public void testGetClientByIdWhenClientIsThere() {
		Client client = addTestClientToDB();
		assertEquals("test", client.getName());
	}

	@Test
	public void testGetClientByIdWithRightFiscalCode() {
		Client client = addTestClientToDB();
		assertEquals("testFC", client.getFiscalCode());
	}

	@Test
	public void testGetClientByIdWithWrongFiscalCode() {
		Client client = addTestClientToDB();
		assertNotEquals("wrongTestFC", client.getFiscalCode());
	}

	@Test
	public void testGetClientByIdWithRightCityResidence() {
		Client client = addTestClientToDB();
		assertEquals("testCR", client.getCityResidence());
	}

	@Test
	public void testGetClientByIdWithWrongCityResidence() {
		Client client = addTestClientToDB();
		assertNotEquals("wrongTestCR", client.getCityResidence());
	}

	@Test
	public void testGetClientByIdWithRightPhone() {
		Client client = addTestClientToDB();
		assertEquals("testPhone", client.getPhone());
	}

	@Test
	public void testGetClientByIdWithWrongPhone() {
		Client client = addTestClientToDB();
		assertNotEquals("wrongTestPhone", client.getPhone());
	}

	@Test
	public void testGetClientByIdWithRightEmail() {
		Client client = addTestClientToDB();
		assertEquals("testEmail", client.getEmail());
	}

	@Test
	public void testGetClientByIdWithWrongEmail() {
		Client client = addTestClientToDB();
		assertNotEquals("wrongTestEmail", client.getEmail());
	}

	@Test
	public void testGetClientByIdWithRightCity() {
		Client client = addTestClientToDB();
		assertEquals("testCity", client.getCity());
	}

	@Test
	public void testGetClientByIdWithWrongCity() {
		Client client = addTestClientToDB();
		assertNotEquals("wrongTestCity", client.getCity());
	}


	@Test
	public void testGetClientByIdWithRightZip() {
		Client client = addTestClientToDB();
		assertEquals("testZip", client.getZip());
	}

	@Test
	public void testGetClientByIdWithWrongZip() {
		Client client = addTestClientToDB();
		assertNotEquals("wrongTestZip", client.getZip());
	}

	@Test
	public void testGetClientByIdWithRightCountry() {
		Client client = addTestClientToDB();
		assertEquals("testCountry", client.getCountry());
	}

	@Test
	public void testGetClientByIdWithWrongCountry() {
		Client client = addTestClientToDB();
		assertNotEquals("wrongTestCountry", client.getCountry());
	}

	@Test
	public void testEditClientWhileNoClientsInDB() {
		Client client=new Client("1", "test", "testFC", "testCR", "testCity", "testZip", "testCountry");
		client.setExtraParameters("testPhone", "testEmail");
		boolean edited = companyController.editClient(client);
		assertFalse(edited);
	}

	@Test
	public void testEditClientName() {
		Client client = addTestClientToDB();
		client.setName("EditedName");
		companyController.editClient(client);
		assertEquals("EditedName", companyController.getClientId("1").getName());
	}

	@Test
	public void testEditClientNameWithTwoClientsInDb() {
		Client client = addTestClientToDB();
		testHelper.addClient("2", "test2", "testFC2", "testCR2", "testCity2", "testZip2", "testCountry2", "testPhone2", "testEmail2");
		client.setName("EditedName");
		companyController.editClient(client);
		assertEquals("EditedName", companyController.getClientId("1").getName());
	}

	//company	
	private Company addTestCompanyToDB() {
		testHelper.addCompany("1", "nameC1", "vatCode1", "address1", "city1", "zipCode1", "country1", "phone1", "email1");
		Company company = companyController.getCompanyId("1");
		assertNotNull(company);
		return company;
	}	

	@Test
	public void addTestCompanyToDBWhenCompanyAlreadyInDB() {
		Company company=new Company("1", "nameC1", "vatCode1", "address1", "city1", "zipCode1", "country1");
		company.setExtraParameters( "phone1", "email1");
		companyController.addCompany(company);
		company=new Company("1", "nameC1", "vatCode1", "address1", "city1", "zipCode1", "country1");
		company.setExtraParameters( "phone1", "email1");
		boolean added = companyController.addCompany(company);
		assertEquals(false, added);
	}

	@Test
	public void testGetAllCompaniesWhenThereAreNoCompanies() {
		List<Company> allCompanies = companyController.getAllCompany();
		assertEquals(0, allCompanies.size());
	}

	@Test
	public void testGetAllCompaniesWhenThereIsOneCompany() {
		testHelper.addCompany("1", "nameC1", "vatCode1", "address1", "city1", "zipCode1", "country1", "phone1", "email1");
		List<Company> allCompanies = companyController.getAllCompany();
		assertEquals(1, allCompanies.size());	
	}

	@Test
	public void testGetCompanyByIdWhenCompanyIsNotThere() {
		testHelper.addCompany("1", "testName", "testVat", "testAddress", "testCity", "testZip", "testCountry", "testPhone", "testEmail");
		Company company = companyController.getCompanyId("2");
		assertNull(company);
	}

	@Test
	public void testGetCompanyByIdWhenCompanyIsThere() {
		Company company = addTestCompanyToDB();
		assertEquals("nameC1", company.getName());
	}

	@Test
	public void testGetCompanyByIdWithRightVatCode() {
		Company company = addTestCompanyToDB();
		assertEquals("vatCode1", company.getVatCode());
	}

	@Test
	public void testGetCompanyByIdWithWrongVatCode() {
		Company company = addTestCompanyToDB();
		assertNotEquals("wrongVatCode", company.getVatCode());
	}

	@Test
	public void testGetCompanyByIdWithRightCity() {
		Company company = addTestCompanyToDB();
		assertEquals("city1", company.getCity());
	}

	@Test
	public void testGetCompanyByIdWithWrongCity() {
		Company company = addTestCompanyToDB();
		assertNotEquals("wrongCity", company.getCity());
	}

	@Test
	public void testGetCompanyByIdWithRightAddress() {
		Company company = addTestCompanyToDB();
		assertEquals("address1", company.getAddress());
	}

	@Test
	public void testGetCompanyByIdWithWrongAddress() {
		Company company = addTestCompanyToDB();
		assertNotEquals("wrongAddress", company.getAddress());
	}


	@Test
	public void testGetCompanyByIdWithRightZipCode() {
		Company company = addTestCompanyToDB();
		assertEquals("zipCode1", company.getZipCode());
	}

	@Test
	public void testGetCompanyByIdWithWrongZipCode() {
		Company company = addTestCompanyToDB();
		assertNotEquals("wrongZipCode", company.getZipCode());
	}

	@Test
	public void testGetCompanyByIdWithRightCountry() {
		Company company = addTestCompanyToDB();
		assertEquals("country1", company.getCountry());
	}

	@Test
	public void testGetCompanyByIdWithWrongCountry() {
		Company company = addTestCompanyToDB();
		assertNotEquals("wrongCountry", company.getCountry());
	}

	@Test
	public void testGetCompanyByIdWithRightPhone() {
		Company company = addTestCompanyToDB();
		assertEquals("phone1", company.getPhone());
	}

	@Test
	public void testGetCompanyByIdWithWrongPhone() {
		Company company = addTestCompanyToDB();
		assertNotEquals("wrongPhone", company.getPhone());
	}

	@Test
	public void testGetCompanyByIdWithRightEmail() {
		Company company = addTestCompanyToDB();
		assertEquals("email1", company.getEmail());
	}

	@Test
	public void testGetCompanyByIdWithWrongEmail() {
		Company company = addTestCompanyToDB();
		assertNotEquals("wrongEmail", company.getEmail());
	}

	@Test
	public void testEditCompanyWhileNoCompaniesInDB() {
		Company company = new Company("1", "nameC1", "vatCode1", "address1", "city1", "zipCode1", "country1");
		company.setExtraParameters( "phone1", "email1");
		boolean edited = companyController.editCompany(company);
		assertFalse(edited);
	}

	@Test
	public void testEditCompanyName() {
		Company company = addTestCompanyToDB();
		company.setName("EditedName");
		companyController.editCompany(company);
		assertEquals("EditedName", companyController.getCompanyId("1").getName());
	}

	@Test
	public void testEditCompanyNameWithTwoCompaniesInDb() {
		Company company = addTestCompanyToDB();
		testHelper.addCompany("2", "nameC2", "vatCode2", "address2", "city2", "zipCode2", "country2", "phone2", "email2");
		company.setName("EditedName");
		companyController.editCompany(company);
		assertEquals("EditedName", companyController.getCompanyId("1").getName());
	}
	
	@Test
	public void testCompanyCreatedInvoicesDefaultValue() {
		Company company = addTestCompanyToDB();
		assertEquals(1, company.getCreatedInvoices());
	}
	
	@Test
	public void testCompanyCanIncrementCreatedInvoicesValue() {
		Company company = addTestCompanyToDB();
		company.updateCreatedInvoicesNumber();
		assertEquals(2, company.getCreatedInvoices());
	}
	
	@Test
	public void testOnlySelectedCompanyIncrementsCreatedInvoicesValue() {
		Company company = addTestCompanyToDB();
		testHelper.addCompany("2", "nameC2", "vatCode2", "address2", "city2", "zipCode2", "country2", "phone2", "email2");
		company.updateCreatedInvoicesNumber();
		assertNotEquals(0, company.getCreatedInvoices());
	}

	//invoice
	private Invoice addTestInvoiceToDB() {
		testHelper.addInvoice("1", "testName", "testPrice", "testDescription");
		Invoice invoice = companyController.getInvoiceId("1");
		assertNotNull(invoice);
		return invoice;
	}

	@Test
	public void addTestInvoiceToDBWhenInvoiceAlreadyInDB() {
		companyController.addInvoice(new Invoice("1", "testName", "testPrice", "testDescription"));
		boolean added = companyController.addInvoice(new Invoice("1", "testName", "testPrice", "testDescription"));
		assertEquals(false, added);
	}

	@Test
	public void testGetAllInvoicesWhenThereAreNoInvoices() {
		List<Invoice> allInvoices = companyController.getAllInvoices();
		assertEquals(0, allInvoices.size());
	}

	@Test
	public void testGetAllInvoicesWhenThereIsOneInvoice() {
		testHelper.addInvoice("1", "nameI1", "100", "basic invoice type1");
		List<Invoice> allInvoices = companyController.getAllInvoices();
		assertEquals(1, allInvoices.size());	
	}

	@Test
	public void testGetInvoiceByIdWhenInvoiceIsNotThere() {
		testHelper.addInvoice("2", "nameI1", "100", "basic invoice type1");
		Invoice invoice = companyController.getInvoiceId("1");
		assertNull(invoice);
	}

	@Test
	public void testGetInvoiceByIdWithRightPrice() {
		Invoice invoice = addTestInvoiceToDB();
		assertEquals("testPrice", invoice.getPrice());
	}

	@Test
	public void testGetInvoiceByIdWithWrongPrice() {
		Invoice invoice = addTestInvoiceToDB();
		assertNotEquals("wrongTestPrice", invoice.getPrice());
	}

	@Test
	public void testGetInvoiceByIdWithRightDescription() {
		Invoice invoice = addTestInvoiceToDB();
		assertEquals("testDescription", invoice.getDescription());
	}

	@Test
	public void testGetInvoiceByIdWithWrongDescription() {
		Invoice invoice = addTestInvoiceToDB();
		assertNotEquals("wrongTestDescription", invoice.getDescription());
	}	

	@Test
	public void testEditInvoiceWhileNoInvoicesInDB() {
		Invoice invoice = new Invoice("1", "testName", "testPrice", "testDescription");
		boolean edited = companyController.editInvoice(invoice);
		assertFalse(edited);
	}

	@Test
	public void testEditInvoiceName() {
		Invoice invoice = addTestInvoiceToDB();
		invoice.setName("EditedName");
		companyController.editInvoice(invoice);
		assertEquals("EditedName", companyController.getInvoiceId("1").getName());
	}

	@Test
	public void testEditInvoiceNameWithTwoInvoicesInDb() {
		Invoice invoice = addTestInvoiceToDB();
		testHelper.addInvoice("2", "nameI2", "200", "basic invoice type2");
		invoice.setName("EditedName");
		companyController.editInvoice(invoice);
		assertEquals("EditedName", companyController.getInvoiceId("1").getName());
	}

	//company hashcode
	@Test
	public void testCompanyHashCodeForDifferentObjects() {
		Company company = new Company("1", "", "", "", "", "", "");
		company.setExtraParameters( "", "");
		assertNotEquals(company.hashCode(),new Company().hashCode());
	}

	@Test
	public void testCompanyHashCodeForTheSameObject() {
		Company company = addTestCompanyToDB();
		assertEquals(company.hashCode(), company.hashCode());
	}

	@Test
	public void testCompanyHashCodeForDifferentObjectWithDifferentFields() {
		testHelper.addTwoCompanies();
		Company company1 = companyController.getCompanyId("1");
		Company company2 = companyController.getCompanyId("2");
		assertNotEquals(company1.hashCode(), company2.hashCode());
	}

	@Test
	public void testHashCodeCompanyWithNullFields() {
		Company company = new Company("1", "name", null, null, null, null, null);
		company.setExtraParameters(null, null);
		company.hashCode();
	}

	//client hashcode
	@Test
	public void testClientHashCodeForDifferentObjects() {
		Client client=new Client("1", "", "", "", "", "", "");
		client.setExtraParameters("", "");
		assertNotEquals(client.hashCode(),new Client().hashCode());
	}

	@Test
	public void testClientHashCodeForTheSameObject() {
		Client client = addTestClientToDB();
		assertEquals(client.hashCode(), client.hashCode());
	}

	@Test
	public void testClientHashCodeForTheDifferentObjectWithDifferentFields() {
		testHelper.addTwoClients();
		Client client1 = companyController.getClientId("1");
		Client client2 = companyController.getClientId("2");
		assertNotEquals(client1.hashCode(), client2.hashCode());
	}

	@Test
	public void testHashCodeClientWithNullFields() {
		Client client = new Client("1", "name", null, null, null, null, null);
		client.setExtraParameters(null, null);
		client.hashCode();
	}

	//invoice hashcode
	@Test
	public void testInvoiceHashCodeForDifferentObjects() {
		assertNotEquals(new Invoice("1", "", "", "").hashCode(),new Invoice().hashCode());
	}

	@Test
	public void testInvoiceHashCodeForTheSameObject() {
		Invoice invoice = addTestInvoiceToDB();
		assertEquals(invoice.hashCode(), invoice.hashCode());
	}

	@Test
	public void testInvoiceHashCodeForTheDifferentObjectWithDifferentFields() {
		testHelper.addTwoInvoices();
		Invoice invoice1 = companyController.getInvoiceId("1");
		Invoice invoice2 = companyController.getInvoiceId("2");
		assertNotEquals(invoice1.hashCode(), invoice2.hashCode());
	}

	@Test
	public void testHashCodeInvoiceWithNullFields() {
		Invoice invoice = new Invoice("1", "name", null, null);
		invoice.hashCode();
	}

	// company equals
	@Test
	public void testCompanyNotEqualsForDifferentObjectWithDifferentNameAndId() {
		testHelper.addTwoCompanies();
		Company company1 = companyController.getCompanyId("1");
		Company company2 = companyController.getCompanyId("2");
		assertEquals(false, company1.equals(company2));
	}

	@Test
	public void testCompanyNotEqualsForDifferentFields() {
		Company company1 = new Company("1", "nameC1", "vatCode1", "address1", "city1", "zipCode1", "country1");
		company1.setExtraParameters( "phone1", "email1");
		Company company2 = new Company("1", "nameC1", "vatCode2", "address2", "city2", "zipCode2", "country2");
		company2.setExtraParameters( "phone2", "email2");
		assertEquals(false, company1.equals(company2));
	}

	@Test
	public void testCompanyEqualsForSameObject() {
		Company company = addTestCompanyToDB();
		assertEquals(true, company.equals(company));
	}

	@Test
	public void testCompanyEqualsForSameFields() {
		Company company1 = new Company("1", "nameC1", "vatCode1", "address1", "city1", "zipCode1", "country1");
		company1.setExtraParameters( "phone1", "email1");
		Company company2 = new Company("1", "nameC1", "vatCode1", "address1", "city1", "zipCode1", "country1");
		company2.setExtraParameters( "phone1", "email1");

		assertEquals(true, company1.equals(company2));
	}

	@Test
	public void testCompanyNull() {
		Company nullCompany = null;
		Company company = addTestCompanyToDB();

		assertEquals(false, company.equals(nullCompany));
	}

	@Test
	public void testCompaniesWithNullFields() {
		Company company1 = new Company("1", "name", null, null, null, null, null);
		company1.setExtraParameters( null,null);
		Company company2 = new Company("1", "name", null, null, null, null, null);
		company2.setExtraParameters( null,null);
		company1.equals(company2);
	}

	// client equals
	@Test
	public void testClientNotEqualsForDifferentObjectWithDifferentNameAndId() {
		testHelper.addTwoClients();
		Client client1 = companyController.getClientId("1");
		Client client2 = companyController.getClientId("2");
		assertEquals(false, client1.equals(client2));
	}

	@Test
	public void testClientNotEqualsForDifferentFields() {
		Client client1 = new Client("1", "a", "b", "c", "d", "f", "g");
		client1.setExtraParameters("ba", "be");
		Client client2 = new Client("1", "a", "me", "tre", "qua", "ue", "ug");	
		client1.setExtraParameters("la", "te");
		assertEquals(false, client1.equals(client2));
	}

	@Test
	public void testClientEqualsForSameObject() {
		Client client = addTestClientToDB();
		assertEquals(true, client.equals(client));
	}

	@Test
	public void testClientEqualsForSameFields() {
		Client client1 = new Client("1", "a", "b", "c", "d", "f", "g");
		client1.setExtraParameters("h", "p");
		Client client2 = new Client("1", "a", "b", "c", "d", "f", "g");
		client2.setExtraParameters("h", "p");
		assertEquals(true, client1.equals(client2));
	}

	@Test
	public void testClientNull() {
		Client nullClient = null;
		Client client = addTestClientToDB();

		assertEquals(false, client.equals(nullClient));
	}

	@Test
	public void testEqualsClientsWithNullFields() {
		Client client1 = new Client("1", "name", null, null, null, null, null);
		client1.setExtraParameters(null, null);
		Client client2 = new Client("1", "name", null, null, null, null, null);
		client1.setExtraParameters(null, null);
		client1.equals(client2);
	}

	// invoice equals
	@Test
	public void testInvoiceNotEqualsForDifferentNameAndId() {
		testHelper.addTwoInvoices();
		Invoice invoice1 = companyController.getInvoiceId("1");
		Invoice invoice2 = companyController.getInvoiceId("2");
		assertEquals(false, invoice1.equals(invoice2));
	}
	
	@Test
	public void testInvoiceNotEqualsForDifferentNameButEqualsId() {
		Invoice invoice1 = new Invoice("1", "a", "b", "c");
		Invoice invoice2 = new Invoice("1", "b", "b", "c");
		assertEquals(false, invoice1.equals(invoice2));
	}

	@Test
	public void testInvoiceNotEqualsForDifferentFields() {
		Invoice invoice1 = new Invoice("1", "a", "b", "c");
		Invoice invoice2 = new Invoice("1", "a", "d", "e");
		assertEquals(false, invoice1.equals(invoice2));
	}

	@Test
	public void testInvoiceEqualsForSameObject() {
		Invoice invoice = addTestInvoiceToDB();
		assertEquals(true, invoice.equals(invoice));
	}

	@Test
	public void testInvoiceEqualsForSameFields() {
		Invoice invoice1 = new Invoice("1", "a", "b", "c");
		Invoice invoice2 = new Invoice("1", "a", "b", "c");

		assertEquals(true, invoice1.equals(invoice2));
	}

	@Test
	public void testInvoiceNull() {
		Invoice nullInvoice = null;
		Invoice invoice = addTestInvoiceToDB();

		assertEquals(false, invoice.equals(nullInvoice));
	}

	@Test
	public void testEqualsInvoicesWithNullFields() {
		Invoice invoice1 = new Invoice("1", "name", null, null);
		Invoice invoice2 = new Invoice("1", "name", null, null);
		assertEquals(true, invoice1.equals(invoice2));
	}

	// generic equals
	@Test
	public void testNotEqualsForDifferentObjects() {
		Invoice invoice = addTestInvoiceToDB();
		Company company = addTestCompanyToDB();

		assertEquals(false, invoice.equals(company));
	}	
	
	@Test
	public void testNotEqualsIfDifferentName() {
		Invoice invoice1 = new Invoice("1", "name1", null, null);
		Invoice invoice2 = new Invoice("1", "name2", null, null);

		assertEquals(false, invoice1.equals(invoice2));
	}	
	
	@Test
	public void testNotEqualsIfDifferentId() {
		Invoice invoice1 = new Invoice("1", "name", null, null);
		Invoice invoice2 = new Invoice("2", "name", null, null);

		assertEquals(false, invoice1.equals(invoice2));
	}	
}
