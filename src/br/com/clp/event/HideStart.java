package br.com.clp.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import br.com.clp.comunication.DriverS7;
import br.com.clp.gui.Button;
import br.com.clp.gui.CheckBox;
import br.com.clp.gui.TextField;
import br.com.clp.utilities.CheckStringDB;
import br.com.clp.utilities.FilterStringDB;

public class HideStart implements ActionListener {
	// INICIALIZA LISTAS
	private List<CheckBox> listCheckBox = new ArrayList<CheckBox>();
	private List<Boolean> listCheckBoolean = new ArrayList<Boolean>();
	private List<TextField> listFieldDB = new ArrayList<TextField>();
	private List<TextField> listFieldValues = new ArrayList<TextField>();

	private CheckBox checkMonitoramento;
	private Button btnStop;
	private Button btnTest;
	private TextField textIP;

	private DriverS7 driver;
	private Timer timer;
	private CycloState cycloState;

	private boolean checkTrue = false;

	public HideStart(Button stop, Button test, TextField textIP, CheckBox checkMonitoramento, CycloState cycloState) {
		this.btnStop = stop;
		this.btnTest = test;
		this.textIP = textIP;
		this.checkMonitoramento = checkMonitoramento;
		this.cycloState = cycloState;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			// Bloco de monitoramento contínuo - Quando CheckBox "Monitoramento Contínuo"
			// selecionado
			verifyCheck();

			if (checkMonitoramento.isSelected() && !cycloState.getState() && checkTrue) {
				timer = new Timer();
				driver = new DriverS7(textIP.getText());
				checkMonitoramento.setEnabled(false);
				btnStop.setEnabled(true);
				cycloState.setState(true);
				// cycloStart = true;
				btnTest.setEnabled(false);

				timer.scheduleAtFixedRate(new TimerTask() {
					@Override
					public void run() {
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
				}, 50, 500);
			}

		} catch (Exception i) {
			i.printStackTrace();
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
	
	public Timer getTimer() {
		return this.timer;
	}
}
