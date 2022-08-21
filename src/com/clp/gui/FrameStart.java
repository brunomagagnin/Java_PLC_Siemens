package com.clp.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import com.clp.event.ButtonStopListener;
import com.clp.event.CheckBoxEvento;
import com.clp.event.CycloStartState;
import com.clp.event.HoldStart;
import com.clp.event.OneStart;
import com.clp.event.TestConListener;

public class FrameStart extends JFrame {
	private static final long serialVersionUID = 1L;
	// private Border raisedbevel = BorderFactory.createRaisedBevelBorder();
	private PanelConfig panelConfig;
	private PanelResult panelResult;
	private PanelDB panelDB;
	private PanelTest panelTest;
	private CycloStartState cycloStartState = new CycloStartState();

	private List<CheckBox> listCheckBox;
	private List<Button> listButton;
	private List<TextField> listFieldDB;
	private List<TextField> listFieldValues;

	private HoldStart holdStart;
	private OneStart oneStart;

	public FrameStart() {
		super("Tela Principal");
		this.setBounds(100, 100, 594, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);

		add(panelConfig = new PanelConfig());
		add(panelResult = new PanelResult());
		add(panelDB = new PanelDB());
		add(panelTest = new PanelTest());

		// INICIALIZA LISTAS
		listCheckBox = new ArrayList<CheckBox>();
		listCheckBox.add(panelDB.checkBox1);
		listCheckBox.add(panelDB.checkBox2);
		listCheckBox.add(panelDB.checkBox3);

		listButton = new ArrayList<Button>();
		listButton.add(panelConfig.buttonStart);
		listButton.add(panelConfig.buttonStop);
		listButton.add(panelTest.buttonTeste);

		listFieldDB = new ArrayList<TextField>();
		listFieldDB.add(panelDB.textFieldDB1);
		listFieldDB.add(panelDB.textFieldDB2);
		listFieldDB.add(panelDB.textFieldDB3);

		listFieldValues = new ArrayList<TextField>();
		listFieldValues.add(panelResult.textFieldResult1);
		listFieldValues.add(panelResult.textFieldResult2);
		listFieldValues.add(panelResult.textFieldResult3);
		
		//******  INICIALIZA OS LISTENER  ************
		oneStart = new OneStart(panelConfig.textIP, panelConfig.checkBoxHold);
		oneStart.fillLists(listCheckBox, listFieldDB, listFieldValues);

		holdStart = new HoldStart(panelConfig.buttonStop, panelTest.buttonTeste, panelConfig.textIP, panelConfig.checkBoxHold, cycloStartState, this);
		holdStart.fillLists(listCheckBox, listFieldDB, listFieldValues);

		// *******  EVENTO BOT�ES DE START E STOP  ****************
		panelConfig.buttonStop.addActionListener(
				new ButtonStopListener(panelConfig.buttonStop, panelTest.buttonTeste, panelConfig.checkBoxHold, cycloStartState, holdStart));
		panelConfig.buttonStart.addActionListener(holdStart);
		panelConfig.buttonStart.addActionListener(oneStart);

		// ***********  EVENTO BOTÃO TESTE DE CONEXÃO  *********************
		panelTest.buttonTeste.addActionListener(new TestConListener(panelConfig.textIP, panelTest.lblStatus));
		
		//************  EVENTO CHECKBOX   ***************
		panelDB.checkBox1.addItemListener(new CheckBoxEvento(panelDB.checkBox1, panelDB.textFieldDB1));
		panelDB.checkBox2.addItemListener(new CheckBoxEvento(panelDB.checkBox2, panelDB.textFieldDB2));
		panelDB.checkBox3.addItemListener(new CheckBoxEvento(panelDB.checkBox3, panelDB.textFieldDB3));
	}
}
