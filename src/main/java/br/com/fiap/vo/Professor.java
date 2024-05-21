package br.com.fiap.vo;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement
public class Professor {
	 	private Long id;
	 	
	 	private String especializacaoProfessor;
	 	
	    private int qtdAulas;
	    
	    private int qtdMaterias;
	    
	    private String descricaoMaterias;
	    
	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public String getEspecializacaoProfessor() {
	        return especializacaoProfessor;
	    }

	    public void setEspecializacaoProfessor(String especializacaoProfessor) {
	        this.especializacaoProfessor = especializacaoProfessor;
	    }

	    public int getQtdAulas() {
	        return qtdAulas;
	    }

	    public void setQtdAulas(int qtdAulas) {
	        this.qtdAulas = qtdAulas;
	    }

	    public int getQtdMaterias() {
	        return qtdMaterias;
	    }

	    public void setQtdMaterias(int qtdMaterias) {
	        this.qtdMaterias = qtdMaterias;
	    }

	    public String getDescricaoMaterias() {
	        return descricaoMaterias;
	    }

	    public void setDescricaoMaterias(String descricaoMaterias) {
	        this.descricaoMaterias = descricaoMaterias;
	    }
}