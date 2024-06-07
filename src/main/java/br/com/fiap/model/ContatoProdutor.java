package br.com.fiap.model;

public class ContatoProdutor {
	
	private Long idContatoProdutor;
	private String emailProdutor;
	private String telProdutor;
	private Long idProdutor;

	public ContatoProdutor(Long idContatoProdutor, String emailProdutor, String telProdutor,
			Long idProdutor) {
		super();
		this.idContatoProdutor = idContatoProdutor;
		this.emailProdutor = emailProdutor;
		this.telProdutor = telProdutor;
		this.idProdutor = idProdutor;
	}
	
	public ContatoProdutor() {
		
	}

	public Long getIdContatoProdutor() {
		return idContatoProdutor;
	}

	public void setIdContatoProdutor(Long idContatoProdutor) {
		this.idContatoProdutor = idContatoProdutor;
	}

	public String getEmailProdutor() {
		return emailProdutor;
	}

	public void setEmailProdutor(String emailProdutor) {
		this.emailProdutor = emailProdutor;
	}

	public String getTelProdutor() {
		return telProdutor;
	}

	public void setTelProdutor(String telProdutor) {
		this.telProdutor = telProdutor;
	}

	public Long getIdProdutor() {
		return idProdutor;
	}

	public void setIdProdutor(Long idProdutor) {
		this.idProdutor = idProdutor;
	}


	@Override
	public String toString() {
		return "ContatoProdutor ["
				+ "idContatoProdutor=" + idContatoProdutor 
				+ ", emailProdutor=" + emailProdutor 
				+ ", telProdutor=" + telProdutor 
				+ ", idProdutor=" + idProdutor 
				+ "]";
	}
	
	
	
	

}
