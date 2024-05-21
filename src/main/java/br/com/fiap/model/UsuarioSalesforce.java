package br.com.fiap.model;


public class UsuarioSalesforce {
    private Long id;
    private String nomeUsuario;
    private Long telefoneUsuario;
    private String email;
    private String regiao;
    private String empresaUsuario;

    public UsuarioSalesforce() {
    }

    public UsuarioSalesforce(final Long id, final String nomeUsuario, final Long telefoneUsuario, final String email, final String regiao, final String empresaUsuario) {
        this.id = id;
        this.nomeUsuario = nomeUsuario;
        this.telefoneUsuario = telefoneUsuario;
        this.email = email;
        this.regiao = regiao;
        this.empresaUsuario = empresaUsuario;
    }

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
        if (nomeUsuario == null || nomeUsuario.isEmpty()) {
            throw new IllegalArgumentException("Nome de usuário não pode ser nulo ou vazio");
        }
        this.nomeUsuario = nomeUsuario;
    }

    public Long getTelefoneUsuario() {
        return telefoneUsuario;
    }

    public void setTelefoneUsuario(Long telefoneUsuario) {
        if (telefoneUsuario == null || telefoneUsuario <= 0) {
            throw new IllegalArgumentException("Telefone de usuário inválido");
        }
        this.telefoneUsuario = telefoneUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Email inválido");
        }
        this.email = email;
    }

    public String getRegiao() {
        return regiao;
    }

    public void setRegiao(String regiao) {
        this.regiao = regiao;
    }

    public String getEmpresaUsuario() {
        return empresaUsuario;
    }

    public void setEmpresaUsuario(String empresaUsuario) {
        this.empresaUsuario = empresaUsuario;
    }

    @Override
    public String toString() {
        return "UsuarioSalesforce{" +
                "id=" + id +
                ", nomeUsuario='" + nomeUsuario + '\'' +
                ", telefoneUsuario=" + telefoneUsuario +
                ", email='" + email + '\'' +
                ", regiao='" + regiao + '\'' +
                ", empresaUsuario='" + empresaUsuario + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UsuarioSalesforce that = (UsuarioSalesforce) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
