package com.clp.gui;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class PanelResult extends JPanel{
	private Border raisedbevel = BorderFactory.createRaisedBevelBorder();
	//private Border blackline = BorderFactory.createLineBorder(Color.black);
	protected TextField textFieldResult1;
	protected TextField textFieldResult2;
	protected TextField textFieldResult3;
	
	public PanelResult() {
		this.setBounds(309, 10, 261, 213);
		this.setBorder(BorderFactory.createTitledBorder(raisedbevel, "Valor Monitorado"));
		this.setLayout(null);
		textFieldResult1 = new TextField(55, 45, 116, 32, null, false, true, null);
		textFieldResult2 = new TextField(55, 87, 116, 32, null, false, true, null);
		textFieldResult3 = new TextField(55, 129, 116, 32, null, false, true, null);
		add(textFieldResult1);
		add(textFieldResult2);
		add(textFieldResult3);
		Label label1 = new Label(31, 45, 14, 29, "1");
		Label label2 = new Label(31, 86, 14, 29, "2");
		Label label3 = new Label(31, 127, 14, 29, "3");
		add(label1);
		add(label2);
		add(label3);
	}
}
