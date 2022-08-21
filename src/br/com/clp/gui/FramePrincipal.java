package br.com.clp.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import br.com.clp.event.ButtonStopListener;
import br.com.clp.event.CheckBoxEvento;
import br.com.clp.event.CycloState;
import br.com.clp.event.HideStart;
import br.com.clp.event.OneStart;
import br.com.clp.event.TestConListener;

public class FramePrincipal extends JFrame {
	private static final long serialVersionUID = 1L;
	// private Border raisedbevel = BorderFactory.createRaisedBevelBorder();
	private PanelConfig pnlConfig;
	private PanelValues pnlValues;
	private PanelDB pnlDB;
	private PanelTest pnlTest;
	private CycloState state = new CycloState();

	private List<CheckBox> listCheckBox;
	private List<Button> listButton;
	private List<TextField> listFieldDB;
	private List<TextField> listFieldValues;

	private HideStart hideStart;
	private OneStart oneStart;

	public FramePrincipal() {
		super("Tela Principal");
		this.setBounds(100, 100, 594, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);

		add(pnlConfig = new PanelConfig());
		add(pnlValues = new PanelValues());
		add(pnlDB = new PanelDB());
		add(pnlTest = new PanelTest());

		// INICIALIZA LISTAS
		listCheckBox = new ArrayList<CheckBox>();
		listCheckBox.add(pnlDB.checkBox1);
		listCheckBox.add(pnlDB.checkBox2);
		listCheckBox.add(pnlDB.checkBox3);

		listButton = new ArrayList<Button>();
		listButton.add(pnlConfig.btnStart);
		listButton.add(pnlConfig.btnStop);
		listButton.add(pnlTest.btnTeste);

		listFieldDB = new ArrayList<TextField>();
		listFieldDB.add(pnlDB.textFieldDB1);
		listFieldDB.add(pnlDB.textFieldDB2);
		listFieldDB.add(pnlDB.textFieldDB3);

		listFieldValues = new ArrayList<TextField>();
		listFieldValues.add(pnlValues.textFieldResultado1);
		listFieldValues.add(pnlValues.textFieldResultado2);
		listFieldValues.add(pnlValues.textFieldResultado3);
		
		//******  INICIALIZA OS LISTENER  ************
		oneStart = new OneStart(pnlConfig.textIP, pnlConfig.checkMonitor);
		oneStart.fillList(listCheckBox, listFieldDB, listFieldValues);

		hideStart = new HideStart(pnlConfig.btnStop, pnlTest.btnTeste, pnlConfig.textIP, pnlConfig.checkMonitor, state);
		hideStart.fillList(listCheckBox, listFieldDB, listFieldValues);

		// *******  EVENTO BOTÕES DE START E STOP  ****************
		pnlConfig.btnStop.addActionListener(
				new ButtonStopListener(pnlConfig.btnStop, pnlTest.btnTeste, pnlConfig.checkMonitor, state, hideStart));
		pnlConfig.btnStart.addActionListener(hideStart);
		pnlConfig.btnStart.addActionListener(oneStart);

		// ***********  EVENTO BOTÃO TESTE DE CONEXÃO  *********************
		// pnlConfig.textIP pnlTest.lblStatus
		pnlTest.btnTeste.addActionListener(new TestConListener(pnlConfig.textIP, pnlTest.lblStatus));
		
		//************  EVENTO CHECKBOX   ***************
		pnlDB.checkBox1.addItemListener(new CheckBoxEvento(pnlDB.checkBox1, pnlDB.textFieldDB1));
		pnlDB.checkBox2.addItemListener(new CheckBoxEvento(pnlDB.checkBox2, pnlDB.textFieldDB2));
		pnlDB.checkBox3.addItemListener(new CheckBoxEvento(pnlDB.checkBox3, pnlDB.textFieldDB3));
	}
}
