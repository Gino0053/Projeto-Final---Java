package com.gruiz.projeto_java.exception;

public class TelefoneUniqueViolationException extends RuntimeException{

	public TelefoneUniqueViolationException(String message) {
        super(message);
    }
	
}