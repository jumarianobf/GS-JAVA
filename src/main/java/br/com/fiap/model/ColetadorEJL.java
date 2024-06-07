package br.com.fiap.model;

public class ColetadorEJL {
	
    private Long idColetador;
    private String primeiroNomeColetador;
    private String sobrenomeColetador;
    private Long qtdColetado;

    public ColetadorEJL(Long idColetador, String primeiroNomeColetador, String sobrenomeColetador, Long qtdColetado) {
        this.idColetador = idColetador;
        this.primeiroNomeColetador = primeiroNomeColetador;
        this.sobrenomeColetador = sobrenomeColetador;
        this.qtdColetado = qtdColetado;
    }

    public ColetadorEJL() {
		// TODO Auto-generated constructor stub
	}


	// Getters e setters
    public Long getIdColetador() {
        return idColetador;
    }

    public void setIdColetador(Long idColetador) {
        this.idColetador = idColetador;
    }

    public String getPrimeiroNomeColetador() {
        return primeiroNomeColetador;
    }

    public void setPrimeiroNomeColetador(String primeiroNomeColetador) {
        this.primeiroNomeColetador = primeiroNomeColetador;
    }

    public String getSobrenomeColetador() {
        return sobrenomeColetador;
    }

    public void setSobrenomeColetador(String sobrenomeColetador) {
        this.sobrenomeColetador = sobrenomeColetador;
    }


	public Long getQtdColetado() {
		return qtdColetado;
	}

	public void setQtdColetado(Long qtdColetado) {
		this.qtdColetado = qtdColetado;
	}

	@Override
	public String toString() {
		return "ColetadorEJL ["
				+ "idColetador=" + idColetador 
				+ ", primeiroNomeColetador=" + primeiroNomeColetador
				+ ", sobrenomeColetador=" + sobrenomeColetador 
				+ ", qtdColetado=" + qtdColetado 
				+ "]";
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ColetadorEJL that = (ColetadorEJL) o;

        return idColetador != null ? idColetador.equals(that.idColetador) : that.idColetador == null;
    }

    @Override
    public int hashCode() {
        return idColetador != null ? idColetador.hashCode() : 0;
    }

}
