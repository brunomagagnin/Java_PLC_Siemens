package com.clp.exceptions;

public class VerifyException extends RuntimeException{
	
	public VerifyException() {
		super("Verifique os campos preenchidos.");
	}
}
