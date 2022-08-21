package br.com.clp.event;

public class CycloStartState {

	private Boolean cycloStartState = false;

	public CycloStartState() {

	}
	
	public void setState(Boolean b) {
		this.cycloStartState = b;
	}
	
	public Boolean getState() {
		return this.cycloStartState;
	}

}
