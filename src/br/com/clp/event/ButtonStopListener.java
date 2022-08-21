package br.com.clp.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import br.com.clp.gui.CheckBox;

public class ButtonStopListener implements ActionListener {
	private JButton btnTest;
	private JButton btnStop;
	private CheckBox checkMonitor;
	private CycloState state;
	private HideStart hideStart;
	
	public ButtonStopListener(JButton stop, JButton test, CheckBox checkMonitor, CycloState state, HideStart hideStart) {
		this.btnStop = stop;
		this.btnTest = test;
		this.checkMonitor = checkMonitor;
		this.state = state;
		this.hideStart = hideStart;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			btnStop.setEnabled(false);
			btnTest.setEnabled(true);
			checkMonitor.setEnabled(true);
			this.state.setState(false);
			hideStart.getTimer().cancel();
			
		} catch (Exception stop) {
			System.out.println(" Botão Stop zoado: " + stop.getMessage() + " - " + stop.getCause() + " - " + stop.getStackTrace());
		}
	}
}
