package br.com.fiap.vo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Curso {
	
	private Long id;
	private String nomeCurso;
	private String areaCurso;
	private String certificadoCurso;
	
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
}
