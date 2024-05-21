package br.com.fiap.model;

public class FuncionarioSalesforce {
    private Long id;
    private String descricaoCargo;
    private String descricaoFormacao;
    private double valorSalarioFuncionario;

    public FuncionarioSalesforce(){

    }

    public FuncionarioSalesforce(String descricaoCargo, String descricaoFormacao, double valorSalarioFuncionario){
        this.descricaoCargo = descricaoCargo;
        this.descricaoFormacao = descricaoFormacao;
        this.valorSalarioFuncionario = valorSalarioFuncionario;
    }

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

    @Override
    public String toString() {
        return "FuncionarioSalesforce{" +
                "id=" + id +
                ", descriçãoCargo='" + descricaoCargo + '\'' +
                ", descricaoFormacao=" + descricaoFormacao +
                ", valorSalarioFuncionario='" + valorSalarioFuncionario + '\'' +
                '}';
    }


}
