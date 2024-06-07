package br.com.fiap.dao;

import br.com.fiap.connection.ConnectionFactory;
import br.com.fiap.exception.*;
import br.com.fiap.model.ContatoColetador;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContatoColetadorDAO {

    private Connection conexao;

    public ContatoColetadorDAO() {
        try {
            this.conexao = new ConnectionFactory().conexao();
        } catch (ClassNotFoundException | SQLException e) {
            throw new DatabaseConnectionException("Erro ao conectar com o banco de dados", e);
        }
    }

    public String salvar(ContatoColetador contatoColetador) throws SQLException {
    	if (contatoColetador == null) {
            throw new IllegalArgumentException("contato coletador não pode ser nulo");
        }
        String sql = "INSERT INTO T_CONTATO_COLETADOR (email_coletador, tel_coletador, id_coletador) VALUES (?, ?, ?)";

        try (PreparedStatement statement = conexao.prepareStatement(sql, new String[] { "id_contato_coletador"})) {
            statement.setString(1, contatoColetador.getEmailColetador());
            statement.setString(2, contatoColetador.getTelColetador());
            statement.setLong(3, contatoColetador.getIdColetador());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();

            	if(generatedKeys.next()) {
                    contatoColetador.setIdContatoColetador(generatedKeys.getLong(1));
            		return "Contato do coletador cadastrado com sucesso!";
                } else {
                    throw new ContatoException("Falha ao cadastrar contato do coletador, nenhum ID obtido.");
                }
            }
    }
       
    
    
    public List<ContatoColetador> listarContatos() {
        List<ContatoColetador> contatos = new ArrayList<>();
        String sql = "SELECT * FROM T_CONTATO_COLETADOR";
        try (PreparedStatement statement = conexao.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Long idContatoColetador = resultSet.getLong("id_contato_coletador");
                String email = resultSet.getString("email_coletador");
                String telefone = resultSet.getString("tel_coletador");
                Long idColetadorEjl = resultSet.getLong("id_coletador");

                ContatoColetador contato = new ContatoColetador(idContatoColetador, email, telefone, idColetadorEjl);
                contatos.add(contato);
            }
        } catch (SQLException e) {
            throw new ContatoException("Erro ao listar contatos: " + e.getMessage(), e);
        } finally {
        	fecharConexao();
        }
        return contatos;
    }
    
    public void atualizar(ContatoColetador contatoColetador) {
        String sql = "UPDATE T_CONTATO_COLETADOR SET email_coletador = ?, tel_coletador = ? WHERE id_contato_coletador = ?";
        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setString(1, contatoColetador.getEmailColetador());
            statement.setString(2, contatoColetador.getTelColetador());
            statement.setLong(3, contatoColetador.getIdContatoColetador());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new ContatoException("Contato coletador não encontrado para atualização");
            }
        } catch (SQLException e) {
            throw new ContatoException("Erro ao atualizar contato coletador: " + e.getMessage(), e);
        } finally {
        	fecharConexao();
        }
    }

    public void deletar(Long idContatoColetador) {
        String sql = "DELETE FROM T_CONTATO_COLETADOR WHERE id_contato_coletador = ?";
        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setLong(1, idContatoColetador);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new ContatoException("Contato coletador não encontrado para exclusão");
            }
        } catch (SQLException e) {
            throw new ContatoException("Erro ao excluir contato coletador: " + e.getMessage(), e);
        } finally {
        	fecharConexao();
        }
    }

    public ContatoColetador buscarPorId(Long idContatoColetador) {
        String sql = "SELECT * FROM T_CONTATO_COLETADOR WHERE id_contato_coletador = ?";
        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setLong(1, idContatoColetador);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String email = resultSet.getString("email_coletador");
                String telefone = resultSet.getString("tel_coletador");
                Long idColetador = resultSet.getLong("id_coletador");


                return new ContatoColetador(idContatoColetador, email, telefone, idColetador);
            } else {
                throw new ContatoException("Contato coletador não encontrado");
            }
        } catch (SQLException e) {
            throw new ContatoException("Erro ao buscar contato coletador por ID: " + e.getMessage(), e);
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
