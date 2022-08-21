package br.com.clp.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import br.com.clp.comunication.DriverS7;
import br.com.clp.gui.CheckBox;
import br.com.clp.gui.TextField;
import br.com.clp.utilities.CheckStringDB;
import br.com.clp.utilities.FilterStringDB;

public class OneStart implements ActionListener {
	// INICIALIZA LISTAS
	private List<CheckBox> listCheckBox = new ArrayList<CheckBox>();
	private List<Boolean> listCheckBoolean = new ArrayList<Boolean>();
	private List<TextField> listFieldDB = new ArrayList<TextField>();
	private List<TextField> listFieldValues = new ArrayList<TextField>();

	private CheckBox checkMonitoramento;
	private TextField textIP;

	private DriverS7 driver;
	private boolean checkTrue = false;

	public OneStart(TextField textIP, CheckBox checkMonitoramento) {
		this.textIP = textIP;
		this.checkMonitoramento = checkMonitoramento;
	}

	// && !state.getState()
	@Override
	public void actionPerformed(ActionEvent e) {
		verifyCheck();
		try {
			if (!checkMonitoramento.isSelected()) {
				Thread thread = new Thread(new Runnable() {
					@Override
					public void run() {
						driver = new DriverS7(textIP.getText());
						driver.connect();
						if (driver.statusConnect()) {
							for (int indice = 0; indice < listCheckBoolean.size(); indice++) {
								if (listCheckBoolean.get(indice)) {
									char i = FilterStringDB.getTypeMemory(listFieldDB.get(indice).getText());
									switch (i) {
									case 'X':
										driver.getBit(listFieldValues, listFieldDB, indice);
										break;

									case 'W':
										driver.getWord(listFieldValues, listFieldDB, indice);
										break;

									case 'D':
										driver.getDouble(listFieldValues, listFieldDB, indice);
										break;
									}
								}
							}
							driver.disconnect();
						} else {
							System.out.println("Sem conexão com CLP.");
						}
					}
				});
				thread.start();
			}

		} catch (Exception exception) {
			System.out.println("Erro ao tentar acessar CLP " + exception.getMessage() + " - " + exception.getCause()
					+ " - " + exception.getStackTrace());
		}
	}

	private void verifyCheck() {
		// Verifica quais CheckBox estão selecionados e com endereço validos
		try {
			listCheckBoolean.clear();
			checkTrue = false;
			for (int i = 0; i < listCheckBox.size(); i++) {
				if (listCheckBox.get(i).isSelected() == true
						&& CheckStringDB.verifyText(listFieldDB.get(i).getText()) == true) {
					checkTrue = true; // Verifica se existe checkbox selecionado
					listCheckBoolean.add(true); // Adiciona no array quais estão selecionados
				} else {
					listCheckBoolean.add(false);
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage() + "  " + e.getCause());
		}
	}

	public void fillList(List<CheckBox> listCheckBox, List<TextField> listFieldDB, List<TextField> listFieldValues) {
		this.listCheckBox = listCheckBox;
		this.listFieldDB = listFieldDB;
		this.listFieldValues = listFieldValues;
	}
}
