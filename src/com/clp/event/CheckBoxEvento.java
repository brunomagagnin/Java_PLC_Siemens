package com.clp.event;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import com.clp.gui.CheckBox;
import com.clp.gui.TextField;

public class CheckBoxEvento implements ItemListener {
	
	private CheckBox checkBox;
	private TextField txtField;
	private boolean isSelected;
	
	
	public CheckBoxEvento(CheckBox checkBox, TextField textField) {
		this.checkBox = checkBox;
		this.txtField = textField;
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		if(checkBox.isSelected()) {
			this.isSelected = true;
			txtField.setEnabled(true);
		} else {
			this.isSelected = false;
			txtField.setEnabled(false);
		}
	}
	
	public boolean isSelected() {
		return this.isSelected;
	}
}
