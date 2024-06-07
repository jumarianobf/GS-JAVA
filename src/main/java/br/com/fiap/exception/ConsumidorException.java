package br.com.fiap.exception;

import java.io.Serializable;

public class ConsumidorException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 1L;

    public ConsumidorException(String message) {
        super(message);
    }

    public ConsumidorException(String message, Throwable cause) {
        super(message, cause);
    }
    
    
}