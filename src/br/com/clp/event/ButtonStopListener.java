package br.com.clp.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import br.com.clp.gui.CheckBox;

public class ButtonStopListener implements ActionListener {
	private JButton buttonTest;
	private JButton buttonStop;
	private CheckBox checkMonitor;
	private CycloStartState cycloStartState;
	private HoldStart holdStart;

	public ButtonStopListener(JButton buttonStop, JButton buttonTest, CheckBox checkBoxHold, CycloStartState cycloStartState,
			HoldStart holdStart) {
		this.buttonStop = buttonStop;
		this.buttonTest = buttonTest;
		this.checkMonitor = checkBoxHold;
		this.cycloStartState = cycloStartState;
		this.holdStart = holdStart;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		buttonStop.setEnabled(false);
		buttonTest.setEnabled(true);
		checkMonitor.setEnabled(true);
		this.cycloStartState.setState(false);
		holdStart.getTimer().cancel();
	}
}
