package br.com.fiap.vo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Funcionario {
	private Long id;
    private String descricaoCargo;
    private String descricaoFormacao;
    private double valorSalarioFuncionario;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricaoCargo() {
        return descricaoCargo;
    }

    public void setDescricaoCargo(String descricaoCargo) {
        this.descricaoCargo = descricaoCargo;
    }

    public String getDescricaoFormacao() {
        return descricaoFormacao;
    }

    public void setFormacao(String descricaoFormacao) {
        this.descricaoFormacao = descricaoFormacao;
    }

    public double getValorSalarioFuncionario() {
        return valorSalarioFuncionario;
    }

    public void setValorSalarioFuncionario(double valorSalarioFuncionario) {
        this.valorSalarioFuncionario = valorSalarioFuncionario;
    }
}