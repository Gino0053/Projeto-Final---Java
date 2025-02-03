package com.gruiz.projeto_java.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.gruiz.projeto_java.exception.ClientNotFoundException;
import com.gruiz.projeto_java.exception.SaldoNegativoException;
import com.gruiz.projeto_java.exception.TelefoneUniqueViolationException;
import com.gruiz.projeto_java.exception.CheckCorrentistaException;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<Object> handleClientNotFoundException(ClientNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(TelefoneUniqueViolationException.class)
    public ResponseEntity<Object> handleTelefoneUniqueViolationException(TelefoneUniqueViolationException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                             .body(new ErrorResponse(ex.getMessage()));
    }
    
    @ExceptionHandler(CheckCorrentistaException.class)
    public ResponseEntity<Object> CheckCorrentistaException(CheckCorrentistaException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(new ErrorResponse(ex.getMessage()));
    }
    
    @ExceptionHandler(SaldoNegativoException.class)
    public ResponseEntity<Object> saldoNegativoException(SaldoNegativoException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(new ErrorResponse(ex.getMessage()));
    }
    
}
