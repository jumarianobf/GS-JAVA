package br.com.fiap.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.fiap.connection.ConnectionFactory;
import br.com.fiap.exception.DatabaseConnectionException;
import br.com.fiap.exception.ConsumidorException;
import br.com.fiap.model.ConsumidorProduto;

public class ConsumidorProdutoDAO {

    private Connection conexao;

    public ConsumidorProdutoDAO() {
        try {
            this.conexao = new ConnectionFactory().conexao();
        } catch (ClassNotFoundException | SQLException e) {
            throw new DatabaseConnectionException("Erro ao conectar com o banco de dados", e);
        }
    }

    public String salvar(ConsumidorProduto consumidorProduto) throws SQLException {
        if (consumidorProduto == null) {
            throw new IllegalArgumentException("ConsumidorProduto não pode ser nulo");
        }

        String sql = "INSERT INTO T_CONSUMIDOR_PRODUTO (primeiro_nome_consumidor, sobrenome_consumidor, cpf_consumidor) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql, new String[]{"id_consumidor_produto"})) {
            stmt.setString(1, consumidorProduto.getPrimeiroNomeConsumidor());
            stmt.setString(2, consumidorProduto.getSobrenomeConsumidor());
            stmt.setString(3, consumidorProduto.getCpfConsumidor());
            stmt.executeUpdate();
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            
                if (generatedKeys.next()) {
                    consumidorProduto.setIdConsumidorProduto(generatedKeys.getLong(1));
                    return "Consumidor cadastrado com sucesso!";
                } else {
                    throw new ConsumidorException("Falha ao cadastrar o consumidor, nenhum ID obtido");
                }
            }
 
    }

    public List<ConsumidorProduto> listarConsumidores() {
        List<ConsumidorProduto> consumidores = new ArrayList<>();
        String sql = "SELECT * FROM T_CONSUMIDOR_PRODUTO";
        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet resultSet = stmt.executeQuery()) {
            while (resultSet.next()) {
                Long idConsumidorProduto = resultSet.getLong("id_consumidor_produto");
                String primeiroNomeConsumidor = resultSet.getString("primeiro_nome_consumidor");
                String sobrenomeConsumidor = resultSet.getString("sobrenome_consumidor");
                String cpfConsumidor = resultSet.getString("cpf_consumidor");

                ConsumidorProduto consumidorProduto = new ConsumidorProduto(idConsumidorProduto, primeiroNomeConsumidor, sobrenomeConsumidor, cpfConsumidor);
                consumidores.add(consumidorProduto);
            }
        } catch (SQLException e) {
            throw new ConsumidorException("Erro ao listar consumidores: " + e.getMessage(), e);
        } 
        return consumidores;
    }

    public String atualizar(ConsumidorProduto consumidorProduto) {
        if (consumidorProduto == null || consumidorProduto.getIdConsumidorProduto() == null) {
            throw new IllegalArgumentException("ConsumidorProduto e seu ID não podem ser nulos");
        }

        String sql = "UPDATE T_CONSUMIDOR_PRODUTO SET primeiro_nome_consumidor = ?, sobrenome_consumidor = ?, cpf_consumidor = ? WHERE id_consumidor_produto = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, consumidorProduto.getPrimeiroNomeConsumidor());
            stmt.setString(2, consumidorProduto.getSobrenomeConsumidor());
            stmt.setString(3, consumidorProduto.getCpfConsumidor());
            stmt.setLong(4, consumidorProduto.getIdConsumidorProduto());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new ConsumidorException("Consumidor não encontrado para atualização");
            }
        } catch (SQLException e) {
            throw new ConsumidorException("Erro ao atualizar o consumidor: " + e.getMessage(), e);
        } 
        return "Atualizado com sucesso";
    }

    public String deletar(Long idConsumidorProduto) {
        if (idConsumidorProduto == null) {
            throw new IllegalArgumentException("ID do ConsumidorProduto não pode ser nulo");
        }

        String sql = "DELETE FROM T_CONSUMIDOR_PRODUTO WHERE id_consumidor_produto = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setLong(1, idConsumidorProduto);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new ConsumidorException("Consumidor não encontrado para exclusão");
            }
        } catch (SQLException e) {
            throw new ConsumidorException("Erro ao excluir consumidor: " + e.getMessage(), e);
        } 
        return "Deletado com sucesso";
    }

    public ConsumidorProduto buscarPorId(Long idConsumidorProduto) {
        if (idConsumidorProduto == null) {
            return null;
        }

        String sql = "SELECT * FROM T_CONSUMIDOR_PRODUTO WHERE id_consumidor_produto = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setLong(1, idConsumidorProduto);

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    String primeiroNomeConsumidor = resultSet.getString("primeiro_nome_consumidor");
                    String sobrenomeConsumidor = resultSet.getString("sobrenome_consumidor");
                    String cpfConsumidor = resultSet.getString("cpf_consumidor");

                    return new ConsumidorProduto(idConsumidorProduto, primeiroNomeConsumidor, sobrenomeConsumidor, cpfConsumidor);
                } else {
                    throw new ConsumidorException("Consumidor não encontrado");
                }
            }
        } catch (SQLException e) {
            throw new ConsumidorException("Erro ao buscar consumidor por ID: " + e.getMessage(), e);
        } 
    }

    
}

