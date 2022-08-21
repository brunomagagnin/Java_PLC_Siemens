package com.clp.gui;

import java.awt.CheckboxGroup;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class PanelDB extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Border raisedbevel = BorderFactory.createRaisedBevelBorder();
	//private Border blackline = BorderFactory.createLineBorder(Color.black);
	protected TextField textFieldDB1;
	protected TextField textFieldDB2;
	protected TextField textFieldDB3;
	protected CheckBox checkBox1;
	protected CheckBox checkBox2;
	protected CheckBox checkBox3;
	private Label label1;
	private Label label2;
	private Label label3;
	
	public PanelDB() {
		this.setBounds(23, 10, 268, 213);
		this.setBorder(BorderFactory.createTitledBorder(raisedbevel, "Endere�o de Memória"));
		this.setLayout(null);
		
		add(textFieldDB1 = new TextField(44, 51, 116, 32, null, true, false, null));
		add(textFieldDB2 = new TextField(44, 93, 116, 32, null, true, false, null));
		add(textFieldDB3 = new TextField(44, 135, 116, 32, null, true, false, null));
	
		add(label1 = new Label(20, 51, 14, 29, "1"));
		add(label2 = new Label(20, 93, 14, 29, "2"));
		add(label3 = new Label(20, 135, 14, 29, "3"));
		
		add(checkBox1 = new CheckBox(169, 56, 93, 21, false,""));
		add(checkBox2 = new CheckBox(169, 98, 93, 21, false, ""));
		add(checkBox3 = new CheckBox(169, 140, 93, 21, false, ""));
	}
	
	
}
