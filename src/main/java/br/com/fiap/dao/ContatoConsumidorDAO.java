package br.com.fiap.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.fiap.connection.ConnectionFactory;
import br.com.fiap.exception.ContatoException;
import br.com.fiap.exception.DatabaseConnectionException;
import br.com.fiap.model.ContatoConsumidor;

public class ContatoConsumidorDAO {

    private Connection conexao;

    public ContatoConsumidorDAO() {
        try {
            this.conexao = new ConnectionFactory().conexao();
        } catch (ClassNotFoundException | SQLException e) {
            throw new DatabaseConnectionException("Erro ao conectar com o banco de dados", e);
        }
    }

    public String salvar(ContatoConsumidor contatoConsumidor) throws SQLException {
        if (contatoConsumidor == null) {
            throw new IllegalArgumentException("ContatoConsumidor não pode ser nulo");
        }

        String sql = "INSERT INTO T_CONTATO_CONSUMIDOR (email_consumidor, tel_consumidor, id_consumidor_produto) VALUES (?, ?, ?)";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql, new String[] { "id_contato_consumidor"})) {
            stmt.setString(1, contatoConsumidor.getEmailConsumidor());
            stmt.setString(2, contatoConsumidor.getTelConsumidor());
            stmt.setLong(3, contatoConsumidor.getIdConsumidorProduto());
            stmt.executeUpdate();
			ResultSet generatedKeys = stmt.getGeneratedKeys();

                if (generatedKeys.next()) {
                    contatoConsumidor.setIdContatoConsumidor(generatedKeys.getLong(1));
                    return "Contato do consumidor cadastrado com sucesso!";
                } else {
                    throw new ContatoException("Falha ao cadastrar o contato do consumidor, nenhum ID obtido");
                }
            }
        } 
    

    public List<ContatoConsumidor> listarContatos() {
        List<ContatoConsumidor> contatos = new ArrayList<>();
        String sql = "SELECT * FROM T_CONTATO_CONSUMIDOR";
        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet resultSet = stmt.executeQuery()) {
            while (resultSet.next()) {
                Long idContato = resultSet.getLong("id_contato_consumidor");
                String emailConsumidor = resultSet.getString("email_consumidor");
                String telConsumidor = resultSet.getString("tel_consumidor");
                Long idConsumidorProduto = resultSet.getLong("id_consumidor_produto");


                ContatoConsumidor contatoConsumidor = new ContatoConsumidor(idContato, emailConsumidor, telConsumidor, idConsumidorProduto);
                contatos.add(contatoConsumidor);
            }
        } catch (SQLException e) {
            throw new ContatoException("Erro ao listar contatos do consumidor: " + e.getMessage(), e);
        } finally {
            fecharConexao();
        }
        return contatos;
    }

    public String atualizar(ContatoConsumidor contatoConsumidor) {
        if (contatoConsumidor == null || contatoConsumidor.getIdContatoConsumidor() == null) {
            throw new IllegalArgumentException("ContatoConsumidor e seu ID não podem ser nulos");
        }

        String sql = "UPDATE T_CONTATO_CONSUMIDOR SET email_consumidor = ?, tel_consumidor = ? WHERE id_contato_consumidor = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, contatoConsumidor.getEmailConsumidor());
            stmt.setString(2, contatoConsumidor.getTelConsumidor());
            stmt.setLong(3, contatoConsumidor.getIdContatoConsumidor());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new ContatoException("Contato do consumidor não encontrado para atualização");
            }
        } catch (SQLException e) {
            throw new ContatoException("Erro ao atualizar o contato do consumidor: " + e.getMessage(), e);
        } finally {
            fecharConexao();
        }
        return "Atualizado com sucesso";
    }

    public String deletar(Long idContato) {
        if (idContato == null) {
            throw new IllegalArgumentException("ID do ContatoConsumidor não pode ser nulo");
        }

        String sql = "DELETE FROM T_CONTATO_CONSUMIDOR WHERE id_contato_consumidor = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setLong(1, idContato);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new ContatoException("Contato do consumidor não encontrado para exclusão");
            }
        } catch (SQLException e) {
            throw new ContatoException("Erro ao excluir contato do consumidor: " + e.getMessage(), e);
        } finally {
            fecharConexao();
        }
        return "Deletado com sucesso";
    }

    public ContatoConsumidor buscarPorId(Long idContato) {
        if (idContato == null) {
            return null;
        }

        String sql = "SELECT * FROM T_CONTATO_CONSUMIDOR WHERE id_contato_consumidor = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setLong(1, idContato);

            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                String emailConsumidor = resultSet.getString("email_consumidor");
                String telConsumidor = resultSet.getString("tel_consumidor");
                Long idConsumidorProduto = resultSet.getLong("id_consumidor_produto");

                
                return new ContatoConsumidor(idContato, emailConsumidor, telConsumidor, idConsumidorProduto);
            } else {
                throw new ContatoException("Contato do consumidor não encontrado");
            }
        } catch (SQLException e) {
            throw new ContatoException("Erro ao buscar contato do consumidor por ID: " + e.getMessage(), e);
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
