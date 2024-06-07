package br.com.fiap.exception;

public class MaterialColetadoException extends ColetadorException {

    public MaterialColetadoException(String message) {
        super(message);
    }

    public MaterialColetadoException(String message, Throwable cause) {
        super(message, cause);
    }
    
    @Override
    public String toString() {
        return "MaterialColetadoException: " + getMessage();
    }
}


