package br.com.fiap.exception;

import java.io.Serializable;

public class ContatoException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 1L;

    public ContatoException(String message) {
        super(message);
    }

    public ContatoException(String message, Throwable cause) {
        super(message, cause);
    }
    
    
}