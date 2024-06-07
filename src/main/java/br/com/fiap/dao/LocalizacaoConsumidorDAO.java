package br.com.fiap.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.fiap.connection.ConnectionFactory;
import br.com.fiap.exception.ConsumidorException;
import br.com.fiap.exception.DatabaseConnectionException;
import br.com.fiap.model.ConsumidorProduto;
import br.com.fiap.model.LocalizacaoConsumidor;
import br.com.fiap.model.ProdutoProdutor;

public class LocalizacaoConsumidorDAO {

    private Connection conexao;

    public LocalizacaoConsumidorDAO() {
        try {
            this.conexao = new ConnectionFactory().conexao();
        } catch (ClassNotFoundException | SQLException e) {
            throw new DatabaseConnectionException("Erro ao conectar com o banco de dados", e);
        }
    }

    public String salvar(LocalizacaoConsumidor localizacaoConsumidor) {
        if (localizacaoConsumidor == null) {
            throw new IllegalArgumentException("LocalizacaoConsumidor não pode ser nulo");
        }

        String sql = "INSERT INTO T_LOCALIZACAO_CONSUMIDOR "
                + "(logradouro_consumidor, bairro_residencia_consumidor, numero_residencia_consumidor, "
                + "complemento_residencia_consumidor, cep_consumidor, id_consumidor_produto, id_produto_produtor) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, localizacaoConsumidor.getLogradouroConsumidor());
            stmt.setString(2, localizacaoConsumidor.getBairroResidenciaConsumidor());
            stmt.setInt(3, localizacaoConsumidor.getNumeroResidenciaConsumidor());
            stmt.setString(4, localizacaoConsumidor.getComplementoResidenciaConsumidor());
            stmt.setString(5, localizacaoConsumidor.getCepConsumidor());
            stmt.setLong(6, localizacaoConsumidor.getConsumidorProduto().getIdConsumidorProduto());
            stmt.setLong(7, localizacaoConsumidor.getProdutoProdutor().getIdProdutoProdutor());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new ConsumidorException("Falha ao cadastrar a localização do consumidor, nenhuma linha afetada");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    localizacaoConsumidor.setIdLocalizacaoConsumidor(generatedKeys.getLong(1));
                    return "Localização do consumidor cadastrada com sucesso!";
                } else {
                    throw new ConsumidorException("Falha ao cadastrar a localização do consumidor, nenhum ID obtido");
                }
            }
        } catch (SQLException e) {
            throw new ConsumidorException("Erro ao salvar localização do consumidor: " + e.getMessage(), e);
        } finally {
            fecharConexao();
        }
    }
    
    public List<LocalizacaoConsumidor> listar() {
        List<LocalizacaoConsumidor> localizacoes = new ArrayList<>();
        String sql = "SELECT * FROM T_LOCALIZACAO_CONSUMIDOR";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                LocalizacaoConsumidor localizacao = popularLocalizacaoConsumidor(rs);
                localizacoes.add(localizacao);
            }
            return localizacoes;
        } catch (SQLException e) {
            throw new ConsumidorException("Erro ao listar localizações dos consumidores: " + e.getMessage(), e);
        } finally {
            fecharConexao();
        }
    }

    public String atualizar(LocalizacaoConsumidor localizacaoConsumidor) {
        if (localizacaoConsumidor == null) {
            throw new IllegalArgumentException("LocalizacaoConsumidor não pode ser nulo");
        }

        String sql = "UPDATE T_LOCALIZACAO_CONSUMIDOR SET "
                + "logradouro_consumidor = ?, "
                + "bairro_residencia_consumidor = ?, "
                + "numero_residencia_consumidor = ?, "
                + "complemento_residencia_consumidor = ?, "
                + "cep_consumidor = ?, "
                + "id_consumidor_produto = ?, "
                + "id_produto_produtor = ? "
                + "WHERE id_localizacao_consumidor = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, localizacaoConsumidor.getLogradouroConsumidor());
            stmt.setString(2, localizacaoConsumidor.getBairroResidenciaConsumidor());
            stmt.setInt(3, localizacaoConsumidor.getNumeroResidenciaConsumidor());
            stmt.setString(4, localizacaoConsumidor.getComplementoResidenciaConsumidor());
            stmt.setString(5, localizacaoConsumidor.getCepConsumidor());
            stmt.setLong(6, localizacaoConsumidor.getConsumidorProduto().getIdConsumidorProduto());
            stmt.setLong(7, localizacaoConsumidor.getProdutoProdutor().getIdProdutoProdutor());
            stmt.setLong(8, localizacaoConsumidor.getIdLocalizacaoConsumidor());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new ConsumidorException("Falha ao atualizar a localização do consumidor, nenhuma linha afetada");
            }

            return "Localização do consumidor atualizada com sucesso!";
        } catch (SQLException e) {
            throw new ConsumidorException("Erro ao atualizar localização do consumidor: " + e.getMessage(), e);
        } finally {
            fecharConexao();
        }
    }

    public String deletar(Long idLocalizacaoConsumidor) {
        if (idLocalizacaoConsumidor == null) {
            throw new IllegalArgumentException("ID da LocalizacaoConsumidor não pode ser nulo");
        }

        String sql = "DELETE FROM T_LOCALIZACAO_CONSUMIDOR WHERE id_localizacao_consumidor = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setLong(1, idLocalizacaoConsumidor);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new ConsumidorException("Falha ao deletar a localização do consumidor, nenhum registro encontrado");
            }

            return "Localização do consumidor deletada com sucesso!";
        } catch (SQLException e) {
            throw new ConsumidorException("Erro ao deletar localização do consumidor: " + e.getMessage(), e);
        } finally {
            fecharConexao();
        }
    }

    public LocalizacaoConsumidor buscarPorId(Long idLocalizacaoConsumidor) {
        if (idLocalizacaoConsumidor == null) {
            throw new IllegalArgumentException("ID da LocalizacaoConsumidor não pode ser nulo");
        }

        String sql = "SELECT * FROM T_LOCALIZACAO_CONSUMIDOR WHERE id_localizacao_consumidor = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setLong(1, idLocalizacaoConsumidor);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return popularLocalizacaoConsumidor(rs);
            } else {
                throw new ConsumidorException("Localização do consumidor não encontrada");
            }
        } catch (SQLException e) {
            throw new ConsumidorException("Erro ao buscar localização do consumidor por ID: " + e.getMessage(), e);
        } finally {
            fecharConexao();
        }
    }

    private LocalizacaoConsumidor popularLocalizacaoConsumidor(ResultSet rs) throws SQLException {
        Long idLocalizacaoConsumidor = rs.getLong("id_localizacao_consumidor");
        String logradouroConsumidor = rs.getString("logradouro_consumidor");
        String bairroResidenciaConsumidor = rs.getString("bairro_residencia_consumidor");
        int numeroResidenciaConsumidor = rs.getInt("numero_residencia_consumidor");
        String complementoResidenciaConsumidor = rs.getString("complemento_residencia_consumidor");
        String cepConsumidor = rs.getString("cep_consumidor");
        Long idConsumidorProduto = rs.getLong("id_consumidor_produto");
        Long idProdutoProdutor = rs.getLong("id_produto_produtor");

        ConsumidorProduto consumidorProduto = new ConsumidorProdutoDAO().buscarPorId(idConsumidorProduto);
        ProdutoProdutor produtoProdutor = new ProdutoProdutorDAO().buscarPorId(idProdutoProdutor);

        return new LocalizacaoConsumidor(idLocalizacaoConsumidor, logradouroConsumidor,
                bairroResidenciaConsumidor, numeroResidenciaConsumidor,
                complementoResidenciaConsumidor, cepConsumidor,
                consumidorProduto, produtoProdutor);
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
