package br.com.clp.gui;

import java.awt.Font;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Label extends JLabel {
	
	public Label(int x, int y, int width, int height, String text) {
		super(text);
		this.setFont(new Font("Tahoma", Font.PLAIN, 12));
		this.setBounds(x, y, width, height);
	}
}
