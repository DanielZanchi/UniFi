package com.unifi.fatture.ui;

import java.awt.Color;
import java.awt.Cursor;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class FormattedButton extends JButton {	
	private static final long serialVersionUID = 7934275814596911716L;

	public FormattedButton(String text, String name) {
		this.setText(text);
		this.setName(name);
		this.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}
}