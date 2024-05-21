package br.com.fiap.vo;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement
public class Usuario {

	private Long id;

    @JsonProperty("nome_usuario")
    private String nomeUsuario;

    @JsonProperty("telefone_usuario")
    private Long telefoneUsuario;

    private String email;
    private String regiao;

    @JsonProperty("empresa_usuario")
    private String empresaUsuario;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public Long getTelefoneUsuario() {
        return telefoneUsuario;
    }

    public void setTelefoneUsuario(Long telefoneUsuario) {
        this.telefoneUsuario = telefoneUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegiao() {
        return regiao;
    }

    public void setRegiao(String regiao) {
        this.regiao = regiao;
    }

    public String getEmpresaUsuario() {
        return this.empresaUsuario;
    }

    public void setEmpresaUsuario(String empresaUsuario) {
        this.empresaUsuario = empresaUsuario;
    }
}
