package com.clp.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.clp.comunication.DriverS7;
import com.clp.exceptions.NoConnectionException;
import com.clp.exceptions.VerifyException;
import com.clp.gui.Button;
import com.clp.gui.CheckBox;
import com.clp.gui.TextField;
import com.clp.utilities.FilterStringDB;
import com.clp.utilities.VerifyStringDB;

public class HoldStart implements ActionListener {
	// INICIALIZA LISTAS
	private List<CheckBox> listCheckBox = new ArrayList<CheckBox>();
	private List<Boolean> listCheckBoolean = new ArrayList<Boolean>();
	private List<TextField> listFieldDB = new ArrayList<TextField>();
	private List<TextField> listFieldResult = new ArrayList<TextField>();

	private CheckBox checkBoxHold;
	private Button buttonStop;
	private Button buttonTest;
	private TextField fieldIP;

	private DriverS7 driver;
	private Timer timer;
	private CycloStartState cycloStartState;

	private boolean checkTrue = false;

	public HoldStart(Button buttonStop, Button buttonTest, TextField fieldIP, CheckBox checkBoxHold,
			CycloStartState cycloStartState) {
		this.buttonStop = buttonStop;
		this.buttonTest = buttonTest;
		this.fieldIP = fieldIP;
		this.checkBoxHold = checkBoxHold;
		this.cycloStartState = cycloStartState;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Bloco de monitoramento contínuo - Quando CheckBox "Monitoramento Contínuo"
		// selecionado

		try {
			verifyCheck();
			verifyString();
			if (checkBoxHold.isSelected() && !cycloStartState.getState() && checkTrue) {
				timer = new Timer();
				driver = new DriverS7(fieldIP.getText());
				checkBoxHold.setEnabled(false);
				buttonStop.setEnabled(true);
				cycloStartState.setState(true);
				buttonTest.setEnabled(false);

				timer.scheduleAtFixedRate(new TimerTask() {
					@Override
					public void run() { // esse
						try {
							driver.connect();
							if (driver.isConnected()) {
								for (int index = 0; index < listCheckBoolean.size(); index++) {
									if (listCheckBoolean.get(index)) {
										char i = FilterStringDB.getTypeMemory(listFieldDB.get(index).getText());
										switch (i) {
										case 'X':
											driver.getBit(listFieldResult, listFieldDB, index);
											break;

										case 'W':
											driver.getWord(listFieldResult, listFieldDB, index);
											break;

										case 'D':
											driver.getDouble(listFieldResult, listFieldDB, index);
											break;
										}
									}
								}
								driver.disconnect();
							} else {
								driver.countTryConnection();
								if (driver.getTryConnection() == 3) {
									timer.cancel();
									buttonTest.setEnabled(true);
									cycloStartState.setState(false);
									checkBoxHold.setEnabled(true);
									buttonStop.setEnabled(false);
									throw new NoConnectionException();
								}
							}
						} catch (NoConnectionException noCon) {
							JOptionPane.showMessageDialog(new JFrame(), noCon.getMessage());
						}
					} // esse
				}, 50, 500);
			}
		} catch (VerifyException exc) {
			JOptionPane.showMessageDialog(new JFrame(), exc.getMessage());
		} catch (ArrayIndexOutOfBoundsException | NullPointerException err) {
			JOptionPane.showMessageDialog(new JFrame(), "Ocorreu um erro inesperado." + err.getMessage());
		}
	}

	private void verifyCheck() {
		// Verifica quais CheckBox estão selecionados e com endereço válidos
		listCheckBoolean.clear();
		checkTrue = false;
		for (int i = 0; i < listCheckBox.size(); i++) {
			if (listCheckBox.get(i).isSelected() == true) {
				checkTrue = true;
				listCheckBoolean.add(true);
			} else {
				listCheckBoolean.add(false);
			}
		}
	}

	private void verifyString() {
		for (int i = 0; i < listCheckBox.size(); i++) {
			if (listCheckBox.get(i).isSelected() == true) {
				String str = listFieldDB.get(i).getText();
				VerifyStringDB.verifyText(str);
			}
		}
	}

	public void fillLists(List<CheckBox> listCheckBox, List<TextField> listFieldDB, List<TextField> listFieldValues) {
		this.listCheckBox = listCheckBox;
		this.listFieldDB = listFieldDB;
		this.listFieldResult = listFieldValues;
	}

	public Timer getTimer() {
		return this.timer;
	}
}
