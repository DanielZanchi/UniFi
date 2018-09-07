package com.unifi.fatture.app;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class DatabaseUiComunication {
	private static final Logger LOGGER = LogManager.getLogger(DatabaseUiComunication.class);
	private AppController myAppController;
	private Database database;
	private Company currentSelectedCompany;
	private Client currentSelectedClient;
	private Invoice currentSelectedInvoice;

	private JComboBox<String> clientsList;
	private JComboBox<String> invoicesList;
	private JLabel companyInfo;

	private int companyCounter = 0;

	private JButton editCompanyButton;
	private JButton editClientButton;
	private JButton editInvoiceButton;

	public DatabaseUiComunication(Database database) {
		this.database = database;
		myAppController = new AppController(database);
		editCompanyButton = new JButton();
		editClientButton = new JButton();
		editInvoiceButton = new JButton();
		companyInfo = new JLabel();
	}

	public boolean addClientToDatabase(String name, String fiscalCode, AddClientToDatabaseParameter addressInformation, String phone,
			String email) {
		String currentId = String.valueOf(this.getClientsCount() + 1);
		Client client = new Client(currentId, name, fiscalCode, addressInformation.getResidenceForDB(), addressInformation.getCityForDB(), addressInformation.getZipForDB(), addressInformation.getCountryForDB());
		client.setExtraParameters(phone, email);
		return myAppController.addClient(client);
	}

	public boolean editClientFromDatabase(String name, String fiscalCode, AddClientToDatabaseParameter addressInformation, String phone, String email) {
		String currentId = String.valueOf(currentSelectedClient.getId());
		Client client = new Client(currentId, name, fiscalCode, addressInformation.getResidenceForDB(), addressInformation.getCityForDB(), addressInformation.getZipForDB(), addressInformation.getCountryForDB());
		client.setExtraParameters(phone, email);
		return myAppController.editClient(client);
	}
	
	public boolean addCompanyToDatabase(String name, String vat, AddCompanyToDatabaseParameter addressInformation, String phone,
			String email) {
		String currentId = String.valueOf(this.getCompaniesCount() +1);
		Company company = new Company(currentId, name, vat, addressInformation.getAddressForDB(), addressInformation.getCityForDB(), addressInformation.getZipForDB(), addressInformation.getCountryForDB());
		company.setExtraParameters(phone, email);
		return myAppController.addCompany(company);
	}

	public boolean editCompanyFromDatabase(String name, String vat, AddCompanyToDatabaseParameter addressInformation, String phone, String email) {
		String currentId = String.valueOf(currentSelectedCompany.getId());
		Company company = new Company(currentId, name, vat, addressInformation.getAddressForDB(), addressInformation.getCityForDB(), addressInformation.getZipForDB(), addressInformation.getCountryForDB());
		company.setExtraParameters(phone, email);
		boolean saved =  myAppController.editCompany(company);
		setCompanyCounter(getCompaniesCount()-1);
		return saved;
	}

	public boolean addInvoiceToDatabase(String name, String description, String price) {
		String currentId = String.valueOf(this.getInvoicesCount() + 1);
		return myAppController.addInvoice(new Invoice(currentId, name, price, description));
	}

	public boolean editInvoiceFromDatabase(String name, String price, String description) {
		String currentId = String.valueOf(currentSelectedInvoice.getId());
		return myAppController.editInvoice(new Invoice(currentId, name, price, description));
	}

	public int getCompaniesCount() {
		List<Company> companies = myAppController.getAllCompany();
		return companies.size();
	}

	public int getClientsCount() {
		List<Client> clients = myAppController.getAllClients();
		return clients.size();
	}

	public int getInvoicesCount() {
		List<Invoice> invoices = myAppController.getAllInvoices();
		return invoices.size();
	}

	public List<Company> getSavedCompanies() {
		return myAppController.getAllCompany();
	}

	public List<Client> getSavedClients() {
		return myAppController.getAllClients();
	}

	public List<Invoice> getSavedInvoices() {
		return myAppController.getAllInvoices();
	}

	public boolean printSelected(){
		try {
			new PDFCreator(currentSelectedCompany, currentSelectedClient, currentSelectedInvoice);
			currentSelectedCompany.updateCreatedInvoicesNumber();
			return true;
		}  catch (Exception e) {
			LOGGER.error(e);
			return false;
		}
	}

	public Company getCurrentSelectedCompany() {
		return currentSelectedCompany;
	}

	public void setCurrentSelectedCompany(Company currentSelectedCompany) {
		this.currentSelectedCompany = currentSelectedCompany;
		this.enableEditCompanyButton();
	}

	public Invoice getCurrentSelectedInvoice() {
		return currentSelectedInvoice;
	}

	public void setCurrentSelectedInvoice(Invoice currentSelectedInvoice) {
		this.currentSelectedInvoice = currentSelectedInvoice;
		this.enableEditInvoiceButton();
	}

	public Client getCurrentSelectedClient() {
		return currentSelectedClient;
	}

	public void setCurrentSelectedClient(Client currentSelectedClient) {
		this.currentSelectedClient = currentSelectedClient;
		this.enableEditClientButton();
	}

	public void updateAllReferences() {
		updateCompanyReference();
		updateClientsReferences();
		updateInvoicesReferences();
	}

	public void updateCompanyReference() {
		if (!this.getSavedCompanies().isEmpty()) {
				companyInfo.setText(this.getSavedCompanies().get(companyCounter).getName());
				this.setCurrentSelectedCompany(this.getSavedCompanies().get(companyCounter));
			}
	}

	public void updateClientsReferences() {
		clientsList.removeAllItems();
		for (int i = 0; i<this.getSavedClients().size(); i++) {
			clientsList.addItem(this.getSavedClients().get(i).getName());
		}
		clientsList.setSelectedIndex(this.getSavedClients().size()-1);
	}

	public void updateInvoicesReferences() {
		invoicesList.removeAllItems();
		for (int i = 0; i<this.getSavedInvoices().size(); i++) {
			invoicesList.addItem(this.getSavedInvoices().get(i).getName());
		}
		invoicesList.setSelectedIndex(this.getSavedInvoices().size()-1);
	}

	public void setClientsList(JComboBox<String> clientsList) {
		this.clientsList = clientsList;
	}

	public void setInvoicesList(JComboBox<String> invoicesList) {
		this.invoicesList = invoicesList;
	}

	public void setCompanyInfo(JLabel companyInfo) {
		this.companyInfo = companyInfo;
	}

	public int getCompanyCounter() {
		return companyCounter;
	}

	public void setCompanyCounter(int companyCounter) {
		this.companyCounter = companyCounter;
		this.updateCompanyReference();
	}

	public void enableEditCompanyButton() {
		editCompanyButton.setEnabled(true);
	}

	public void setEditCompanyButton(JButton editMyCompanyButton) {
		editCompanyButton = editMyCompanyButton;
	}

	public void enableEditClientButton() {
		editClientButton.setEnabled(true);
	}

	public void seteditClientButton(JButton editClientButton) {
		this.editClientButton = editClientButton;
	}

	public void enableEditInvoiceButton() {
		editInvoiceButton.setEnabled(true);
	}

	public void seteditInvoiceButton(JButton editInvoiceButton) {
		this.editInvoiceButton = editInvoiceButton;
	}

	public Class<? extends Database> getCurrentDatabaseClass() {
		return database.getClass();
	}
}