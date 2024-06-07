package br.com.fiap.model;

public class LocalizacaoConsumidor {
	
	private Long idLocalizacaoConsumidor;
	private String logradouroConsumidor;
	private String bairroResidenciaConsumidor;
	private int numeroResidenciaConsumidor;
	private String complementoResidenciaConsumidor;
	private String cepConsumidor;
	private ConsumidorProduto consumidorProduto;
	private ProdutoProdutor produtoProdutor;
	
	public LocalizacaoConsumidor(Long idLocalizacaoConsumidor, String logradouroConsumidor,
			String bairroResidenciaConsumidor, int numeroResidenciaConsumidor, String complementoResidenciaConsumidor,
			String cepConsumidor, ConsumidorProduto consumidorProduto, ProdutoProdutor produtoProdutor) {
		super();
		this.idLocalizacaoConsumidor = idLocalizacaoConsumidor;
		this.logradouroConsumidor = logradouroConsumidor;
		this.bairroResidenciaConsumidor = bairroResidenciaConsumidor;
		this.numeroResidenciaConsumidor = numeroResidenciaConsumidor;
		this.complementoResidenciaConsumidor = complementoResidenciaConsumidor;
		this.cepConsumidor = cepConsumidor;
		this.consumidorProduto = consumidorProduto;
		this.produtoProdutor = produtoProdutor;
	}
	
	public LocalizacaoConsumidor() {
		
	}

	public Long getIdLocalizacaoConsumidor() {
		return idLocalizacaoConsumidor;
	}

	public void setIdLocalizacaoConsumidor(Long idLocalizacaoConsumidor) {
		this.idLocalizacaoConsumidor = idLocalizacaoConsumidor;
	}

	public String getLogradouroConsumidor() {
		return logradouroConsumidor;
	}

	public void setLogradouroConsumidor(String logradouroConsumidor) {
		this.logradouroConsumidor = logradouroConsumidor;
	}

	public String getBairroResidenciaConsumidor() {
		return bairroResidenciaConsumidor;
	}

	public void setBairroResidenciaConsumidor(String bairroResidenciaConsumidor) {
		this.bairroResidenciaConsumidor = bairroResidenciaConsumidor;
	}

	public int getNumeroResidenciaConsumidor() {
		return numeroResidenciaConsumidor;
	}

	public void setNumeroResidenciaConsumidor(int numeroResidenciaConsumidor) {
		this.numeroResidenciaConsumidor = numeroResidenciaConsumidor;
	}

	public String getComplementoResidenciaConsumidor() {
		return complementoResidenciaConsumidor;
	}

	public void setComplementoResidenciaConsumidor(String complementoResidenciaConsumidor) {
		this.complementoResidenciaConsumidor = complementoResidenciaConsumidor;
	}

	public String getCepConsumidor() {
		return cepConsumidor;
	}

	public void setCepConsumidor(String cepConsumidor) {
		this.cepConsumidor = cepConsumidor;
	}

	public ConsumidorProduto getConsumidorProduto() {
		return consumidorProduto;
	}

	public void setConsumidorProduto(ConsumidorProduto consumidorProduto) {
		this.consumidorProduto = consumidorProduto;
	}

	public ProdutoProdutor getProdutoProdutor() {
		return produtoProdutor;
	}

	public void setProdutoProdutor(ProdutoProdutor produtoProdutor) {
		this.produtoProdutor = produtoProdutor;
	}
	
	public Long getIdConsumidorProduto() {
		return consumidorProduto.getIdConsumidorProduto();
	}
	
	public Long getIdProdutoProdutor() {
		return produtoProdutor.getIdProdutoProdutor();
	}

	@Override
	public String toString() {
		return "LocalizacaoConsumidor ["
				+ "idLocalizacaoConsumidor=" + idLocalizacaoConsumidor 
				+ ", logradouroConsumidor=" + logradouroConsumidor 
				+ ", bairroResidenciaConsumidor=" + bairroResidenciaConsumidor
				+ ", numeroResidenciaConsumidor=" + numeroResidenciaConsumidor 
				+ ", complementoResidenciaConsumidor=" + complementoResidenciaConsumidor 
				+ ", cepConsumidor=" + cepConsumidor 
				+ ", consumidorProduto=" + consumidorProduto 
				+ ", produtoProdutor=" + produtoProdutor 
				+ "]";
	}
	
	
	
	

}
