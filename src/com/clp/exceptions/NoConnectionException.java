package com.clp.exceptions;

public class NoConnectionException extends RuntimeException {
	
	public NoConnectionException() {
		super("Sem conexão com CLP.");			
	}

}
