package com.clp.gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JFormattedTextField;
import javax.swing.text.MaskFormatter;

@SuppressWarnings("serial")
public class TextField extends JFormattedTextField {

	public TextField(int x, int y, int width, int height, MaskFormatter mask,  boolean editable, boolean enabled, String text) {
		super(mask);
		this.setForeground(Color.BLACK);
		this.setFont(new Font("Tahoma", Font.PLAIN, 12));
		this.setEditable(editable);
		this.setEnabled(enabled);
		this.setColumns(10);
		this.setBounds(x, y ,width, height);
		this.setText(text);
	}
}
