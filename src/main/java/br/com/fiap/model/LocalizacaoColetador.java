package br.com.fiap.model;

public class LocalizacaoColetador {
	private Long idLocalizacaoColetador;
	private static String cepColetador;
	private String ruaColetador;
	private String praiaColetador;
	private String coordenadas;
	private ColetadorEJL coletadorEJL;

	public LocalizacaoColetador(Long idLocalizacaoColetador, String cepColetador, String ruaColetador,
			String praiaColetador, String coordenadas, ColetadorEJL coletadorEJL) {
		super();
		this.idLocalizacaoColetador = idLocalizacaoColetador;
		this.cepColetador = cepColetador;
		this.ruaColetador = ruaColetador;
		this.praiaColetador = praiaColetador;
		this.coordenadas = coordenadas;
		this.coletadorEJL = coletadorEJL;
	}

	public Long getIdLocalizacaoColetador() {
		return idLocalizacaoColetador;
	}
	
	public void setIdLocalizacaoColetador(Long idLocalizacaoColetador) {
		this.idLocalizacaoColetador = idLocalizacaoColetador;
	}
	
	public static String getCepColetador() {
		return cepColetador;
	}
	
	public void setCepColetador(String cepColetador) {
		this.cepColetador = cepColetador;
	}
	
	public String getRuaColetador() {
		return ruaColetador;
	}
	
	public void setRuaColetador(String ruaColetador) {
		this.ruaColetador = ruaColetador;
	}
	
	public String getPraiaColetador() {
		return praiaColetador;
	}
	
	public void setPraiaColetador(String praiaColetador) {
		this.praiaColetador = praiaColetador;
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
	
	public String getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(String coordenadas) {
        this.coordenadas = coordenadas;
    }
	
	@Override
	public String toString() {
		return "LocalizacaoColetador ["
				+ "idLocalizacaoColetador=" + idLocalizacaoColetador 
				+ ", cepColetador=" + cepColetador 
				+ ", ruaColetador=" + ruaColetador 
				+ ", praiaColetador=" + praiaColetador
				+ ", coletadorEJL=" + coletadorEJL 
				+ "]";
	}
	
	
	


	
	
	
	
	

}
