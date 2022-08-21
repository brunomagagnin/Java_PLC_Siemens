package br.com.clp.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JTextField;

import br.com.clp.comunication.ConnectionS7;

public class TestConListener implements ActionListener {
	private JTextField textIP;
	private JLabel lblStatus;

	public TestConListener(JTextField field, JLabel label) {
		this.textIP = field;
		this.lblStatus = label;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				ConnectionS7 conectar = new ConnectionS7(textIP.getText());
				conectar.connect();
				if (conectar.statusConnect()) {
					lblStatus.setText("Conexão OK!");
				} else {
					lblStatus.setText("Sem conexão!");
				}
				conectar.disconnect();
			}
		});
		thread.run();
	}
}
