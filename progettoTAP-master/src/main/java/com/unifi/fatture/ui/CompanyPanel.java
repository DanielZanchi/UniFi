package com.unifi.fatture.ui;

import java.awt.Font;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;

import com.unifi.fatture.app.AddCompanyToDatabaseParameter;
import com.unifi.fatture.app.DatabaseUiComunication;

public class CompanyPanel extends PanelWithObligatoryTextFields {
	private static final long serialVersionUID = 6387743798709513734L;

	private transient DatabaseUiComunication myDatabaseUiComunication;

	private JTextField companyNameTF;
	private JTextField companyVatTF;
	private JTextField companyAddressTF;
	private JTextField companyCityTF;
	private JTextField companyZipTF;
	private JTextField companyCountryTF;
	private JTextField companyPhoneTF;
	private JTextField companyEmailTF;
	private CompanyPanel addCompanyPanel;

	private boolean isSaving;

	public CompanyPanel(JLayeredPane outerPanel, int buttonWidth, int buttonHeight, DatabaseUiComunication dbUiCom) {
		super("AddCompanyPanel", outerPanel, buttonWidth, buttonHeight, 0);
		addCompanyPanel = this;
		myDatabaseUiComunication = dbUiCom;

		initLabelsTextFields();

		FormattedButton saveButton = new FormattedButton("Save", "SaveButton");
		saveButton.setEnabled(false);
		saveButton.setBounds((addCompanyPanel.getWidth() / 2) + 24,
				addCompanyPanel.getHeight() - 20 - addCompanyPanel.getY(), buttonWidth, buttonHeight);
		addCompanyPanel.add(saveButton);
		saveButton.addActionListener(e -> {
			if (addCompanyPanel.isSaving()) {
				myDatabaseUiComunication.addCompanyToDatabase(companyNameTF.getText(), companyVatTF.getText(),
						new AddCompanyToDatabaseParameter(companyAddressTF.getText(), companyCityTF.getText(),
								companyZipTF.getText(), companyCountryTF.getText()),
						companyPhoneTF.getText(), companyEmailTF.getText());

				myDatabaseUiComunication.setCurrentSelectedCompany(myDatabaseUiComunication.getSavedCompanies()
						.get(myDatabaseUiComunication.getSavedCompanies().size() - 1));
				myDatabaseUiComunication.enableEditCompanyButton();
			} else {
				myDatabaseUiComunication.editCompanyFromDatabase(companyNameTF.getText(), companyVatTF.getText(),
						new AddCompanyToDatabaseParameter(companyAddressTF.getText(), companyCityTF.getText(),
								companyZipTF.getText(), companyCountryTF.getText()),
						companyPhoneTF.getText(), companyEmailTF.getText());
			}
			addCompanyPanel.setVisible(false);
			resetTextFields();
			myDatabaseUiComunication.updateCompanyReference();
		});

		String[] freeTextFields = { "companyPhoneTextField", "companyEmailTextField" };
		super.setUpTextFields(addCompanyPanel.getComponents(), freeTextFields, saveButton);
	}

	private void initLabelsTextFields() {
		int addPanelY = addCompanyPanel.getY();
		int insets = 8;
		int insetsMiddle = 80;

		JLabel addCompanyTitleLabel = new JLabel("Company");
		addCompanyTitleLabel.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		int width = (int) addCompanyTitleLabel.getPreferredSize().getWidth();
		int height = (int) addCompanyTitleLabel.getPreferredSize().getHeight();
		addCompanyTitleLabel.setBounds((addCompanyPanel.getWidth() / 2) - (width / 2), addPanelY - 10, width, height);
		addCompanyPanel.add(addCompanyTitleLabel);

		JLabel companyNameLabel = new JLabel("Company Name:");
		setUpLabelInThePanel(companyNameLabel, 0, addCompanyTitleLabel);

		companyNameTF = new JTextField();
		setUpTextFieldInThePanel(companyNameTF, 300, "companyNameTextField", 0, insets, companyNameLabel);

		JLabel companyVatLabel = new JLabel("VAT Number:");
		setUpLabelInThePanel(companyVatLabel, 0, companyNameTF);

		companyVatTF = new JTextField();
		setUpTextFieldInThePanel(companyVatTF, 200, "companyVatTextField", 0, insets, companyVatLabel);

		JLabel companyAddressLabel = new JLabel("Address:");
		setUpLabelInThePanel(companyAddressLabel, 0, companyVatTF);

		companyAddressTF = new JTextField();
		setUpTextFieldInThePanel(companyAddressTF, 300, "companyAddressTextField", 0, insets, companyAddressLabel);

		JLabel companyCityLabel = new JLabel("City:");
		setUpLabelInThePanel(companyCityLabel, 0, companyAddressTF);

		companyCityTF = new JTextField();
		setUpTextFieldInThePanel(companyCityTF, 300, "companyCityTextField", 0, insets, companyCityLabel);

		JLabel companyZipLabel = new JLabel("ZIP Code:");
		setUpLabelInThePanel(companyZipLabel, -insetsMiddle, companyCityTF);

		companyZipTF = new JTextField();
		setUpTextFieldInThePanel(companyZipTF, 80, "companyZipTextField", -insetsMiddle, insets, companyZipLabel);

		JLabel companyCountryLabel = new JLabel("Country:");
		setUpLabelInThePanel(companyCountryLabel, insetsMiddle, companyCityTF);

		companyCountryTF = new JTextField();
		setUpTextFieldInThePanel(companyCountryTF, 100, "companyCountryTextField", insetsMiddle, insets,
				companyCountryLabel);

		JLabel companyPhoneLabel = new JLabel("Phone:");
		setUpLabelInThePanel(companyPhoneLabel, 0, companyCountryTF);

		companyPhoneTF = new JTextField();
		setUpTextFieldInThePanel(companyPhoneTF, 150, "companyPhoneTextField", 0, insets, companyPhoneLabel);

		JLabel companyEmailLabel = new JLabel("Email:");
		setUpLabelInThePanel(companyEmailLabel, 0, companyPhoneTF);

		companyEmailTF = new JTextField();
		setUpTextFieldInThePanel(companyEmailTF, 190, "companyEmailTextField", 0, insets, companyEmailLabel);
	}

	private void setUpLabelInThePanel(JLabel label, int insetsMiddle, JComponent relatedComponent) {
		int width = (int) label.getPreferredSize().getWidth();
		int height = (int) label.getPreferredSize().getHeight();
		label.setBounds((addCompanyPanel.getWidth() / 2) - (width / 2) + insetsMiddle,
				relatedComponent.getY() + relatedComponent.getHeight() + 23, width, height);
		addCompanyPanel.add(label);
	}

	private void setUpTextFieldInThePanel(JTextField textField, int width, String name, int insetsWidth, int insets,
			JLabel linkedLabel) {
		textField.setName(name);
		textField.setHorizontalAlignment(JTextField.CENTER);
		textField.setBounds((addCompanyPanel.getWidth() / 2) - (width / 2) + insetsWidth,
				linkedLabel.getY() + linkedLabel.getHeight() + insets, width, 28);
		addCompanyPanel.add(textField);
	}

	private void resetTextFields() {
		companyNameTF.setText("");
		companyVatTF.setText("");
		companyAddressTF.setText("");
		companyCityTF.setText("");
		companyZipTF.setText("");
		companyCountryTF.setText("");
		companyPhoneTF.setText("");
		companyEmailTF.setText("");
	}

	public boolean isSaving() {
		return isSaving;
	}

	@Override
	public void setAddingMode(boolean isSaving) {
		this.isSaving = isSaving;
		if (!isSaving) {
			companyNameTF.setText(myDatabaseUiComunication.getCurrentSelectedCompany().getName());
			companyVatTF.setText(myDatabaseUiComunication.getCurrentSelectedCompany().getVatCode());
			companyAddressTF.setText(myDatabaseUiComunication.getCurrentSelectedCompany().getAddress());
			companyCityTF.setText(myDatabaseUiComunication.getCurrentSelectedCompany().getCity());
			companyZipTF.setText(myDatabaseUiComunication.getCurrentSelectedCompany().getZipCode());
			companyCountryTF.setText(myDatabaseUiComunication.getCurrentSelectedCompany().getCountry());
			companyPhoneTF.setText(myDatabaseUiComunication.getCurrentSelectedCompany().getPhone());
			companyEmailTF.setText(myDatabaseUiComunication.getCurrentSelectedCompany().getEmail());
		}
		this.setVisible(true);
	}
}