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
import br.com.fiap.exception.ProdutoException;
import br.com.fiap.model.ProdutoProdutor;
import br.com.fiap.model.ProdutorMaterial;

public class ProdutoProdutorDAO {

    private Connection conexao;

    public ProdutoProdutorDAO() {
        try {
            this.conexao = new ConnectionFactory().conexao();
        } catch (ClassNotFoundException | SQLException e) {
            throw new DatabaseConnectionException("Erro ao conectar com o banco de dados", e);
        }
    }

    public String salvar(ProdutoProdutor produtoProdutor) {
        if (produtoProdutor == null) {
            throw new IllegalArgumentException("ProdutoProdutor não pode ser nulo");
        }

        String sql = "INSERT INTO T_PRODUTO_PRODUTOR (nome_produto, descricao_produto, preco_produto, id_produtor) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, produtoProdutor.getNomeProduto());
            stmt.setString(2, produtoProdutor.getDescricaoProduto());
            stmt.setDouble(3, produtoProdutor.getPrecoProduto());
            stmt.setLong(4, produtoProdutor.getProdutorMaterial().getIdProdutor());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new ProdutoException("Falha ao cadastrar o produto produtor, nenhuma linha afetada");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    produtoProdutor.setIdProdutoProdutor(generatedKeys.getLong(1));
                    return "Produto produtor cadastrado com sucesso!";
                } else {
                    throw new ProdutoException("Falha ao cadastrar o produto produtor, nenhum ID obtido");
                }
            }
        } catch (SQLException e) {
            throw new ProdutoException("Erro ao salvar produto produtor: " + e.getMessage(), e);
        } finally {
            fecharConexao();
        }
    }

    public List<ProdutoProdutor> listarProdutos() {
        List<ProdutoProdutor> produtos = new ArrayList<>();
        String sql = "SELECT * FROM T_PRODUTO_PRODUTOR";
        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet resultSet = stmt.executeQuery()) {
            while (resultSet.next()) {
                Long idProdutoProdutor = resultSet.getLong("id_produto_produtor");
                String nomeProduto = resultSet.getString("nome_produto");
                String descricaoProduto = resultSet.getString("descricao_produto");
                double precoProduto = resultSet.getDouble("preco_produto");
                Long idProdutor = resultSet.getLong("id_produtor");

                ProdutorMaterial produtorMaterial = new ProdutorMaterial();
                produtorMaterial.setIdProdutor(idProdutor);

                ProdutoProdutor produtoProdutor = new ProdutoProdutor(idProdutoProdutor, nomeProduto, descricaoProduto, precoProduto, produtorMaterial);
                produtos.add(produtoProdutor);
            }
        } catch (SQLException e) {
            throw new ProdutoException("Erro ao listar produtos produtores: " + e.getMessage(), e);
        } finally {
            fecharConexao();
        }
        return produtos;
    }

    public String atualizar(ProdutoProdutor produtoProdutor) {
        if (produtoProdutor == null || produtoProdutor.getIdProdutoProdutor() == null) {
            throw new IllegalArgumentException("ProdutoProdutor e seu ID não podem ser nulos");
        }

        String sql = "UPDATE T_PRODUTO_PRODUTOR SET nome_produto = ?, descricao_produto = ?, preco_produto = ? WHERE id_produto_produtor = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, produtoProdutor.getNomeProduto());
            stmt.setString(2, produtoProdutor.getDescricaoProduto());
            stmt.setDouble(3, produtoProdutor.getPrecoProduto());
            stmt.setLong(4, produtoProdutor.getIdProdutoProdutor());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new ProdutoException("Produto produtor não encontrado para atualização");
            }
        } catch (SQLException e) {
            throw new ProdutoException("Erro ao atualizar o produto produtor: " + e.getMessage(), e);
        } finally {
            fecharConexao();
        }
        return "Atualizado com sucesso";
    }

    public String deletar(Long idProdutoProdutor) {
        if (idProdutoProdutor == null) {
            throw new IllegalArgumentException("ID do ProdutoProdutor não pode ser nulo");
        }

        String sql = "DELETE FROM T_PRODUTO_PRODUTOR WHERE id_produto_produtor = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setLong(1, idProdutoProdutor);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new ProdutoException("Produto produtor não encontrado para exclusão");
            }
        } catch (SQLException e) {
            throw new ProdutoException("Erro ao excluir produto produtor: " + e.getMessage(), e);
        } finally {
            fecharConexao();
        }
        return "Deletado com sucesso";
    }

    public ProdutoProdutor buscarPorId(Long idProdutoProdutor) {
        if (idProdutoProdutor == null) {
            return null;
        }

        String sql = "SELECT * FROM T_PRODUTO_PRODUTOR WHERE id_produto_produtor = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setLong(1, idProdutoProdutor);

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    String nomeProduto = resultSet.getString("nome_produto");
                    String descricaoProduto = resultSet.getString("descricao_produto");
                    double precoProduto = resultSet.getDouble("preco_produto");
                    Long idProdutor = resultSet.getLong("id_produtor");

                    ProdutorMaterial produtorMaterial = new ProdutorMaterial();
                    produtorMaterial.setIdProdutor(idProdutor);

                    return new ProdutoProdutor(idProdutoProdutor, nomeProduto, descricaoProduto, precoProduto, produtorMaterial);
                } else {
                    throw new ProdutoException("Produto produtor não encontrado");
                }
            }
        } catch (SQLException e) {
            throw new ProdutoException("Erro ao buscar produto produtor por ID: " + e.getMessage(), e);
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
