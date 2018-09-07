package com.unifi.fatture.ui;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JButtonFixture;
import org.assertj.swing.fixture.JComboBoxFixture;
import org.assertj.swing.fixture.JLabelFixture;
import org.assertj.swing.fixture.JPanelFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MainWindowUITest  extends MainWindowUsingFongo {
	private FrameFixture window;
	private JButtonFixture addCompanyButton;
	private JPanelFixture addCompanyPanel;

	@Before
	public void setUp() throws IOException {
		super.init();
		window = new FrameFixture(frame.getMainFrame());
		window.show();
		addCompanyButton = window.panel("CompanyPanel").button("AddCompanyButton");
	}

	@After
	public void tearDown() {
		window.cleanUp();
	}

	@Test
	public void testApplicationName() {
		window.requireTitle("Fatture App");
	}

	@Test
	public void testWindowIsEnable() {
		window.requireVisible();
	}

	@Test
	public void testWindowIsVisible() {
		window.isEnabled();
	}

	@Test
	public void testWindowBckgColor() {
		window.background().requireEqualTo(java.awt.Color.LIGHT_GRAY);
	}


	//Company panel
	@Test
	public void testAddButtonText() {
		addCompanyButton.requireText("Add");
	}

	@Test 
	public void testAddButtonAction() {
		showAddCompanyPanel();
		addCompanyPanel.requireVisible();
	}

	@Test
	public void testEditButtonWithNoCompanySelected() {
		JButtonFixture editButton = window.button("EditCompanyButton");
		JLabelFixture currentCompanySelected = window.label("currentSelectedCompanyLabel");
		assertEquals("My Company", currentCompanySelected.text());
		editButton.requireDisabled();
	}

	@Test
	public void testCurrentCompanyLabelWithNoCompanies() {
		JLabelFixture companyInfoLabel = window.label("currentSelectedCompanyLabel");
		companyInfoLabel.requireText("My Company");
	}

	@Test 
	public void testPreviewCompanyButtonWithNoCompanies() {
		JButtonFixture previewButton = window.button("prevCompany_Button");
		previewButton.requireEnabled();
	}

	@Test 
	public void testPreviewCompanyButtonWithOneCompany() {
		JButtonFixture previewButton = window.button("prevCompany_Button");
		JLabelFixture companyInfoLabel = window.label("currentSelectedCompanyLabel");
		showAddCompanyPanel();
		setTextfieldsStrings("comp1", "vat1", "address1", "city1", "zip1", "country1", "phone1", "email1");
		JButtonFixture saveAdd_Button = addCompanyPanel.button("SaveButton");
		saveAdd_Button.click();
		previewButton.click();
		companyInfoLabel.requireText("comp1");
	}

	@Test 
	public void testPreviewCompanyButtonAfterNextButtonWithTwoCompanies() {
		JButtonFixture previewButton = window.button("prevCompany_Button");
		JButtonFixture nextButton = window.button("nextCompany_Button");
		JLabelFixture companyInfoLabel = window.label("currentSelectedCompanyLabel");
		showAddCompanyPanel();
		JButtonFixture saveAdd_Button = addCompanyPanel.button("SaveButton");
		setTextfieldsStrings("comp1", "vat1", "address1", "city1", "zip1", "country1", "phone1", "email1");
		saveAdd_Button.click();
		addCompanyButton.click();
		setTextfieldsStrings("comp2", "vat2", "address2", "city2", "zip2", "country2", "phone2", "email2");
		saveAdd_Button.click();
		nextButton.click();
		previewButton.click();
		companyInfoLabel.requireText("comp1");
	}

	@Test 
	public void testNextCompanyButtonWithOneCompany() {
		JButtonFixture nextButton = window.button("nextCompany_Button");
		JLabelFixture companyInfoLabel = window.label("currentSelectedCompanyLabel");
		showAddCompanyPanel();
		setTextfieldsStrings("comp1", "vat1", "address1", "city1", "zip1", "country1", "phone1", "email1");
		JButtonFixture saveAdd_Button = addCompanyPanel.button("SaveButton");
		saveAdd_Button.click();
		nextButton.click();
		companyInfoLabel.requireText("comp1");
	}

	@Test
	public void testNextCompanyButtonWithNoCompanies() {
		JButtonFixture nextButton = window.button("nextCompany_Button");
		nextButton.requireEnabled();
	}

	@Test 
	public void testNextCompanyButtonWithTwoCompanies() {
		JButtonFixture nextButton = window.button("nextCompany_Button");
		JLabelFixture companyInfoLabel = window.label("currentSelectedCompanyLabel");
		showAddCompanyPanel();
		JButtonFixture saveAdd_Button = addCompanyPanel.button("SaveButton");
		setTextfieldsStrings("comp1", "vat1", "address1", "city1", "zip1", "country1", "phone1", "email1");
		saveAdd_Button.click();
		addCompanyButton.click();
		setTextfieldsStrings("comp2", "vat2", "address2", "city2", "zip2", "country2", "phone2", "email2");
		saveAdd_Button.click();
		nextButton.click();
		companyInfoLabel.requireText("comp2");
	}

	private void setTextfieldsStrings(String string1, String string2, String string3, String string4,
			String string6, String string7, String string8, String string9) {
		addCompanyPanel.textBox("companyNameTextField").setText(string1);
		addCompanyPanel.textBox("companyVatTextField").setText(string2);
		addCompanyPanel.textBox("companyAddressTextField").setText(string3);
		addCompanyPanel.textBox("companyCityTextField").setText(string4);
		addCompanyPanel.textBox("companyZipTextField").setText(string6);
		addCompanyPanel.textBox("companyCountryTextField").setText(string7);
		addCompanyPanel.textBox("companyPhoneTextField").setText(string8);
		addCompanyPanel.textBox("companyEmailTextField").setText(string9);
	}

	//Client panel
	@Test 
	public void testEditButtonWithNoClientSelected() {
		JComboBoxFixture clientComboBox = window.comboBox("clientsComboBox");
		assertEquals(null, clientComboBox.selectedItem());
		JButtonFixture editClientButton = window.button("editClientButton");
		editClientButton.requireDisabled();
	}

	@Test
	public void testClientsComboBoxWithNoClients(){
		JComboBoxFixture clientComboBox = window.comboBox("clientsComboBox");
		clientComboBox.requireItemCount(0);
	}

	//Invoice panel
	@Test 
	public void testEditButtonWithNoInvoiceSelected() {
		JComboBoxFixture invoiceComboBox = window.comboBox("invoicesComboBox");
		assertEquals(null, invoiceComboBox.selectedItem());
		JButtonFixture editInvoiceButton = window.button("editInvoiceButton");
		editInvoiceButton.requireDisabled();
	}

	@Test
	public void testInvoicesComboBoxWithNoInvoices() {
		JComboBoxFixture invoiceComboBox = window.comboBox("invoicesComboBox");
		invoiceComboBox.requireItemCount(0);
	}

	private void showAddCompanyPanel() {
		addCompanyButton.click();
		addCompanyPanel = window.panel("AddCompanyPanel");
	}
}