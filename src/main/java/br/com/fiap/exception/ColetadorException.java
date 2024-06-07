package br.com.fiap.exception;

import java.io.Serializable;

public class ColetadorException extends RuntimeException implements Serializable  {

	private static final long serialVersionUID = 1L;

	public ColetadorException(String message) {
	        super(message);
	}

	public ColetadorException(String message, Throwable cause) {
		super(message, cause);
		}
	
	@Override
    public String toString() {
        return "ColetadorException: " + getMessage();
    }
}
