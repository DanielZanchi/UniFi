package com.unifi.fatture.ui;

import java.util.List;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class ObligatoryTextFieldListener implements DocumentListener{
	private List<JTextField> textFields;
	private FormattedButton saveButton;
	
	public ObligatoryTextFieldListener(List<JTextField> textFields, FormattedButton saveButton ) {
		this.textFields = textFields;
		this.saveButton = saveButton;
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		changed();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		changed();
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		//Plain text components do not fire these events, so this block isn't covered
	}

	public void changed() {
		boolean shouldActivate = true;
		for (JTextField tf : textFields) {
			if (tf.getText().equals("")) {
				shouldActivate = false;
				break;
			}
		}
		saveButton.setEnabled(shouldActivate);
	}
}