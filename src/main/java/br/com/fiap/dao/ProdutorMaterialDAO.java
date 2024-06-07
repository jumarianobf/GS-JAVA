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
import br.com.fiap.exception.ProdutorMaterialException;
import br.com.fiap.model.ProdutorMaterial;
import br.com.fiap.model.MaterialColetado;

public class ProdutorMaterialDAO {

    private Connection conexao;

    public ProdutorMaterialDAO() {
        try {
            this.conexao = new ConnectionFactory().conexao();
        } catch (ClassNotFoundException | SQLException e) {
            throw new DatabaseConnectionException("Erro ao conectar com o banco de dados", e);
        }
    }

    public String salvar(ProdutorMaterial produtorMaterial) throws SQLException {
        if (produtorMaterial == null) {
            throw new IllegalArgumentException("ProdutorMaterial não pode ser nulo");
        }
        
        String sql = "INSERT INTO T_PRODUTOR_MATERIAL (nome_empresa_produtor, tipo_produtor, id_material) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql, new String[] {"id_produtor"})) {
            stmt.setString(1, produtorMaterial.getNomeEmpresaProdutor());
            stmt.setString(2, produtorMaterial.getTipoProdutor());
            stmt.setLong(3, produtorMaterial.getIdMaterial());
            stmt.executeUpdate();
   
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            
                if (generatedKeys.next()) {
                    produtorMaterial.setIdProdutor(generatedKeys.getLong(1));
                    return "Produtor de material cadastrado com sucesso!";
                } else {
                    throw new ProdutorMaterialException("Falha ao cadastrar o produtor de material, nenhum ID obtido");
                }
            }
        } 
    

    public List<ProdutorMaterial> listarProdutores() {
        List<ProdutorMaterial> produtores = new ArrayList<>();
        String sql = "SELECT * FROM T_PRODUTOR_MATERIAL";
        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet resultSet = stmt.executeQuery()) {
            while (resultSet.next()) {
                Long idProdutor = resultSet.getLong("id_produtor");
                String nomeEmpresaProdutor = resultSet.getString("nome_empresa_produtor");
                String tipoProdutor = resultSet.getString("tipo_produtor");
                Long idMaterial = resultSet.getLong("id_material");


                ProdutorMaterial produtorMaterial = new ProdutorMaterial(idProdutor, nomeEmpresaProdutor, tipoProdutor, idMaterial);
                produtores.add(produtorMaterial);
            }
        } catch (SQLException e) {
            throw new ProdutorMaterialException("Erro ao listar produtores de material: " + e.getMessage(), e);
        } finally {
            fecharConexao();
        }
        return produtores;
    }

    public String atualizar(ProdutorMaterial produtorMaterial) {
    	if (produtorMaterial == null || produtorMaterial.getIdProdutor() == null) {
            throw new IllegalArgumentException("ProdutorMaterial e seu ID não podem ser nulos");
        }
    	
        String sql = "UPDATE T_PRODUTOR_MATERIAL SET nome_empresa_produtor = ?, tipo_produtor = ? WHERE id_produtor = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, produtorMaterial.getNomeEmpresaProdutor());
            stmt.setString(2, produtorMaterial.getTipoProdutor());
            stmt.setLong(3, produtorMaterial.getIdProdutor());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new ProdutorMaterialException("Produtor de material não encontrado para atualização");
            }
        } catch (SQLException e) {
            throw new ProdutorMaterialException("Erro ao atualizar o produtor de material: " + e.getMessage(), e);
        } finally {
            fecharConexao();
        }
        return "Atualizado com sucesso";
    }

    public String deletar(Long idProdutor) {
    	if (idProdutor == null) {
            throw new IllegalArgumentException("ID do ProdutorMaterial não pode ser nulo");
        }
    	
        String sql = "DELETE FROM T_PRODUTOR_MATERIAL WHERE id_produtor = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setLong(1, idProdutor);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new ProdutorMaterialException("Produtor de material não encontrado para exclusão");
            }
        } catch (SQLException e) {
            throw new ProdutorMaterialException("Erro ao excluir produtor de material: " + e.getMessage(), e);
        } finally {
            fecharConexao();
        }
        return "Deletado com sucesso";
    }

    public ProdutorMaterial buscarPorId(Long idProdutor) {
        if (idProdutor == null) {
            return null;
        }
        String sql = "SELECT * FROM T_PRODUTOR_MATERIAL WHERE id_produtor = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setLong(1, idProdutor);

            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                String nomeEmpresaProdutor = resultSet.getString("nome_empresa_produtor");
                String tipoProdutor = resultSet.getString("tipo_produtor");
                Long idMaterial = resultSet.getLong("id_material");


                return new ProdutorMaterial(idProdutor, nomeEmpresaProdutor, tipoProdutor, idMaterial);
            } else {
                throw new ProdutorMaterialException("Produtor de material não encontrado");
            }
        } catch (SQLException e) {
            throw new ProdutorMaterialException("Erro ao buscar produtor de material por ID: " + e.getMessage(), e);
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
