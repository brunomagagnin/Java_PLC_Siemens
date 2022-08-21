package com.clp.gui;

import java.awt.Font;

import javax.swing.JCheckBox;

@SuppressWarnings("serial")
public class CheckBox extends JCheckBox {

	public CheckBox(int x, int y, int width, int heigth, boolean selected, String text) {
		super(text);
		this.setBounds(x, y, width, heigth);
		this.setFont(new Font("Tahoma", Font.PLAIN, 9));
		this.setSelected(selected);
	}
}
