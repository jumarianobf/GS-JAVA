package br.com.fiap.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.fiap.connection.ConnectionFactory;
import br.com.fiap.exception.ConsumidorException;
import br.com.fiap.exception.DatabaseConnectionException;
import br.com.fiap.model.ConsumidorProduto;
import br.com.fiap.model.TransacaoConsumidor;

public class TransacaoConsumidorDAO {

    private Connection conexao;

    public TransacaoConsumidorDAO() {
        try {
            this.conexao = new ConnectionFactory().conexao();
        } catch (ClassNotFoundException | SQLException e) {
            throw new DatabaseConnectionException("Erro ao conectar com o banco de dados", e);
        }
    }

    public String salvar(TransacaoConsumidor transacaoConsumidor) {
        if (transacaoConsumidor == null) {
            throw new IllegalArgumentException("TransacaoConsumidor não pode ser nulo");
        }

        String sql = "INSERT INTO T_TRANSACAO_CONSUMIDOR "
                + "(data_transicao, qtd_transacao_consumidor, valor_total_transacao, id_consumidor_produto) "
                + "VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setDate(1, new java.sql.Date(transacaoConsumidor.getDataTransicao().getTime()));
            stmt.setInt(2, transacaoConsumidor.getQtdTransacaoConsumidor());
            stmt.setDouble(3, transacaoConsumidor.getValorTotalTransacao());
            stmt.setLong(4, transacaoConsumidor.getConsumidorProduto().getIdConsumidorProduto());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new ConsumidorException("Falha ao cadastrar a transação do consumidor, nenhuma linha afetada");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    transacaoConsumidor.setIdTransacaoConsumidor(generatedKeys.getLong(1));
                    return "Transação do consumidor cadastrada com sucesso!";
                } else {
                    throw new ConsumidorException("Falha ao cadastrar a transação do consumidor, nenhum ID obtido");
                }
            }
        } catch (SQLException e) {
            throw new ConsumidorException("Erro ao salvar transação do consumidor: " + e.getMessage(), e);
        } finally {
            fecharConexao();
        }
    }

    public List<TransacaoConsumidor> listarTransacoes() {
        List<TransacaoConsumidor> transacoes = new ArrayList<>();
        String sql = "SELECT * FROM T_TRANSACAO_CONSUMIDOR";
        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet resultSet = stmt.executeQuery()) {
            while (resultSet.next()) {
                Long idTransacaoConsumidor = resultSet.getLong("id_transacao_consumidor");
                Date dataTransicao = resultSet.getDate("data_transicao");
                int qtdTransacaoConsumidor = resultSet.getInt("qtd_transacao_consumidor");
                double valorTotalTransacao = resultSet.getDouble("valor_total_transacao");
                Long idConsumidorProduto = resultSet.getLong("id_consumidor_produto");

                ConsumidorProduto consumidorProduto = new ConsumidorProduto();
                consumidorProduto.setIdConsumidorProduto(idConsumidorProduto);

                TransacaoConsumidor transacaoConsumidor = new TransacaoConsumidor(idTransacaoConsumidor, dataTransicao,
                        qtdTransacaoConsumidor, valorTotalTransacao, consumidorProduto);
                transacoes.add(transacaoConsumidor);
            }
        } catch (SQLException e) {
            throw new ConsumidorException("Erro ao listar transações do consumidor: " + e.getMessage(), e);
        } finally {
            fecharConexao();
        }
        return transacoes;
    }

    public String atualizar(TransacaoConsumidor transacaoConsumidor) {
        if (transacaoConsumidor == null || transacaoConsumidor.getIdTransacaoConsumidor() == null) {
            throw new IllegalArgumentException("TransacaoConsumidor e seu ID não podem ser nulos");
        }

        String sql = "UPDATE T_TRANSACAO_CONSUMIDOR SET data_transicao = ?, qtd_transacao_consumidor = ?, "
                + "valor_total_transacao = ?, id_consumidor_produto = ? WHERE id_transacao_consumidor = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setDate(1, new java.sql.Date(transacaoConsumidor.getDataTransicao().getTime()));
            stmt.setInt(2, transacaoConsumidor.getQtdTransacaoConsumidor());
            stmt.setDouble(3, transacaoConsumidor.getValorTotalTransacao());
            stmt.setLong(4, transacaoConsumidor.getConsumidorProduto().getIdConsumidorProduto());
            stmt.setLong(5, transacaoConsumidor.getIdTransacaoConsumidor());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new ConsumidorException("Transação do consumidor não encontrada para atualização");
            }
        } catch (SQLException e) {
            throw new ConsumidorException("Erro ao atualizar a transação do consumidor: " + e.getMessage(), e);
        } finally {
            fecharConexao();
        }
        return "Atualizado com sucesso";
    }

    public String deletar(Long idTransacaoConsumidor) {
        if (idTransacaoConsumidor == null) {
            throw new IllegalArgumentException("ID da TransacaoConsumidor não pode ser nulo");
        }

        String sql = "DELETE FROM T_TRANSACAO_CONSUMIDOR WHERE id_transacao_consumidor = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setLong(1, idTransacaoConsumidor);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new ConsumidorException("Transação do consumidor não encontrada para exclusão");
            }
        } catch (SQLException e) {
            throw new ConsumidorException("Erro ao excluir transação do consumidor: " + e.getMessage(), e);
        } finally {
            fecharConexao();
        }
        return "Deletado com sucesso";
    }

    public TransacaoConsumidor buscarPorId(Long idTransacaoConsumidor) {
        if (idTransacaoConsumidor == null) {
            return null;
        }

        String sql = "SELECT * FROM T_TRANSACAO_CONSUMIDOR WHERE id_transacao_consumidor = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setLong(1, idTransacaoConsumidor);

            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                Date dataTransicao = resultSet.getDate("data_transicao");
                int qtdTransacaoConsumidor = resultSet.getInt("qtd_transacao_consumidor");
                double valorTotalTransacao = resultSet.getDouble("valor_total_transacao");
                Long idConsumidorProduto = resultSet.getLong("id_consumidor_produto");

                ConsumidorProduto consumidorProduto = new ConsumidorProduto();
                consumidorProduto.setIdConsumidorProduto(idConsumidorProduto);
                
                return new TransacaoConsumidor(idTransacaoConsumidor, dataTransicao, qtdTransacaoConsumidor,
                        valorTotalTransacao, consumidorProduto);
            } else {
                throw new ConsumidorException("Transação do consumidor não encontrada");
            }
        } catch (SQLException e) {
            throw new ConsumidorException("Erro ao buscar transação do consumidor por ID: " + e.getMessage(), e);
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
               
