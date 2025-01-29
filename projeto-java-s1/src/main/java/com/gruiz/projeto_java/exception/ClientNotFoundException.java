package com.gruiz.projeto_java.exception;

public class ClientNotFoundException extends RuntimeException {

    public ClientNotFoundException(String message) {
        super(message);
    }

    public ClientNotFoundException(Long id) {
        super("Cliente com ID " + id + " não encontrado.");
    }
}