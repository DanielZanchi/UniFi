package com.unifi.fatture.ui;

import java.awt.Font;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;

import com.unifi.fatture.app.DatabaseUiComunication;

public class ItemInvoicePanel extends PanelWithObligatoryTextFields {
	private static final long serialVersionUID = 8698651509983266694L;

	private JTextField itemDescriptionTF;
	private JTextField itemNameTF;
	private JFormattedTextField itemPriceTF;

	private transient DatabaseUiComunication databaseUiComunication;

	private ItemInvoicePanel addItemPanel;
	private boolean isSaving;

	public ItemInvoicePanel(JLayeredPane outerPanel, int buttonWidth, int buttonHeight, DatabaseUiComunication dbUiCom) {
		super("AddInvoicePanel", outerPanel, buttonWidth, buttonHeight, 250);
		this.databaseUiComunication = dbUiCom;
		addItemPanel = this;

		initLabelsTextFields();

		FormattedButton saveButton = new FormattedButton("Save", "SaveButton");
		saveButton.setEnabled(false);
		saveButton.setBounds((addItemPanel.getWidth() / 2) + 24,
				addItemPanel.getHeight() - 20 - addItemPanel.getY(), buttonWidth, buttonHeight);
		addItemPanel.add(saveButton);
		saveButton.addActionListener(e -> {
			// save invoice
			if(addItemPanel.isSaving()) {
				databaseUiComunication.addInvoiceToDatabase(itemNameTF.getText(), itemDescriptionTF.getText(), itemPriceTF.getText());
			}else {
				databaseUiComunication.editInvoiceFromDatabase(itemNameTF.getText(), itemPriceTF.getText(), itemDescriptionTF.getText());
			}
			addItemPanel.setVisible(false);
			databaseUiComunication.updateInvoicesReferences();
		});

		String[] freeTextFields = {""};
		super.setUpTextFields(addItemPanel.getComponents(), freeTextFields, saveButton);
	}

	private void initLabelsTextFields() {
		int addPanelY = addItemPanel.getY();
		int insets = 8;
		int insetsMiddle = 23;

		JLabel addItemTitleLabel = new JLabel("Item");
		addItemTitleLabel.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		int width = (int) addItemTitleLabel.getPreferredSize().getWidth();
		int height = (int) addItemTitleLabel.getPreferredSize().getHeight();
		addItemTitleLabel.setBounds((addItemPanel.getWidth() / 2) - (width / 2), addPanelY - 10, width, height);
		addItemPanel.add(addItemTitleLabel);

		JLabel itemNameLabel = new JLabel("Item Name:");
		setUpLabelInThePanel(itemNameLabel, 0, addItemTitleLabel);

		itemNameTF = new JTextField();
		setUpTextFieldInThePanel(itemNameTF, 200, "invoiceName_TF", 0, insetsMiddle, itemNameLabel);

		JLabel itemDescriptionLabel = new JLabel("Item Description:");
		setUpLabelInThePanel(itemDescriptionLabel, 0, itemNameTF);

		itemDescriptionTF = new JTextField();
		setUpTextFieldInThePanel(itemDescriptionTF, 350, "invoiceDescription_TF", 0, insetsMiddle, itemDescriptionLabel);

		JLabel itemPriceLabel = new JLabel("Price (incl. VAT):");
		setUpLabelInThePanel(itemPriceLabel, 0, itemDescriptionTF);

		NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
		DecimalFormat decimalFormat = (DecimalFormat) numberFormat;
		decimalFormat.setGroupingUsed(false);
		itemPriceTF = new JFormattedTextField(decimalFormat);
		itemPriceTF.setColumns(15);
		width = 70;
		itemPriceTF.setHorizontalAlignment(JTextField.CENTER);
		itemPriceTF.setName("invoicePrice_TF");
		itemPriceTF.setBounds((addItemPanel.getWidth() / 2) - (width / 2),
				itemPriceLabel.getY() + itemPriceLabel.getHeight() + insets, width, 28);
		addItemPanel.add(itemPriceTF);
	}

	private void setUpLabelInThePanel(JLabel label, int insetsMiddle, JComponent relatedComponent) {
		int width = (int) label.getPreferredSize().getWidth();
		int height = (int) label.getPreferredSize().getHeight();
		label.setBounds((addItemPanel.getWidth() / 2) - (width / 2) + insetsMiddle, relatedComponent.getY() + relatedComponent.getHeight() + 23, width, height);
		addItemPanel.add(label);
	}

	private void setUpTextFieldInThePanel(JTextField textField, int width, String name, int insetsWidth, int insets, JLabel linkedLabel) {
		textField.setName(name);
		textField.setHorizontalAlignment(JTextField.CENTER);
		textField.setBounds((addItemPanel.getWidth() / 2) - (width / 2) + insetsWidth, linkedLabel.getY() + linkedLabel.getHeight() + insets, width, 28);
		addItemPanel.add(textField);
	}

	public boolean isSaving() {
		return isSaving;
	}

	@Override
	public void setAddingMode(boolean isSaving) {
		this.isSaving = isSaving;
		if(!isSaving) {
			itemNameTF.setText(databaseUiComunication.getCurrentSelectedInvoice().getName());
			itemPriceTF.setText(databaseUiComunication.getCurrentSelectedInvoice().getPrice());
			itemDescriptionTF.setText(databaseUiComunication.getCurrentSelectedInvoice().getDescription());
		}
		this.setVisible(true);
	}
}