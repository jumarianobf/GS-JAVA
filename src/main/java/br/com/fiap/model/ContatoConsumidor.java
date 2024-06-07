package br.com.fiap.model;

public class ContatoConsumidor {
	
	private Long idContatoConsumidor;
	private String emailConsumidor;
	private String telConsumidor;
	private Long idConsumidorProduto;
	
	public ContatoConsumidor(Long idContatoConsumidor, String emailConsumidor, String telConsumidor,
			Long idConsumidorProduto) {
		super();
		this.idContatoConsumidor = idContatoConsumidor;
		this.emailConsumidor = emailConsumidor;
		this.telConsumidor = telConsumidor;
		this.idConsumidorProduto = idConsumidorProduto;
	}
	
	public ContatoConsumidor() {
		
	}
	
	public Long getIdContatoConsumidor() {
		return idContatoConsumidor;
	}
	public void setIdContatoConsumidor(Long idContatoConsumidor) {
		this.idContatoConsumidor = idContatoConsumidor;
	}
	public String getEmailConsumidor() {
		return emailConsumidor;
	}
	public void setEmailConsumidor(String emailConsumidor) {
		this.emailConsumidor = emailConsumidor;
	}
	public String getTelConsumidor() {
		return telConsumidor;
	}
	public void setTelConsumidor(String telConsumidor) {
		this.telConsumidor = telConsumidor;
	}
	

	public Long getIdConsumidorProduto() {
		return idConsumidorProduto;
	}

	public void setIdConsumidorProduto(Long idConsumidorProduto) {
		this.idConsumidorProduto = idConsumidorProduto;
	}


	
	@Override
	public String toString() {
		return "ContatoConsumidor [idContatoConsumidor=" + idContatoConsumidor + ", emailConsumidor=" + emailConsumidor
				+ ", telConsumidor=" + telConsumidor + ", idConsumidorProduto=" + idConsumidorProduto + "]";
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContatoConsumidor that = (ContatoConsumidor) o;

        return idContatoConsumidor != null ? idContatoConsumidor.equals(that.idContatoConsumidor) : that.idContatoConsumidor == null;
    }

    @Override
    public int hashCode() {
        return idContatoConsumidor != null ? idContatoConsumidor.hashCode() : 0;
    }
	
	

}
