package br.com.fiap.exception;

import java.io.Serializable;

public class ProdutorException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 1L;

    public ProdutorException(String message) {
        super(message);
    }

    public ProdutorException(String message, Throwable cause) {
        super(message, cause);
    }
    
    
}