package com.clp.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JTextField;

import com.clp.comunication.ConnectionS7;

public class TestConListener implements ActionListener {
	private JTextField fieldIP;
	private JLabel lblStatus;

	public TestConListener(JTextField field, JLabel label) {
		this.fieldIP = field;
		this.lblStatus = label;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				ConnectionS7 con = new ConnectionS7(fieldIP.getText());
				con.connect();
				if (con.isConnected()) {
					lblStatus.setText("Conexão OK.");
				} else {
					lblStatus.setText("Sem conexão.");
				}
				con.disconnect();
			}
		});
		thread.run();
	}
}
