package br.com.fiap.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.fiap.connection.ConnectionFactory;
import br.com.fiap.exception.DatabaseConnectionException;
import br.com.fiap.exception.ContatoException;
import br.com.fiap.model.ContatoProdutor;

public class ContatoProdutorDAO {

    private Connection conexao;

    public ContatoProdutorDAO() {
        try {
            this.conexao = new ConnectionFactory().conexao();
        } catch (ClassNotFoundException | SQLException e) {
            throw new DatabaseConnectionException("Erro ao conectar com o banco de dados", e);
        }
    }

    public String salvar(ContatoProdutor contatoProdutor) throws SQLException {
        if (contatoProdutor == null) {
            throw new IllegalArgumentException("ContatoProdutor não pode ser nulo");
        }

        String sql = "INSERT INTO T_CONTATO_PRODUTOR (email_produtor, tel_produtor, id_produtor) VALUES (?, ?, ?)";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql, new String[] { "idContatoProdutor"})) {
            stmt.setString(1, contatoProdutor.getEmailProdutor());
            stmt.setString(2, contatoProdutor.getTelProdutor());
            stmt.setLong(3, contatoProdutor.getIdProdutor());
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    contatoProdutor.setIdContatoProdutor(generatedKeys.getLong(1));
                    return "Contato do produtor cadastrado com sucesso!";
                } else {
                    throw new ContatoException("Falha ao cadastrar o contato do produtor, nenhum ID obtido");
                }
            }
        } 
    

    public List<ContatoProdutor> listarContatos() {
        List<ContatoProdutor> contatos = new ArrayList<>();
        String sql = "SELECT * FROM T_CONTATO_PRODUTOR";
        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet resultSet = stmt.executeQuery()) {
            while (resultSet.next()) {
                Long idContatoProdutor = resultSet.getLong("id_contato_produtor");
                String emailProdutor = resultSet.getString("email_produtor");
                String telProdutor = resultSet.getString("tel_produtor");
                Long idProdutor = resultSet.getLong("id_produtor");

                ContatoProdutor contatoProdutor = new ContatoProdutor(idContatoProdutor, emailProdutor, telProdutor, idProdutor);
                contatos.add(contatoProdutor);
            }
        } catch (SQLException e) {
            throw new ContatoException("Erro ao listar contatos dos produtores: " + e.getMessage(), e);
        } finally {
            fecharConexao();
        }
        return contatos;
    }

    public String atualizar(ContatoProdutor contatoProdutor) {
        if (contatoProdutor == null || contatoProdutor.getIdContatoProdutor() == null) {
            throw new IllegalArgumentException("ContatoProdutor e seu ID não podem ser nulos");
        }

        String sql = "UPDATE T_CONTATO_PRODUTOR SET email_produtor = ?, tel_produtor = ? WHERE id_contato_produtor = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, contatoProdutor.getEmailProdutor());
            stmt.setString(2, contatoProdutor.getTelProdutor());
            stmt.setLong(3, contatoProdutor.getIdContatoProdutor());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new ContatoException("Contato do produtor não encontrado para atualização");
            }
        } catch (SQLException e) {
            throw new ContatoException("Erro ao atualizar o contato do produtor: " + e.getMessage(), e);
        } finally {
            fecharConexao();
        }
        return "Atualizado com sucesso";
    }

    public String deletar(Long idContatoProdutor) {
        if (idContatoProdutor == null) {
            throw new IllegalArgumentException("ID do ContatoProdutor não pode ser nulo");
        }

        String sql = "DELETE FROM T_CONTATO_PRODUTOR WHERE id_contato_produtor = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setLong(1, idContatoProdutor);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new ContatoException("Contato do produtor não encontrado para exclusão");
            }
        } catch (SQLException e) {
            throw new ContatoException("Erro ao excluir contato do produtor: " + e.getMessage(), e);
        } finally {
            fecharConexao();
        }
        return "Deletado com sucesso";
    }

    public ContatoProdutor buscarPorId(Long idContatoProdutor) {
        if (idContatoProdutor == null) {
            return null;
        }

        String sql = "SELECT * FROM T_CONTATO_PRODUTOR WHERE id_contato_produtor = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setLong(1, idContatoProdutor);

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    String emailProdutor = resultSet.getString("email_produtor");
                    String telProdutor = resultSet.getString("tel_produtor");
                    Long idProdutor = resultSet.getLong("id_produtor");


                    return new ContatoProdutor(idContatoProdutor, emailProdutor, telProdutor, idProdutor);
                } else {
                    throw new ContatoException("Contato do produtor não encontrado");
                }
            }
        } catch (SQLException e) {
            throw new ContatoException("Erro ao buscar contato do produtor por ID: " + e.getMessage(), e);
        } finally {
            fecharConexao();
        }
    }

    private void fecharConexao() {
        try {
            if (conexao != null) {
                conexao.close();
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Erro ao fechar conexão com o banco de dados", e);
        }
    }
}
