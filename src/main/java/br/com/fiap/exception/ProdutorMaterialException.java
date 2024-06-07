package br.com.fiap.exception;

import java.io.Serializable;

public class ProdutorMaterialException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 1L;

    public ProdutorMaterialException(String message) {
        super(message);
    }

    public ProdutorMaterialException(String message, Throwable cause) {
        super(message, cause);
    }
}
