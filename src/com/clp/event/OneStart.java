package com.clp.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.clp.comunication.DriverS7;
import com.clp.exceptions.NoConnectionException;
import com.clp.exceptions.VerifyException;
import com.clp.gui.CheckBox;
import com.clp.gui.TextField;
import com.clp.utilities.FilterStringDB;
import com.clp.utilities.VerifyStringDB;

public class OneStart implements ActionListener {
	// INICIALIZA LISTAS
	private List<CheckBox> listCheckBox = new ArrayList<CheckBox>();
	private List<Boolean> listCheckBoolean = new ArrayList<Boolean>();
	private List<TextField> listFieldDB = new ArrayList<TextField>();
	private List<TextField> listFieldResult = new ArrayList<TextField>();

	private CheckBox checkBoxHold;
	private TextField fieldIP;

	private DriverS7 driver;
	private boolean checkTrue = false;

	public OneStart(TextField fieldIP, CheckBox checkBoxHold) {
		this.fieldIP = fieldIP;
		this.checkBoxHold = checkBoxHold;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (!checkBoxHold.isSelected()) {
			try {
				verifyCheck();
				verifyString();
				Thread thread = new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							driver = new DriverS7(fieldIP.getText());
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
							}
							driver.disconnect();
						} catch (NoConnectionException noCon) {
							JOptionPane.showMessageDialog(new JFrame(), noCon.getMessage());
						}
					}
				});
				thread.start();
			} catch (VerifyException exc) {
				JOptionPane.showMessageDialog(new JFrame(), "Verifique o campo preenchido errado.");
			} catch (ArrayIndexOutOfBoundsException | NullPointerException err) {
				JOptionPane.showMessageDialog(new JFrame(), "Ocorreu um erro inesperado." + err.getMessage());
			}
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
			VerifyStringDB.verifyText(listFieldDB.get(i).getText());
		}
	}

	public void fillLists(List<CheckBox> listCheckBox, List<TextField> listFieldDB, List<TextField> listFieldResult) {
		this.listCheckBox = listCheckBox;
		this.listFieldDB = listFieldDB;
		this.listFieldResult = listFieldResult;
	}
}
