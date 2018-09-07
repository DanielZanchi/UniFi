package com.unifi.fatture.ui;

import java.io.IOException;

import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JButtonFixture;
import org.assertj.swing.fixture.JPanelFixture;
import org.assertj.swing.fixture.JTextComponentFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ItemInvoicePanelUITest extends MainWindowUsingFongo{
	private FrameFixture window;
	private JPanelFixture addInvoice_Panel;
	private JButtonFixture cancelAdd_Button;
	private JButtonFixture saveAdd_Button;
	private JButtonFixture addInvoice_Button;
	private JButtonFixture editInvoice_Button;

	@Before
	public void setUp() throws IOException {
		super.init();
		window = new FrameFixture(frame.getMainFrame());
		window.show();
		addInvoice_Button = window.panel("InvoicePanel").button("addInvoiceButton");
		editInvoice_Button = window.panel("InvoicePanel").button("editInvoiceButton");
	}

	@After
	public void tearDown() {
		window.cleanUp();
	}

	@Test
	public void testAddButtonAction() {
		addInvoice_Button.click();
		addInvoice_Panel = window.panel("AddInvoicePanel");
		addInvoice_Panel.requireVisible();
	}

	@Test
	public void testCancelButtonText() {
		addInvoice_Button.click();
		addInvoice_Panel = window.panel("AddInvoicePanel");
		cancelAdd_Button = addInvoice_Panel.button("CancelButton");
		cancelAdd_Button.requireText("Cancel");
	}

	@Test
	public void testCancelButtonAction() {
		addInvoice_Button.click();
		addInvoice_Panel = window.panel("AddInvoicePanel");
		cancelAdd_Button = addInvoice_Panel.button("CancelButton");
		cancelAdd_Button.click();
		addInvoice_Panel.requireNotVisible();
	}

	@Test
	public void testSaveButtonText() {
		getSaveButton();
		saveAdd_Button.requireText("Save");
	}

	@Test
	public void testSaveButtonNoInputsAction() {
		getSaveButton();
		saveAdd_Button.requireDisabled();
	}

	@Test
	public void testSaveButtonWithInputsAction() {
		getSaveButton();
		setTextfieldsStrings("0", "1", "2");
		saveAdd_Button.click();
		addInvoice_Panel.requireNotVisible();
	}

	@Test
	public void testSaveButtonWithWrongInputsAction() {
		getSaveButton();
		setTextfieldsStrings("0", "1", "");
		saveAdd_Button.requireDisabled();
	}

	// edit button
	@Test 
	public void testEditButtonActionAddPanelVisible() {
		addInvoice_Button.click();
		addInvoice_Panel = window.panel("AddInvoicePanel");
		setTextfieldsStrings("0", "1", "2");
		saveAdd_Button = addInvoice_Panel.button("SaveButton");
		saveAdd_Button.click();
		editInvoice();
		addInvoice_Panel.requireVisible();
	}

	@Test 
	public void testEditButtonWithNoCompanySelected() {
		editInvoice();
		editInvoice_Button.requireDisabled();
	}

	@Test 
	public void testEditButtonNameTextField() {
		initTextFieldsForEditButtonAssertions();
		JTextComponentFixture invoiceName = addInvoice_Panel.textBox("invoiceName_TF");
		invoiceName.text().compareTo("0");
	}

	@Test 
	public void testEditButtonPriceTextField() {
		initTextFieldsForEditButtonAssertions();
		JTextComponentFixture invoicePrice = addInvoice_Panel.textBox("invoicePrice_TF");
		invoicePrice.text().compareTo("1");
	}

	@Test 
	public void testEditButtonDescriptionTextField() {
		initTextFieldsForEditButtonAssertions();
		JTextComponentFixture invoiceDescription = addInvoice_Panel.textBox("invoiceDescription_TF");
		invoiceDescription.text().compareTo("2");
	}

	@Test 
	public void testEditButtonNameModified() {
		checkTextfieldTextAfterInvoiceSavedSaved("invoiceName_TF", "edited");
	}

	@Test 
	public void testEditButtonPriceModified() {
		checkTextfieldTextAfterInvoiceSavedSaved("invoicePrice_TF", "edited");
	}

	@Test 
	public void testEditButtonDescriptionModified() {
		checkTextfieldTextAfterInvoiceSavedSaved("invoiceDescription_TF", "edited");
	}

	// Help methods
	private void checkTextfieldTextAfterInvoiceSavedSaved(String textFieldName, String newText) {
		initTextFieldsForEditButtonAssertions();
		addInvoice_Panel.textBox(textFieldName).setText(newText);
		saveAdd_Button.click();
		editInvoice();
		JTextComponentFixture invoiceTextField = addInvoice_Panel.textBox(textFieldName);
		invoiceTextField.text().compareTo(newText);
	}

	private void initTextFieldsForEditButtonAssertions() {
		addInvoice_Button.click();
		addInvoice_Panel = window.panel("AddInvoicePanel");
		setTextfieldsStrings("0", "1", "2");
		saveAdd_Button = addInvoice_Panel.button("SaveButton");
		saveAdd_Button.click();
		editInvoice();
	}

	private void editInvoice() {
		editInvoice_Button.click();
	}

	private void getSaveButton() {
		addInvoice_Button.click();
		addInvoice_Panel = window.panel("AddInvoicePanel");
		saveAdd_Button = addInvoice_Panel.button("SaveButton");
	}

	private void setTextfieldsStrings(String string1, String string2, String string3) {
		addInvoice_Panel.textBox("invoiceName_TF").setText(string1);
		addInvoice_Panel.textBox("invoicePrice_TF").setText(string2);
		addInvoice_Panel.textBox("invoiceDescription_TF").setText(string3);	
	}
}