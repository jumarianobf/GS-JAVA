package br.com.fiap.model;


public class CursoSalesforce {
	private Long id;
	private String nomeCurso;
	private String areaCurso;
	private String certificadoCurso;

	public CursoSalesforce() {
	}
	public CursoSalesforce(String nomeCurso, String areaCurso, String certificadoCurso) {
		this.nomeCurso = nomeCurso;
		this.areaCurso = areaCurso;
		this.certificadoCurso = certificadoCurso;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeCurso() {
		return nomeCurso;
	}

	public void setNomeCurso(String nomeCurso) {
		this.nomeCurso = nomeCurso;
	}

	public String getAreaCurso() {
		return areaCurso;
	}

	public void setAreaCurso(String areaCurso) {
		this.areaCurso = areaCurso;
	}

	public String getCertificadoCurso() {
		return certificadoCurso;
	}

	public void setCertificadoCurso(String certificadoCurso) {
		this.certificadoCurso = certificadoCurso;
	}

	@Override
	public String toString() {
		return "CursoSalesforce " +
				"[id=" + id
				+ ", nomeCurso="
				+ nomeCurso + ", areaCurso="
				+ areaCurso + ", certificadoCurso="
				+ certificadoCurso + "]";
	}

}

	

	
	
	
	

