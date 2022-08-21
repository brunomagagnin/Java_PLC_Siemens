package br.com.clp.gui;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class Button extends JButton{
	
	public Button(int x, int y, int width, int heigth, String text) {
		super(text);
		this.setBounds(x, y, width, heigth);
	}
}
