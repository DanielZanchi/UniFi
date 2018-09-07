package com.unifi.fatture.ui;

import java.awt.Color;
import java.awt.Component;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public abstract class PanelWithObligatoryTextFields extends JPanel {
	private static final long serialVersionUID = 7883635384945697293L;
	private LinkedList<JTextField> textFields;
	private Color layerColor = new java.awt.Color(216, 245, 255);
	private int insets = 22;
	public abstract void setAddingMode(boolean isSaving);

	public PanelWithObligatoryTextFields(String panelName, JLayeredPane outerPanel, int buttonWidth, int buttonHeight, int heightOffset) {
		this.setBackground(layerColor);
		this.setName(panelName);
		this.setVisible(false);
		this.setBorder(BorderFactory.createLineBorder(Color.white, 3));	

		int width = outerPanel.getWidth() - insets - insets;
		int height = outerPanel.getHeight() - insets - insets-heightOffset;
		this.setBounds(insets, insets, width, height);
		outerPanel.add(this);
		this.setLayout(null);
		outerPanel.setLayer(this, 2);


		FormattedButton cancelButton = new FormattedButton("Cancel", "CancelButton");
		cancelButton.setBounds((this.getWidth() / 2) - buttonWidth - 24,
				this.getHeight() - 20 - this.getY(), buttonWidth, buttonHeight);
		this.add(cancelButton);
		cancelButton.addActionListener(e ->	this.setVisible(false));

	}

	private void addComponent(Component[] components, String[] textFieldsNotObligatory) {
		for (Component component : components) {
			if (component.getClass().equals(JTextField.class)) {
				boolean sameName = false;
				for(int i = 0; i<textFieldsNotObligatory.length; i++) {
					if(((JTextField) component).getName().equals(textFieldsNotObligatory[i])) {
						sameName = true;
						break;
					}
				}
				if(!sameName) {
					textFields.add((JTextField) component);
				}
			}
		}
	}
	
	protected void setUpTextFields(Component[] components, String[] textFieldsNotObligatory, FormattedButton saveButton) {
		textFields = new LinkedList<>();
		
		addComponent(components, textFieldsNotObligatory);

		for (JTextField tf : textFields) {
			tf.getDocument().addDocumentListener(new ObligatoryTextFieldListener(textFields,saveButton));
		}
	}	
}