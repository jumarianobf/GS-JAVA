package br.com.fiap.model;

public class ContatoColetador {
    
    private Long idContatoColetador;
    private String emailColetador;
    private String telColetador;
    private Long idColetador; 

    public ContatoColetador(Long idContatoColetador, String emailColetador, String telColetador, Long idColetador) {
        this.idContatoColetador = idContatoColetador;
        this.emailColetador = emailColetador;
        this.telColetador = telColetador;
        this.idColetador = idColetador; 
    }

    public ContatoColetador() {
        // Construtor padrão vazio
    }
    

	public Long getIdColetador() {
		return idColetador;
	}

	public void setIdColetador(Long idColetador) {
		this.idColetador = idColetador;
	}

	// Getters e setters
    public Long getIdContatoColetador() {
        return idContatoColetador;
    }

    public void setIdContatoColetador(Long idContatoColetador) {
        this.idContatoColetador = idContatoColetador;
    }

    public String getEmailColetador() {
        return emailColetador;
    }

    public void setEmailColetador(String emailColetador) {
        this.emailColetador = emailColetador;
    }

    public String getTelColetador() {
        return telColetador;
    }

    public void setTelColetador(String telColetador) {
        this.telColetador = telColetador;
    }
    




	@Override
	public String toString() {
		return "ContatoColetador [idContatoColetador=" + idContatoColetador + ", emailColetador=" + emailColetador
				+ ", telColetador=" + telColetador + ", coletadorEJL=" + "]";
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContatoColetador that = (ContatoColetador) o;

        return idContatoColetador != null ? idContatoColetador.equals(that.idContatoColetador) : that.idContatoColetador == null;
    }

    @Override
    public int hashCode() {
        return idContatoColetador != null ? idContatoColetador.hashCode() : 0;
    }

}

