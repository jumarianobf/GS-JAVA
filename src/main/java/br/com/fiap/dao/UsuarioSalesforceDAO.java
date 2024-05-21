package br.com.fiap.dao;

import br.com.fiap.connection.ConnectionFactory;
import br.com.fiap.model.UsuarioSalesforce;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioSalesforceDAO {

    private Connection conexao;

    public UsuarioSalesforceDAO() throws ClassNotFoundException, SQLException {
        this.conexao = new ConnectionFactory().conexao();
    }

    public String salvar(UsuarioSalesforce usuarioSalesforce) {
        String sql = "INSERT INTO T_USUARIO_SALESFORCE (nome_usuario, telefone_usuario, email, regiao_usuario, empresa_usuario) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, usuarioSalesforce.getNomeUsuario());
            statement.setLong(2, usuarioSalesforce.getTelefoneUsuario());
            statement.setString(3, usuarioSalesforce.getEmail());
            statement.setString(4, usuarioSalesforce.getRegiao());
            statement.setString(5, usuarioSalesforce.getEmpresaUsuario());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                return "Falha ao cadastrar usuário, nenhuma linha afetada.";
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    usuarioSalesforce.setId(generatedKeys.getLong(1));
                    return "Usuário cadastrado com sucesso!";
                } else {
                    return "Falha ao cadastrar usuário, nenhum ID obtido.";
                }
            }
        } catch (SQLException e) {
            return "Erro ao salvar usuário: " + e.getMessage();
        } finally {
            fecharConexao();
        }
    }

    public UsuarioSalesforce buscarPorId(Long id) {
        if (id == null) {
            return null;
        }
        String sql = "SELECT * FROM T_USUARIO_SALESFORCE WHERE id_usuario = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new UsuarioSalesforce(
                            rs.getLong("id_usuario"),
                            rs.getString("nome_usuario"),
                            rs.getLong("telefone_usuario"),
                            rs.getString("email"),
                            rs.getString("regiao_usuario"),
                            rs.getString("empresa_usuario")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário por ID: " + e.getMessage(), e);
        }
        return null;
    }

    public String atualizarUsuario(UsuarioSalesforce usuario) {
        String sql = "UPDATE T_USUARIO_SALESFORCE SET nome_usuario = ?, telefone_usuario = ?, email = ?, regiao_usuario = ?, empresa_usuario = ? WHERE id_usuario = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNomeUsuario());
            stmt.setLong(2, usuario.getTelefoneUsuario());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getRegiao());
            stmt.setString(5, usuario.getEmpresaUsuario());
            stmt.setLong(6, usuario.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar usuário: " + e.getMessage(), e);
        }
        return "Atualizado com sucesso";
    }

    public String excluirUsuario(Long id) {
        String sql = "DELETE FROM T_USUARIO_SALESFORCE WHERE id_usuario = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir usuário: " + e.getMessage(), e);
        }
        return "Deletado com sucesso";
    }

    public List<UsuarioSalesforce> listarUsuarios() {
        List<UsuarioSalesforce> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM T_USUARIO_SALESFORCE";
        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                UsuarioSalesforce usuario = new UsuarioSalesforce(
                        rs.getLong("id_usuario"),
                        rs.getString("nome_usuario"),
                        rs.getLong("telefone_usuario"),
                        rs.getString("email"),
                        rs.getString("regiao_usuario"),
                        rs.getString("empresa_usuario")
                );
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar usuários: " + e.getMessage(), e);
        }
        return usuarios;
    }
    
    private void fecharConexao() {
        try {
            if (conexao != null) {
                conexao.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

