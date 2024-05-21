package br.com.fiap.bo;

import br.com.fiap.dao.UsuarioSalesforceDAO;
import br.com.fiap.model.UsuarioSalesforce;

import java.sql.SQLException;
import java.util.List;

public class UsuarioSalesforceBO {

    private UsuarioSalesforceDAO usuarioSalesforceDAO;

    public UsuarioSalesforceBO() {
        try {
            this.usuarioSalesforceDAO = new UsuarioSalesforceDAO();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Erro ao inicializar SalesforceBO: " + e.getMessage(), e);
        }
    }

    public void salvar(UsuarioSalesforce usuario) throws ClassNotFoundException, SQLException {
        validarUsuario(usuario);
        if (emailJaExiste(usuario.getEmail())) {
            throw new IllegalArgumentException("E-mail já cadastrado.");
        }
        usuarioSalesforceDAO.salvar(usuario);
    }
    

    public UsuarioSalesforce buscarPorId(Long id) throws ClassNotFoundException, SQLException {
        return usuarioSalesforceDAO.buscarPorId(id);
    }

    public void atualizarUsuario(UsuarioSalesforce usuario) throws ClassNotFoundException, SQLException {
        validarUsuario(usuario);
        UsuarioSalesforce usuarioExistente = usuarioSalesforceDAO.buscarPorId(usuario.getId());
        if (usuarioExistente != null ) {
            throw new IllegalArgumentException("E-mail já cadastrado.");
        }
        usuarioSalesforceDAO.atualizarUsuario(usuario);
    }

    public void excluirUsuario(Long id) throws ClassNotFoundException, SQLException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido.");
        }
        usuarioSalesforceDAO.excluirUsuario(id);
    }

    public List<UsuarioSalesforce> listarUsuarios() throws ClassNotFoundException, SQLException {
        return usuarioSalesforceDAO.listarUsuarios();
    }

    private void validarUsuario(UsuarioSalesforce usuario) {
        if (usuario.getNomeUsuario() == null || usuario.getNomeUsuario().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome de usuário não pode ser nulo ou vazio.");
        }
        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("E-mail não pode ser nulo ou vazio.");
        }
        if (usuario.getTelefoneUsuario() == null || String.valueOf(usuario.getTelefoneUsuario()).length() < 10) {
            throw new IllegalArgumentException("Telefone inválido.");
        }
    }

    public boolean emailJaExiste(String email) throws ClassNotFoundException, SQLException {
        List<UsuarioSalesforce> usuarios = usuarioSalesforceDAO.listarUsuarios();
        for (UsuarioSalesforce usuario : usuarios) {
            if (usuario.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }
}
