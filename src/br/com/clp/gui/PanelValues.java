package br.com.clp.gui;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class PanelValues extends JPanel{
	private Border raisedbevel = BorderFactory.createRaisedBevelBorder();
	//private Border blackline = BorderFactory.createLineBorder(Color.black);
	protected TextField textFieldResultado1;
	protected TextField textFieldResultado2;
	protected TextField textFieldResultado3;
	
	public PanelValues() {
		this.setBounds(309, 10, 261, 213);
		this.setBorder(BorderFactory.createTitledBorder(raisedbevel, "Valor Monitorado"));
		this.setLayout(null);
		textFieldResultado1 = new TextField(55, 45, 116, 32, null, false, true, null);
		textFieldResultado2 = new TextField(55, 87, 116, 32, null, false, true, null);
		textFieldResultado3 = new TextField(55, 129, 116, 32, null, false, true, null);
		add(textFieldResultado1);
		add(textFieldResultado2);
		add(textFieldResultado3);
		Label label1 = new Label(31, 45, 14, 29, "1");
		Label label2 = new Label(31, 86, 14, 29, "2");
		Label label3 = new Label(31, 127, 14, 29, "3");
		add(label1);
		add(label2);
		add(label3);
	}
}
