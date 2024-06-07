package br.com.fiap.model;

public class ProdutorMaterial {

	private Long idProdutor;
	private String nomeEmpresaProdutor;
	private String tipoProdutor;
	private Long idMaterial;
	
	public ProdutorMaterial(Long idProdutor, String nomeEmpresaProdutor, String tipoProdutor,
			Long idMaterial) {
		super();
		this.idProdutor = idProdutor;
		this.nomeEmpresaProdutor = nomeEmpresaProdutor;
		this.tipoProdutor = tipoProdutor;
		this.idMaterial = idMaterial;
	}
	
	public ProdutorMaterial() {
		
	}
	
	public Long getIdProdutor() {
		return idProdutor;
	}
	public void setIdProdutor(Long idProdutor) {
		this.idProdutor = idProdutor;
	}
	public String getNomeEmpresaProdutor() {
		return nomeEmpresaProdutor;
	}
	public void setNomeEmpresaProdutor(String nomeEmpresaProdutor) {
		this.nomeEmpresaProdutor = nomeEmpresaProdutor;
	}
	public String getTipoProdutor() {
		return tipoProdutor;
	}
	public void setTipoProdutor(String tipoProdutor) {
		this.tipoProdutor = tipoProdutor;
	}

	public Long getIdMaterial() {
		return idMaterial;
	}

	public void setIdMaterial(Long idMaterial) {
		this.idMaterial = idMaterial;
	}

	@Override
	public String toString() {
		return "ProdutorMaterial ["
				+ "idProdutor=" + idProdutor 
				+ ", nomeEmpresaProdutor=" + nomeEmpresaProdutor
				+ ", tipoProdutor=" + tipoProdutor 
				+ ", idMaterial=" + idMaterial
				+ "]";
	}
	
	
	
	
	
	
}
