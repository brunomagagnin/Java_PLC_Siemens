package com.clp.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.clp.comunication.DriverS7;
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
		try {
			try {
				if (!checkBoxHold.isSelected()) {
					verifyCheck();
					Thread thread = new Thread(new Runnable() {
						@Override
						public void run() {
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
								driver.disconnect();
							} else {
								System.out.println("Sem conexão com CLP."); // Deve lançar exception
							}
						}
					});
					thread.start();
				}
			} catch (VerifyException v) {

			} finally {
				JFrame frame = new JFrame();
				JOptionPane.showMessageDialog(frame, "Verifique qual campo está preenchido errado.");
			}

		} catch (ArrayIndexOutOfBoundsException | NullPointerException exc) {
			exc.printStackTrace();
		} finally {
			JFrame frame = new JFrame();
			JOptionPane.showMessageDialog(frame, "Ocorreu um erro inesperado, favor contatar o desenvolvedor.");
		}
	}

	private void verifyCheck() {
		// Verifica quais CheckBox est�o selecionados e com endere�o validos
		listCheckBoolean.clear();
		checkTrue = false;
		for (int i = 0; i < listCheckBox.size(); i++) {
			if (listCheckBox.get(i).isSelected() == true) {
				VerifyStringDB.verifyText(listFieldDB.get(i).getText());
				checkTrue = true;
				listCheckBoolean.add(true);
			} else {
				listCheckBoolean.add(false);
			}
		}
	}

	public void fillLists(List<CheckBox> listCheckBox, List<TextField> listFieldDB, List<TextField> listFieldResult) {
		this.listCheckBox = listCheckBox;
		this.listFieldDB = listFieldDB;
		this.listFieldResult = listFieldResult;
	}
}
