package com.unifi.fatture.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ItemEvent;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import com.unifi.fatture.app.DatabaseUiComunication;

public class MainWindowUI {
	private DatabaseUiComunication myDatabaseUiComunication;

	private JFrame fattureAppFrame;
	private JLayeredPane outerPanel;

	private int buttonHeight = 30;
	private int buttonWidth = 50;

	private Color outerColor = new java.awt.Color(232, 246, 250);
	private Color layer1Color = new java.awt.Color(226, 244, 252);

	private Dimension windowDimension = new Dimension(500, 700);
	private PanelWithObligatoryTextFields addCompanyPanel;
	private PanelWithObligatoryTextFields addClientPanel;
	private PanelWithObligatoryTextFields addItemPanel;

	private static final String ARIAL = "Arial";

	public MainWindowUI(DatabaseUiComunication dbUiComm) {
		myDatabaseUiComunication = dbUiComm;
		initialize();
	}

	private void initialize(){
		final JPanel myCompanyPanel;

		fattureAppFrame = new JFrame();
		fattureAppFrame.setTitle("Fatture App");
		fattureAppFrame.setPreferredSize(windowDimension);
		fattureAppFrame.setResizable(false);
		fattureAppFrame.pack();
		fattureAppFrame.setBackground(java.awt.Color.LIGHT_GRAY);
		fattureAppFrame.getContentPane().setLayout(null);
		fattureAppFrame.setLocationRelativeTo(null);

		fattureAppFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		outerPanel = new JLayeredPane();
		outerPanel.setName("OuterPanel");
		outerPanel.setBackground(outerColor);
		outerPanel.setBounds(0, 0, fattureAppFrame.getContentPane().getWidth(),
				fattureAppFrame.getContentPane().getHeight());
		fattureAppFrame.getContentPane().add(outerPanel);
		outerPanel.setLayout(null);

		int outerInsets = 8;
		int topPanelHeight = 80;

		myCompanyPanel = new JPanel();
		myCompanyPanel.setName("CompanyPanel");
		myCompanyPanel.setBorder(BorderFactory.createLineBorder(Color.white));
		myCompanyPanel.setBounds(outerInsets, outerInsets, outerPanel.getWidth() - outerInsets * 2, topPanelHeight);
		outerPanel.add(myCompanyPanel);
		myCompanyPanel.setBackground(layer1Color);
		myCompanyPanel.setLayout(null);

		// MY COMPANY panel
		int innerInsets = 12;
		String myCompanyNameString = "My Company";
		JLabel myCompanyLabel = new JLabel(myCompanyNameString);
		myCompanyLabel.setName("currentSelectedCompanyLabel");
		myCompanyLabel.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		double width = 310;
		double height = myCompanyLabel.getPreferredSize().getHeight();
		myCompanyLabel.setBounds(innerInsets, innerInsets, (int) width, (int) height);
		myCompanyPanel.add(myCompanyLabel);

		JLabel myCompanyInfoLabel = new JLabel("info");
		width = 310;
		height = myCompanyLabel.getPreferredSize().getHeight();
		myCompanyInfoLabel.setBounds(innerInsets, myCompanyLabel.getY() + innerInsets + myCompanyLabel.getHeight(),
				(int) width, (int) height);
		myCompanyPanel.add(myCompanyInfoLabel);

		FormattedButton editMyCompanyButton = new FormattedButton("Edit", "EditCompanyButton");
		editMyCompanyButton.setEnabled(false);
		editMyCompanyButton.setBounds(myCompanyPanel.getWidth() - innerInsets - buttonWidth,
				(myCompanyPanel.getHeight() / 2) - (buttonHeight / 2), buttonWidth, buttonHeight);
		myCompanyPanel.add(editMyCompanyButton);
		outerPanel.setLayer(editMyCompanyButton, 1);
		editMyCompanyButton.addActionListener(e -> showGenericAddPanel(addCompanyPanel, false));
		myDatabaseUiComunication.setEditCompanyButton(editMyCompanyButton);

		FormattedButton addMyCompanyButton = new FormattedButton("Add", "AddCompanyButton");
		addMyCompanyButton.setBounds(editMyCompanyButton.getX() - innerInsets - buttonWidth,
				(myCompanyPanel.getHeight() / 2) - (buttonHeight / 2), buttonWidth, buttonHeight);
		myCompanyPanel.add(addMyCompanyButton);
		outerPanel.setLayer(addMyCompanyButton, 1);
		addMyCompanyButton.addActionListener(e -> showGenericAddPanel(addCompanyPanel, true));

		// ▲, ▼
		FormattedButton prevCompanyButton = new FormattedButton("▲", "prevCompany_Button");
		prevCompanyButton.setFont(new Font(ARIAL, Font.PLAIN, 10));
		int h = 16;
		int w = 16;
		prevCompanyButton.setBounds(addMyCompanyButton.getX() - innerInsets - w, myCompanyPanel.getHeight() / 2 - h,
				w, h);
		myCompanyPanel.add(prevCompanyButton);
		prevCompanyButton.addActionListener(e -> {
			// go to previous company
			if(myDatabaseUiComunication.getCompanyCounter()-1>=0) {
				myDatabaseUiComunication.setCompanyCounter(myDatabaseUiComunication.getCompanyCounter()-1);
			}
		});

		FormattedButton nextCompanyButton = new FormattedButton("▼", "nextCompany_Button");
		nextCompanyButton.setFont(new Font(ARIAL, Font.PLAIN, 10));
		h = 16;
		w = 16;
		nextCompanyButton.setBounds(addMyCompanyButton.getX() - innerInsets - w, myCompanyPanel.getHeight() / 2 + 2,
				w, h);
		myCompanyPanel.add(nextCompanyButton);
		nextCompanyButton.addActionListener(e -> {
			if(myDatabaseUiComunication.getCompanyCounter()+1<=myDatabaseUiComunication.getCompaniesCount()-1) {
				myDatabaseUiComunication.setCompanyCounter(myDatabaseUiComunication.getCompanyCounter()+1);
			}
		});

		// Invoice panel
		JPanel invoicePanel = new JPanel();
		invoicePanel.setName("InvoicePanel");
		invoicePanel.setBorder(BorderFactory.createLineBorder(Color.white));
		int invoicePanelY = (outerInsets * 2) + myCompanyPanel.getHeight();
		int invoicePanelWidth = outerPanel.getWidth() - (outerInsets * 2);
		int invoicePanelHeight = outerPanel.getHeight() - (invoicePanelY + outerInsets);
		invoicePanel.setBounds(outerInsets, invoicePanelY, invoicePanelWidth, invoicePanelHeight);
		outerPanel.add(invoicePanel);
		invoicePanel.setBackground(layer1Color);
		invoicePanel.setLayout(null);

		outerPanel.setLayer(invoicePanel, 1);

		// Client Panel
		JPanel clientPanel = new JPanel();
		clientPanel.setName("ClientPanel");
		clientPanel.setBackground(layer1Color);
		clientPanel.setBounds(innerInsets, innerInsets, invoicePanelWidth - (innerInsets * 2), 110);
		invoicePanel.add(clientPanel);
		clientPanel.setLayout(null);

		JLabel clientLbl = new JLabel("Client");
		clientLbl.setBounds((clientPanel.getWidth() / 2) - 26, innerInsets, 52, 16);
		clientLbl.setFont(new Font(ARIAL, Font.BOLD, 16));
		clientPanel.add(clientLbl);

		FormattedButton editClient = new FormattedButton("Edit", "editClientButton");
		editClient.setEnabled(false);
		editClient.setBounds(clientPanel.getWidth() - buttonWidth,
				(clientPanel.getHeight() / 2) - (buttonHeight / 2), buttonWidth, buttonHeight);
		editClient.addActionListener(e -> showGenericAddPanel(addClientPanel, false));
		clientPanel.add(editClient);
		myDatabaseUiComunication.seteditClientButton(editClient);
		outerPanel.setLayer(myCompanyPanel, 1);

		JComboBox<String> clientListComboBox = new JComboBox<>();
		clientListComboBox.setName("clientsComboBox");
		clientListComboBox.setBounds(0, (clientPanel.getHeight() / 2) - 14, 338, 28);
		clientPanel.add(clientListComboBox);
		clientListComboBox.addItemListener(e -> {
			if(e.getStateChange() == ItemEvent.SELECTED) {
				myDatabaseUiComunication.setCurrentSelectedClient(myDatabaseUiComunication.getSavedClients().get(clientListComboBox.getSelectedIndex()));
			}
		});

		FormattedButton addClient = new FormattedButton("Add", "AddClientButton");
		addClient.setBounds(editClient.getX() - innerInsets - buttonWidth,
				(clientPanel.getHeight() / 2) - (buttonHeight / 2), buttonWidth, buttonHeight);
		outerPanel.setLayer(myCompanyPanel, 1);
		clientPanel.add(addClient);
		addClient.addActionListener(e -> showGenericAddPanel(addClientPanel, true));

		// Description panel
		JPanel invoiceProvisionPanel = new JPanel();
		invoiceProvisionPanel.setName("InvoiceDescription");
		invoiceProvisionPanel.setBackground(layer1Color);
		invoiceProvisionPanel.setBounds(innerInsets, clientPanel.getY() + clientPanel.getHeight() + innerInsets, invoicePanelWidth - (innerInsets * 2), 110);
		invoicePanel.add(invoiceProvisionPanel);
		invoiceProvisionPanel.setLayout(null);

		JLabel invoiceItemLbl = new JLabel("Invoice item");
		invoiceItemLbl.setBounds((invoiceProvisionPanel.getWidth() / 2) - 54, innerInsets, 108, 16);
		invoiceItemLbl.setFont(new Font(ARIAL, Font.BOLD, 16));
		invoiceProvisionPanel.add(invoiceItemLbl);

		FormattedButton editInvoiceProvision = new FormattedButton("Edit", "editInvoiceButton");
		editInvoiceProvision.setEnabled(false);
		editInvoiceProvision.setBounds(invoiceProvisionPanel.getWidth() - buttonWidth,
				(invoiceProvisionPanel.getHeight() / 2) - (buttonHeight / 2), buttonWidth, buttonHeight);
		editInvoiceProvision.addActionListener(e -> showGenericAddPanel(addItemPanel, false));
		invoiceProvisionPanel.add(editInvoiceProvision);
		myDatabaseUiComunication.seteditInvoiceButton(editInvoiceProvision);

		JComboBox<String> invoiceListcomboBox = new JComboBox<>();
		invoiceListcomboBox.setName("invoicesComboBox");
		invoiceListcomboBox.setBounds(0, (invoiceProvisionPanel.getHeight() / 2) - 14, 338, 28);
		invoiceProvisionPanel.add(invoiceListcomboBox);
		invoiceListcomboBox.addItemListener(e -> {
			if(e.getStateChange() == ItemEvent.SELECTED) {
				myDatabaseUiComunication.setCurrentSelectedInvoice(myDatabaseUiComunication.getSavedInvoices().get(invoiceListcomboBox.getSelectedIndex()));
			}
		});

		FormattedButton addInvoiceProvision = new FormattedButton("Add", "addInvoiceButton");
		addInvoiceProvision.setBounds(editInvoiceProvision.getX() - innerInsets - buttonWidth,
				(invoiceProvisionPanel.getHeight() / 2) - (buttonHeight / 2), buttonWidth, buttonHeight);
		invoiceProvisionPanel.add(addInvoiceProvision);
		addInvoiceProvision.addActionListener(e -> showGenericAddPanel(addItemPanel, true));

		FormattedButton createInvoiceButton = new FormattedButton("CREATE INVOICE", "createInvoice_Button");
		createInvoiceButton.setFont(new Font(ARIAL, Font.PLAIN, 14));
		createInvoiceButton.setEnabled(true);
		createInvoiceButton.setBackground(Color.white);
		createInvoiceButton.setOpaque(true);
		h = 50;
		w = 180;
		createInvoiceButton.setBounds(invoicePanelWidth / 2 - (w / 2), invoicePanel.getHeight() - h - 8, w, h);
		invoicePanel.add(createInvoiceButton);

		createInvoiceButton.addActionListener(e -> myDatabaseUiComunication.printSelected());

		createAddRecordsPanels();
		updateReferences(clientListComboBox, invoiceListcomboBox, myCompanyLabel);

		fattureAppFrame.pack();
		fattureAppFrame.setVisible(true);
	}

	private void updateReferences(JComboBox<String> clientListComboBox, JComboBox<String> invoiceListcomboBox, JLabel companyInfo) {
		myDatabaseUiComunication.setClientsList(clientListComboBox);
		myDatabaseUiComunication.setInvoicesList(invoiceListcomboBox);
		myDatabaseUiComunication.setCompanyInfo(companyInfo);
		myDatabaseUiComunication.updateAllReferences();
	}

	private void createAddRecordsPanels() {
		addCompanyPanel = new CompanyPanel(outerPanel, buttonWidth, buttonHeight, myDatabaseUiComunication);
		addClientPanel = new ClientPanel(outerPanel, buttonWidth, buttonHeight, myDatabaseUiComunication);
		addItemPanel = new ItemInvoicePanel(outerPanel, buttonWidth, buttonHeight, myDatabaseUiComunication);
	}

	private void showGenericAddPanel(PanelWithObligatoryTextFields panelToShow, boolean isSaving) {
		panelToShow.setAddingMode(isSaving);
	}

	public JFrame getMainFrame() {
		return fattureAppFrame;
	}
}