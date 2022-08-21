package br.com.clp.event;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import br.com.clp.gui.CheckBox;
import br.com.clp.gui.TextField;

public class CheckBoxEvento implements ItemListener {
	
	private CheckBox checkBox;
	private TextField txtField;
	private boolean boxSelecionado;
	
	
	public CheckBoxEvento(CheckBox checkBox, TextField textField) {
		this.checkBox = checkBox;
		this.txtField = textField;
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		if(checkBox.isSelected()) {
			this.boxSelecionado = true;
			txtField.setEnabled(true);
		} else {
			this.boxSelecionado = false;
			txtField.setEnabled(false);
		}
	}
	
	public boolean boxSelecionado() {
		return this.boxSelecionado;
	}
}
