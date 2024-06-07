package br.com.fiap.exception;

public class ViaCEPException extends Exception {
	
	public ViaCEPException(String mensagem) {
		super(mensagem);
	}
	
	
	public ViaCEPException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
	

}
