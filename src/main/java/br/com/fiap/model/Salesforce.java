package br.com.fiap.model;

public class Salesforce {
    private Long id;
    private String regiao;
    private Long cep;
    private String rua;
    private String bairro;
    private String complemento;
    private Long telefone;


    public Salesforce(){

    }

    public Salesforce(Long id, String regiao, Long cep, String rua, String bairro, String complemento, Long telefone) {
        this.id = id;
        this.regiao = regiao;
        this.cep = cep;
        this.rua = rua;
        this.bairro = bairro;
        this.complemento = complemento;
        this.telefone = telefone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegiao() {
        return regiao;
    }

    public void setRegiao(String regiao) {
        this.regiao = regiao;
    }

    public Long getCep() {
        return cep;
    }

    public void setCep(Long cep) {
        this.cep = cep;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public Long getTelefone() {
        return telefone;
    }

    public void setTelefone(Long telefone) {
        this.telefone = telefone;
    }

    @Override
    public String toString() {
        return "Salesforce{" +
                "id=" + id +
                ", regiao='" + regiao + '\'' +
                ", cep=" + cep +
                ", rua='" + rua + '\'' +
                ", bairro='" + bairro + '\'' +
                ", complemeto='" + complemento + '\'' +
                ", telefone='" + telefone + '\'' +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Salesforce that = (Salesforce) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }


}

