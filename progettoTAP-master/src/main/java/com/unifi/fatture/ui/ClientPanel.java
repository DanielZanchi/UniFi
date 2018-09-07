package com.unifi.fatture.ui;

import java.awt.Font;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;

import com.unifi.fatture.app.AddClientToDatabaseParameter;
import com.unifi.fatture.app.DatabaseUiComunication;

public class ClientPanel extends PanelWithObligatoryTextFields {
	private static final long serialVersionUID = -4964123340815964907L;

	private transient DatabaseUiComunication myDatabaseUiComunication;

	private JTextField clientNameTF;
	private JTextField clientVatTF;
	private JTextField clientAddressTF;
	private JTextField clientCityTF;
	private JTextField clientZipTF;
	private JTextField clientCountryTF;
	private JTextField clientPhoneTF;
	private JTextField clientEmailTF;

	private ClientPanel addClientPanel;
	private boolean isSaving;

	public ClientPanel(JLayeredPane outerPanel, int buttonWidth, int buttonHeight, DatabaseUiComunication dbUiCom) {
		super("AddClientPanel", outerPanel, buttonWidth, buttonHeight, 0);
		addClientPanel = this;
		myDatabaseUiComunication = dbUiCom;

		initLabelsTextFields();

		FormattedButton saveButton = new FormattedButton("Save", "SaveButton");
		saveButton.setEnabled(false);
		saveButton.setBounds((addClientPanel.getWidth() / 2) + 24,
				addClientPanel.getHeight() - 20 - addClientPanel.getY(), buttonWidth, buttonHeight);
		addClientPanel.add(saveButton);

		saveButton.addActionListener(e -> {
			// save company
			if(addClientPanel.isSaving()) {
				myDatabaseUiComunication.addClientToDatabase(clientNameTF.getText(), clientVatTF.getText(),
						new AddClientToDatabaseParameter(clientAddressTF.getText(), clientCityTF.getText(), clientZipTF.getText(), clientCityTF.getText()), clientPhoneTF.getText(),
						clientEmailTF.getText());
			}else {
				myDatabaseUiComunication.editClientFromDatabase(clientNameTF.getText(),
						clientVatTF.getText(), new AddClientToDatabaseParameter(clientAddressTF.getText(), clientCityTF.getText(), clientZipTF.getText(), clientCityTF.getText()),
						clientPhoneTF.getText(), clientEmailTF.getText());
			}
			addClientPanel.setVisible(false);
			resetTextFields();
			dbUiCom.updateClientsReferences();
		});

		String[] freeTextFields = {"clientPhone_TF", "clientEmail_TF"};
		super.setUpTextFields(addClientPanel.getComponents(), freeTextFields, saveButton);
	}

	private void initLabelsTextFields() {
		int addPanelY = addClientPanel.getY();
		int insets = 8;
		int insetsMiddle = 80;

		JLabel addClientTitleLabel = new JLabel("Client");
		addClientTitleLabel.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		addClientTitleLabel.setBounds((int) (((double)addClientPanel.getWidth() / 2) - (addClientTitleLabel.getPreferredSize().getWidth() / 2)), addPanelY - 10, (int)addClientTitleLabel.getPreferredSize().getWidth(), (int)addClientTitleLabel.getPreferredSize().getHeight());
		addClientPanel.add(addClientTitleLabel);

		JLabel clientNameLabel = new JLabel("Client Name:");
		setUpLabelInThePanel(clientNameLabel, 0, addClientTitleLabel);

		clientNameTF = new JTextField();
		setUpTextFieldInThePanel(clientNameTF, 300, "clientName_TF", 0, insets, clientNameLabel);

		JLabel clientVatLabel = new JLabel("VAT / Fiscal Number:");
		setUpLabelInThePanel(clientVatLabel, 0, clientNameTF);

		clientVatTF = new JTextField();
		setUpTextFieldInThePanel(clientVatTF, 200, "clientVat_TF", 0, insets, clientVatLabel);

		
		
		JLabel clientAddressLabel = new JLabel("Address:");
		setUpLabelInThePanel(clientAddressLabel, 0, clientVatTF);

		clientAddressTF = new JTextField();
		setUpTextFieldInThePanel(clientAddressTF, 300, "clientAddress_TF", 0, insets, clientAddressLabel);

		
		
		JLabel clientCityLabel = new JLabel("City:");
		setUpLabelInThePanel(clientCityLabel, 0, clientAddressTF);

		clientCityTF = new JTextField();
		setUpTextFieldInThePanel(clientCityTF, 300, "clientCity_TF", 0, insets, clientCityLabel);

		
		
		JLabel clientZipLabel = new JLabel("ZIP Code:");
		setUpLabelInThePanel(clientZipLabel, -insetsMiddle, clientCityTF);

		clientZipTF = new JTextField();
		setUpTextFieldInThePanel(clientZipTF, 80, "clientZip_TF", - insetsMiddle, insets, clientZipLabel);

		JLabel clientCountryLabel = new JLabel("Country:");
		setUpLabelInThePanel(clientCountryLabel, insetsMiddle, clientCityTF);

		clientCountryTF = new JTextField();
		setUpTextFieldInThePanel(clientCountryTF, 100, "clientCountry_TF",  insetsMiddle, insets, clientCountryLabel);

		JLabel clientPhoneLabel = new JLabel("Phone:");
		setUpLabelInThePanel(clientPhoneLabel, 0, clientCountryTF);

		clientPhoneTF = new JTextField();
		setUpTextFieldInThePanel(clientPhoneTF, 150, "clientPhone_TF",0, insets, clientPhoneLabel);

		JLabel clientEmailLabel = new JLabel("Email:");
		setUpLabelInThePanel(clientEmailLabel, 0, clientPhoneTF);

		clientEmailTF = new JTextField();
		setUpTextFieldInThePanel(clientEmailTF, 190, "clientEmail_TF",0, insets, clientEmailLabel);
	}

	private void setUpLabelInThePanel(JLabel label, int insetsMiddle, JComponent relatedComponent) {
		int width = (int) label.getPreferredSize().getWidth();
		int height = (int) label.getPreferredSize().getHeight();
		label.setBounds((addClientPanel.getWidth() / 2) - (width / 2)+insetsMiddle, relatedComponent.getY() + relatedComponent.getHeight() + 23, width, height);
		addClientPanel.add(label);
	}

	private void setUpTextFieldInThePanel(JTextField textField, int width, String name, int insetsWidth, int insets, JLabel linkedLabel) {
		textField.setName(name);
		textField.setHorizontalAlignment(JTextField.CENTER);
		textField.setBounds((addClientPanel.getWidth() / 2) - (width / 2)+insetsWidth, linkedLabel.getY() + linkedLabel.getHeight() + insets, width, 28);
		addClientPanel.add(textField);
	}

	private void resetTextFields() {
		clientNameTF.setText("");
		clientVatTF.setText("");
		clientAddressTF.setText("");
		clientCityTF.setText("");
		clientZipTF.setText("");
		clientCountryTF.setText("");
		clientPhoneTF.setText("");
		clientEmailTF.setText("");
	}

	public boolean isSaving() {
		return isSaving;
	}

	@Override
	public void setAddingMode(boolean isSaving) {
		this.isSaving = isSaving;
		if(!isSaving) {
			clientNameTF.setText(myDatabaseUiComunication.getCurrentSelectedClient().getName());
			clientVatTF.setText(myDatabaseUiComunication.getCurrentSelectedClient().getFiscalCode());
			clientAddressTF.setText(myDatabaseUiComunication.getCurrentSelectedClient().getCityResidence());
			clientCityTF.setText(myDatabaseUiComunication.getCurrentSelectedClient().getCity());
			clientZipTF.setText(myDatabaseUiComunication.getCurrentSelectedClient().getZip());
			clientCountryTF.setText(myDatabaseUiComunication.getCurrentSelectedClient().getCountry());
			clientPhoneTF.setText(myDatabaseUiComunication.getCurrentSelectedClient().getPhone());
			clientEmailTF.setText(myDatabaseUiComunication.getCurrentSelectedClient().getEmail());
		}
		this.setVisible(true);
	}
}