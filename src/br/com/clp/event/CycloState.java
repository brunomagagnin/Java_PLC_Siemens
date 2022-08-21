package br.com.clp.event;

public class CycloState {

	private Boolean cycloState = false;

	public CycloState() {

	}
	
	public void setState(Boolean b) {
		this.cycloState = b;
	}
	
	public Boolean getState() {
		return this.cycloState;
	}

}
