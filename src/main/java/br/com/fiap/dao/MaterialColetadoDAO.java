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
import br.com.fiap.exception.MaterialColetadoException;
import br.com.fiap.model.ColetadorEJL;
import br.com.fiap.model.MaterialColetado;

public class MaterialColetadoDAO {

		private Connection conexao;
		
		public MaterialColetadoDAO() {
			try {
				this.conexao = new ConnectionFactory().conexao();
			} catch (ClassNotFoundException | SQLException e) {
				throw new DatabaseConnectionException("Erro ao conectar com o banco de dados", e);
			}
		}
		
		public String salvar (MaterialColetado materialColetado ) {
			String sql = "INSERT INTO T_MATERIAL_COLETADOR "
					+ "(tipo_material, qtd_material, id_coletador) VALUES (?, ?, ?)";
			
			try(PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
				stmt.setString(1, materialColetado.getTipoMaterial());
				stmt.setInt(2, materialColetado.getQtdMaterial());
				stmt.setLong(4, materialColetado.getColetadorEJL().getIdColetador());
				
				int affectRows = stmt.executeUpdate();
				
				if(affectRows == 0) {
					throw new MaterialColetadoException("Falha ao cadastrar o material coletado, nenhuma linha afetada");
				}
				
				try (ResultSet generatedKeys = stmt.getGeneratedKeys()){
					if(generatedKeys.next()) {
						materialColetado.setIdMaterial(generatedKeys.getLong(1));
						return "Material coletado cadastrado com sucesso!";
					} else {
						throw new MaterialColetadoException("Falha ao cadastrar o material coletado, nenhum ID obtido");
					}
				}
			} catch (SQLException e) {
				throw new MaterialColetadoException("Erro ao salvar material coletado: " + e.getMessage(), e);
			} finally {
				fecharConexao();
			}
		}
		
		public List<MaterialColetado> listarMaterial() {
	        List<MaterialColetado> materiais = new ArrayList<>();
	        String sql = "SELECT * FROM T_MATERIAL_COLETADO";
	        try (PreparedStatement statement = conexao.prepareStatement(sql);
	             ResultSet resultSet = statement.executeQuery()) {
	            while (resultSet.next()) {
	                Long idMaterialColetado= resultSet.getLong("id_material");
	                String tipoMaterial= resultSet.getString("tipo_material");
	                int qtdMaterial = resultSet.getInt("qtd_material");
	                Long idColetadorEJL = resultSet.getLong("id_coletador");
	                		

	                ColetadorEJL coletadorEJL = new ColetadorEJL();
	                coletadorEJL.setIdColetador(idColetadorEJL);

	                MaterialColetado materialColetado= new MaterialColetado(idMaterialColetado, tipoMaterial, qtdMaterial, coletadorEJL);
	                materiais.add(materialColetado);
	            }
	        } catch (SQLException e) {
	            throw new MaterialColetadoException("Erro ao listar materiais coletados: " + e.getMessage(), e);
	        } finally {
	        	fecharConexao();
	        }
	        return materiais;
	    }
		
		
		public String atualizar(MaterialColetado materialColetado) {
	        String sql = "UPDATE T_MATERIAL_COLETADO SET tipo_material = ?, qtd_material = ? WHERE id_material = ?";
	        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
	            stmt.setString(1, materialColetado.getTipoMaterial());
	            stmt.setInt(2, materialColetado.getQtdMaterial());
	            stmt.setLong(3, materialColetado.getIdMaterial());
	            int affectedRows = stmt.executeUpdate();
	            if (affectedRows == 0) {
	                throw new MaterialColetadoException("Material coletado não encontrado para atualização");
	            }
	        } catch (SQLException e) {
	            throw new MaterialColetadoException("Erro ao atualizar o material coletado: " + e.getMessage(), e);
	        } finally {
	        	fecharConexao();
	        }
	        return "Atualizado com sucesso";
	    }
		
		
		public String deletar(Long idMaterial) {
	        String sql = "DELETE FROM T_MATERIAL_COLETADO WHERE id_material = ?";
	        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
	            statement.setLong(1, idMaterial);
	            int affectedRows = statement.executeUpdate();
	            if (affectedRows == 0) {
	                throw new MaterialColetadoException("Material coletado não encontrado para exclusão");
	            }
	        } catch (SQLException e) {
	            throw new MaterialColetadoException("Erro ao excluir material coletador: " + e.getMessage(), e);
	        } finally {
	        	fecharConexao();
	        }
	        return "Deletado com sucesso";
	    }
		
		
	    public MaterialColetado buscarPorId(Long idMaterial) {
	    	if (idMaterial == null) {
	    		return null;
	    	}
	        String sql = "SELECT * FROM T_MATERIAL_COLETADO WHERE id_material = ?";
	        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
	            statement.setLong(1, idMaterial);
	            ResultSet resultSet = statement.executeQuery();
	            if (resultSet.next()) {
	                String tipoMaterial = resultSet.getString("tipo_material");
	                int qtdMaterial = resultSet.getInt("qtd_material");
	                Long idColetadorEJL = resultSet.getLong("id_coletador");

	                ColetadorEJL coletadorEJL = new ColetadorEJL();
	                coletadorEJL.setIdColetador(idColetadorEJL);

	                return new MaterialColetado(idMaterial, tipoMaterial, qtdMaterial, coletadorEJL);
	            } else {
	                throw new MaterialColetadoException("Material coletado não encontrado");
	            }
	        } catch (SQLException e) {
	            throw new MaterialColetadoException("Erro ao buscar material coletador por ID: " + e.getMessage(), e);
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
