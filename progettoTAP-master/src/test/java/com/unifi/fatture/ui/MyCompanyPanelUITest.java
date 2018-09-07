package com.unifi.fatture.ui;

import java.io.IOException;

import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JButtonFixture;
import org.assertj.swing.fixture.JPanelFixture;
import org.assertj.swing.fixture.JTextComponentFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MyCompanyPanelUITest extends MainWindowUsingFongo{
	private FrameFixture window;
	private JPanelFixture myCompany_Panel;
	private JPanelFixture addCompany_Panel;
	private JButtonFixture addCompany_Button;
	private JButtonFixture cancelAdd_Button;
	private JButtonFixture saveAdd_Button;
	private JButtonFixture editCompany_Button; 

	@Before
	public void setUp() throws IOException {
		super.init();
		window = new FrameFixture(frame.getMainFrame());
		window.show();
		myCompany_Panel = window.panel("CompanyPanel");
		addCompany_Button = window.panel("CompanyPanel").button("AddCompanyButton");
		editCompany_Button = window.panel("CompanyPanel").button("EditCompanyButton");
	}

	@After
	public void tearDown() {
		window.cleanUp();
	}

	@Test
	public void testCompanyPanelBackground() {
		myCompany_Panel.background().requireEqualTo(new java.awt.Color(226, 244, 252));
	}

	@Test
	public void testAddButtonText() {
		addCompany_Button.requireText("Add");
	}

	@Test
	public void testAddButtonAction() {
		showAddCompanyPanel();
		addCompany_Panel.requireVisible();
	}

	@Test
	public void testCancelButtonText() {
		showAddCompanyPanel();
		cancelAdd_Button = addCompany_Panel.button("CancelButton");
		cancelAdd_Button.requireText("Cancel");
	}

	@Test
	public void testCancelButtonAction() {
		showAddCompanyPanel();
		cancelAdd_Button = addCompany_Panel.button("CancelButton");
		cancelAdd_Button.click();
		addCompany_Panel.requireNotVisible();
	}

	@Test
	public void testSaveButtonText() {
		showAddCompanyPanel();
		saveAdd_Button = addCompany_Panel.button("SaveButton");
		saveAdd_Button.requireText("Save");
	}

	@Test
	public void testSaveButtonAction() {
		saveCompany();
		saveAdd_Button.requireDisabled();
	}

	@Test
	public void testSaveResetNameTextField() {
		saveCompany();

		showAddCompanyPanel();
		JTextComponentFixture companyName = addCompany_Panel.textBox("companyNameTextField");
		companyName.text().compareTo("");
	}

	@Test
	public void testCancelResetNameTextField() {
		cancelCompany();

		showAddCompanyPanel();
		JTextComponentFixture companyName = addCompany_Panel.textBox("companyNameTextField");
		companyName.text().compareTo("");
	}

	@Test
	public void testSaveResetVatTextField() {
		saveCompany();

		showAddCompanyPanel();
		JTextComponentFixture companyVat = addCompany_Panel.textBox("companyVatTextField");
		companyVat.text().isEmpty();
	}

	@Test
	public void testCancelResetVatTextField() {
		cancelCompany();

		showAddCompanyPanel();
		JTextComponentFixture companyVat = addCompany_Panel.textBox("companyVatTextField");
		companyVat.text().isEmpty();
	}

	@Test
	public void testSaveResetAddressTextField() {
		saveCompany();

		showAddCompanyPanel();
		JTextComponentFixture companyAddress = addCompany_Panel.textBox("companyAddressTextField");
		companyAddress.text().isEmpty();
	}

	@Test
	public void testCancelResetAddressTextField() {
		cancelCompany();

		showAddCompanyPanel();
		JTextComponentFixture companyAddress = addCompany_Panel.textBox("companyAddressTextField");
		companyAddress.text().isEmpty();
	}

	@Test
	public void testSaveResetCityTextField() {
		saveCompany();

		showAddCompanyPanel();
		JTextComponentFixture companyCity = addCompany_Panel.textBox("companyCityTextField");
		companyCity.text().isEmpty();
	}

	@Test
	public void testCancelResetCityTextField() {
		cancelCompany();

		showAddCompanyPanel();
		JTextComponentFixture companyCity = addCompany_Panel.textBox("companyCityTextField");
		companyCity.text().isEmpty();
	}


	@Test
	public void testSaveResetZipTextField() {
		saveCompany();

		showAddCompanyPanel();
		JTextComponentFixture companyZip = addCompany_Panel.textBox("companyZipTextField");
		companyZip.text().isEmpty();
	}

	@Test
	public void testCancelResetZipTextField() {
		cancelCompany();

		showAddCompanyPanel();
		JTextComponentFixture companyZip = addCompany_Panel.textBox("companyZipTextField");
		companyZip.text().isEmpty();
	}

	@Test
	public void testSaveResetCountryTextField() {
		saveCompany();

		showAddCompanyPanel();
		JTextComponentFixture companyCountry = addCompany_Panel.textBox("companyCountryTextField");
		companyCountry.text().isEmpty();
	}

	@Test
	public void testCancelResetCountryTextField() {
		cancelCompany();

		showAddCompanyPanel();
		JTextComponentFixture companyCountry = addCompany_Panel.textBox("companyCountryTextField");
		companyCountry.text().isEmpty();
	}

	@Test
	public void testSaveResetPhoneTextField() {
		saveCompany();

		showAddCompanyPanel();
		JTextComponentFixture companyPhone = addCompany_Panel.textBox("companyPhoneTextField");
		companyPhone.text().compareTo("");
	}

	@Test
	public void testCancelResetPhoneTextField() {
		cancelCompany();

		showAddCompanyPanel();
		JTextComponentFixture companyPhone = addCompany_Panel.textBox("companyPhoneTextField");
		companyPhone.text().compareTo("");
	}

	@Test
	public void testSaveResetEmailTextField() {
		saveCompany();

		showAddCompanyPanel();
		JTextComponentFixture companyEmail = addCompany_Panel.textBox("companyEmailTextField");
		companyEmail.text().compareTo("");
	}

	@Test
	public void testCancelResetEmailTextField() {
		cancelCompany();

		showAddCompanyPanel();
		JTextComponentFixture companyEmail = addCompany_Panel.textBox("companyEmailTextField");
		companyEmail.text().compareTo("");
	}

	@Test
	public void testSaveButtonWithInputsAction() {
		showAddCompanyPanel();
		setTextfieldsStrings("0", "1", "2", "4", "5", "6", "", "");
		saveCompany();
		addCompany_Panel.requireNotVisible();
	}

	@Test
	public void testSaveButtonWithWrongInputsAction() {
		showAddCompanyPanel();
		setTextfieldsStrings("0", "1", "2", "4", "", "", "", "");
		saveAdd_Button = addCompany_Panel.button("SaveButton");
		saveAdd_Button.requireDisabled();
	}

	//edit button
	@Test 
	public void testEditButtonActionAddPanelVisible() {
		showAddCompanyPanel();
		setTextfieldsStrings("0", "1", "2", "4", "", "", "", "");
		saveAdd_Button = addCompany_Panel.button("SaveButton");
		saveAdd_Button.click();
		editCompany();
		addCompany_Panel.requireVisible();
	}

	@Test 
	public void testEditButtonWithNoCompanySelected() {
		editCompany();
		editCompany_Button.requireDisabled();
	}

	@Test 
	public void testEditButtonNameTextField() {
		initTextFieldsForEditButtonAssertions();
		JTextComponentFixture companyName = addCompany_Panel.textBox("companyNameTextField");
		companyName.text().compareTo("0");
	}

	@Test 
	public void testEditButtonVatCodeTextField() {
		initTextFieldsForEditButtonAssertions();
		JTextComponentFixture companyVat = addCompany_Panel.textBox("companyVatTextField");
		companyVat.text().compareTo("1");
	}

	@Test 
	public void testEditButtonAddressTextField() {
		initTextFieldsForEditButtonAssertions();
		JTextComponentFixture companyAddress = addCompany_Panel.textBox("companyAddressTextField");
		companyAddress.text().compareTo("2");
	}

	@Test 
	public void testEditButtonCityTextField() {
		initTextFieldsForEditButtonAssertions();
		JTextComponentFixture companyCity = addCompany_Panel.textBox("companyCityTextField");
		companyCity.text().compareTo("3");
	}


	@Test 
	public void testEditButtonZipTextField() {
		initTextFieldsForEditButtonAssertions();
		JTextComponentFixture companyZip = addCompany_Panel.textBox("companyZipTextField");
		companyZip.text().compareTo("5");
	}

	@Test 
	public void testEditButtonCountryTextField() {
		initTextFieldsForEditButtonAssertions();
		JTextComponentFixture companyCountry = addCompany_Panel.textBox("companyCountryTextField");
		companyCountry.text().compareTo("6");
	}

	@Test 
	public void testEditButtonPhoneTextField() {
		initTextFieldsForEditButtonAssertions();
		JTextComponentFixture companyPhone = addCompany_Panel.textBox("companyPhoneTextField");
		companyPhone.text().compareTo("7");
	}

	@Test 
	public void testEditButtonEmailTextField() {
		initTextFieldsForEditButtonAssertions();
		JTextComponentFixture companyEmail = addCompany_Panel.textBox("companyEmailTextField");
		companyEmail.text().compareTo("");
	}

	@Test 
	public void testEditButtonNameModified() {
		checkTextfieldTextAfterCompanySaved("companyNameTextField", "edited");
	}

	@Test 
	public void testEditButtonVatModified() {
		checkTextfieldTextAfterCompanySaved("companyVatTextField", "edited");
	}

	@Test 
	public void testEditButtonAddressModified() {
		checkTextfieldTextAfterCompanySaved("companyAddressTextField", "edited");
	}

	@Test 
	public void testEditButtonCityModified() {
		checkTextfieldTextAfterCompanySaved("companyCityTextField", "edited");
	}


	@Test 
	public void testEditButtonZipModified() {
		checkTextfieldTextAfterCompanySaved("companyZipTextField", "edited");
	}

	@Test 
	public void testEditButtonCountryModified() {
		checkTextfieldTextAfterCompanySaved("companyCountryTextField", "edited");
	}

	@Test 
	public void testEditButtonPhoneModified() {
		checkTextfieldTextAfterCompanySaved("companyPhoneTextField", "edited");
	}

	@Test 
	public void testEditButtonEmailModified() {
		checkTextfieldTextAfterCompanySaved("companyEmailTextField", "edited");
	}

	// Help methods
	private void checkTextfieldTextAfterCompanySaved(String textFieldName, String newText) {
		initTextFieldsForEditButtonAssertions();
		addCompany_Panel.textBox(textFieldName).setText(newText);
		saveAdd_Button.click();
		editCompany();
		JTextComponentFixture companyTextField = addCompany_Panel.textBox(textFieldName);
		companyTextField.text().compareTo(newText);
	}

	private void showAddCompanyPanel() {
		addCompany_Button.click();
		addCompany_Panel = window.panel("AddCompanyPanel");
	}

	private void saveCompany() {
		showAddCompanyPanel();
		saveAdd_Button = addCompany_Panel.button("SaveButton");
		saveAdd_Button.click();
	}

	private void editCompany() {
		editCompany_Button.click();
	}

	private void cancelCompany(){
		showAddCompanyPanel();
		cancelAdd_Button = addCompany_Panel.button("CancelButton");
		cancelAdd_Button.click();
	}

	private void setTextfieldsStrings(String string1, String string2, String string3, String string4,
			String string6, String string7, String string8, String string9) {
		addCompany_Panel.textBox("companyNameTextField").setText(string1);
		addCompany_Panel.textBox("companyVatTextField").setText(string2);
		addCompany_Panel.textBox("companyAddressTextField").setText(string3);
		addCompany_Panel.textBox("companyCityTextField").setText(string4);
		addCompany_Panel.textBox("companyZipTextField").setText(string6);
		addCompany_Panel.textBox("companyCountryTextField").setText(string7);
		addCompany_Panel.textBox("companyPhoneTextField").setText(string8);
		addCompany_Panel.textBox("companyEmailTextField").setText(string9);
	}

	private void initTextFieldsForEditButtonAssertions() {
		showAddCompanyPanel();
		setTextfieldsStrings("0", "1", "2", "4", "5", "6", "7", "");
		saveAdd_Button = addCompany_Panel.button("SaveButton");
		saveAdd_Button.click();
		editCompany();
	}
}