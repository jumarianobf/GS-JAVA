package br.com.fiap.model;

public class ProdutoProdutor {

	private Long idProdutoProdutor;
	
	private String nomeProduto;
	
	private String descricaoProduto;
	
	private double precoProduto;
	
	private ProdutorMaterial produtorMaterial;
	
	
	public ProdutoProdutor(Long idProdutoProdutor, String nomeProduto, String descricaoProduto, double precoProduto,
			ProdutorMaterial produtorMaterial) {
		super();
		this.idProdutoProdutor = idProdutoProdutor;
		this.nomeProduto = nomeProduto;
		this.descricaoProduto = descricaoProduto;
		this.precoProduto = precoProduto;
		this.produtorMaterial = produtorMaterial;
	}


	public Long getIdProdutoProdutor() {
		return idProdutoProdutor;
	}


	public void setIdProdutoProdutor(Long idProdutoProdutor) {
		this.idProdutoProdutor = idProdutoProdutor;
	}


	public String getNomeProduto() {
		return nomeProduto;
	}


	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}


	public String getDescricaoProduto() {
		return descricaoProduto;
	}


	public void setDescricaoProduto(String descricaoProduto) {
		this.descricaoProduto = descricaoProduto;
	}


	public double getPrecoProduto() {
		return precoProduto;
	}


	public void setPrecoProduto(double precoProduto) {
		this.precoProduto = precoProduto;
	}


	public ProdutorMaterial getProdutorMaterial() {
		return produtorMaterial;
	}


	public void setProdutorMaterial(ProdutorMaterial produtorMaterial) {
		this.produtorMaterial = produtorMaterial;
	}
	
	public Long getIdProdutor() {
		return produtorMaterial.getIdProdutor();
	}


	@Override
	public String toString() {
		return "ProdutoProdutor ["
				+ "idProdutoProdutor=" + idProdutoProdutor 
				+ ", nomeProduto=" + nomeProduto
				+ ", descricaoProduto=" + descricaoProduto 
				+ ", precoProduto=" + precoProduto 
				+ ", produtorMaterial=" + produtorMaterial 
				+ "]";
	}
}
