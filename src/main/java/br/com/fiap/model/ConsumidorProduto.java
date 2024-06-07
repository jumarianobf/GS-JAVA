package br.com.fiap.model;

public class ConsumidorProduto {
	
	private Long idConsumidorProduto;
	private String primeiroNomeConsumidor;
	private String sobrenomeConsumidor;
	private String cpfConsumidor;
	
	public ConsumidorProduto(Long idConsumidorProduto, String primeiroNomeConsumidor, String sobrenomeConsumidor,
			String cpfConsumidor) {
		super();
		this.idConsumidorProduto = idConsumidorProduto;
		this.primeiroNomeConsumidor = primeiroNomeConsumidor;
		this.sobrenomeConsumidor = sobrenomeConsumidor;
		this.cpfConsumidor = cpfConsumidor;
	}
	
	public ConsumidorProduto() {
		
	}
	
	public Long getIdConsumidorProduto() {
		return idConsumidorProduto;
	}
	
	public void setIdConsumidorProduto(Long idConsumidorProduto) {
		this.idConsumidorProduto = idConsumidorProduto;
	}
	
	public String getPrimeiroNomeConsumidor() {
		return primeiroNomeConsumidor;
	}
	
	public void setPrimeiroNomeConsumidor(String primeiroNomeConsumidor) {
		this.primeiroNomeConsumidor = primeiroNomeConsumidor;
	}
	
	public String getSobrenomeConsumidor() {
		return sobrenomeConsumidor;
	}
	
	public void setSobrenomeConsumidor(String sobrenomeConsumidor) {
		this.sobrenomeConsumidor = sobrenomeConsumidor;
	}
	
	public String getCpfConsumidor() {
		return cpfConsumidor;
	}
	
	public void setCpfConsumidor(String cpfConsumidor) {
		this.cpfConsumidor = cpfConsumidor;
	}
	
	@Override
	public String toString() {
		return "ConsumidorProduto ["
				+ "idConsumidorProduto=" + idConsumidorProduto 
				+ ", primeiroNomeConsumidor=" + primeiroNomeConsumidor 
				+ ", sobrenomeConsumidor=" + sobrenomeConsumidor 
				+ ", cpfConsumidor=" + cpfConsumidor 
				+ "]";
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConsumidorProduto that = (ConsumidorProduto) o;

        return idConsumidorProduto != null ? idConsumidorProduto.equals(that.idConsumidorProduto) : that.idConsumidorProduto == null;
    }

    @Override
    public int hashCode() {
        return idConsumidorProduto != null ? idConsumidorProduto.hashCode() : 0;
    }
	

}
