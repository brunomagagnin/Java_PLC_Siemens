package br.com.clp.gui;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class PanelTest extends JPanel {
	private Border raisedbevel = BorderFactory.createRaisedBevelBorder();
	//private Border blackline = BorderFactory.createLineBorder(Color.black);
	
	protected Button btnTeste;
	private Label lblTeste;
	protected Label lblStatus;

	public PanelTest() {
		this.setBounds(309, 241, 261, 197);
		this.setBorder(BorderFactory.createTitledBorder(raisedbevel, "Teste de Conex�o"));
		this.setLayout(null);
		
		lblTeste = new Label(15, 62, 126, 19, "Status da Conex�o: ");
		lblStatus = new Label(120, 62, 126, 19, "Aguardando teste");
		btnTeste = new Button(10, 99, 222, 32, "Teste de Conex�o");
		
		this.add(lblTeste);
		this.add(lblStatus);
		this.add(btnTeste);
	}

}
