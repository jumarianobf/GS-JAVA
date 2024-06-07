package br.com.fiap.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MaterialColetado {
	
	private Long idMaterial;
	private String tipoMaterial;
	private int qtdMaterial;
	
	private ColetadorEJL coletadorEJL;
	
	public MaterialColetado(Long idMaterial, String tipoMaterial, int qtdMaterial, ColetadorEJL coletadorEJL) {
		super();
		this.idMaterial = idMaterial;
		this.tipoMaterial = tipoMaterial;
		this.qtdMaterial = qtdMaterial;
		this.coletadorEJL = coletadorEJL;
	}
	
	public MaterialColetado() {
		
	}

	public Long getIdMaterial() {
		return idMaterial;
	}

	public void setIdMaterial(Long idMaterial) {
		this.idMaterial = idMaterial;
	}

	public String getTipoMaterial() {
		return tipoMaterial;
	}

	public void setTipoMaterial(String tipoMaterial) {
		this.tipoMaterial = tipoMaterial;
	}

	public int getQtdMaterial() {
		return qtdMaterial;
	}

	public void setQtdMaterial(int qtdMaterial) {
		this.qtdMaterial = qtdMaterial;
	}

	public ColetadorEJL getColetadorEJL() {
		return coletadorEJL;
	}

	public void setColetadorEJL(ColetadorEJL coletadorEJL) {
		this.coletadorEJL = coletadorEJL;
	}
	
	public Long getIdColetador() {
        return coletadorEJL.getIdColetador();
    }

	@Override
	public String toString() {
		return "MaterialColetado ["
				+ "idMaterial=" + idMaterial 
				+ ", tipoMaterial=" + tipoMaterial 
				+ ", qtdMaterial=" + qtdMaterial 
				+ ", coletadorEJL=" + coletadorEJL 
				+ "]";
	}
	
	
	
	

}
