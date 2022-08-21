package br.com.clp.gui;

import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.text.MaskFormatter;

@SuppressWarnings("serial")
public class PanelConfig extends JPanel {
	private Border raisedbevel = BorderFactory.createRaisedBevelBorder();
	//private Border blackline = BorderFactory.createLineBorder(Color.black);
	protected Button btnStart;
	protected Button btnStop;
	protected TextField textIP;
	protected CheckBox checkMonitor;
	public PanelConfig() {
		this.setBounds(23, 241, 268, 197);
		this.setBorder(BorderFactory.createTitledBorder(raisedbevel, "Configurações"));
		this.setLayout(null);
		
		Label labelIP = new Label(10, 23, 116, 29, "Endere\u00E7o de IP"); 
		Label labelStatus = new Label(10, 62, 126, 19, "Desconectado");
		add(labelIP);
		add(labelStatus);
		
		checkMonitor = new CheckBox(112, 59, 150, 21, true, "Monitoramento cont\u00EDnuo");
		add(checkMonitor);
		
		MaskFormatter maskIP = null;
		try {
			maskIP = new MaskFormatter("###.###.###.###");
			maskIP.setPlaceholderCharacter('_');
		} catch (ParseException e) {
			System.out.println();
		}
		textIP = new TextField(116, 21, 116, 32, maskIP, true, true, "192168000001");
		add(textIP);
		
		btnStart = new Button(10, 99, 222, 32, "START");
		btnStop = new Button(10, 141, 222, 32, "STOP");
		btnStop.setEnabled(false);
		add(btnStart);
		add(btnStop);
	}
}
