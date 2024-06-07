package br.com.fiap.exception;

import java.io.Serializable;

public class ProdutoException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 1L;

    public ProdutoException(String message) {
        super(message);
    }

    public ProdutoException(String message, Throwable cause) {
        super(message, cause);
    }
    
    @Override
    public String toString() {
        return "ProdutoProdutorException: " + getMessage();
    }
}
