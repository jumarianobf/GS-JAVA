package br.com.fiap.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.fiap.connection.ConnectionFactory;
import br.com.fiap.exception.ColetadorException;
import br.com.fiap.exception.DatabaseConnectionException;
import br.com.fiap.model.ColetadorEJL;
import br.com.fiap.model.LocalizacaoColetador;

public class LocalizacaoColetadorDAO {


	private Connection conexao;
	
	public LocalizacaoColetadorDAO() {
		try {
			this.conexao = new ConnectionFactory().conexao();
		} catch (ClassNotFoundException | SQLException e) {
			throw new DatabaseConnectionException("Erro ao conectar com o banco de dados", e);
		}
	}
	
	public String salvar (LocalizacaoColetador localizacaoColetador ) {
        if (localizacaoColetador == null) {
            throw new IllegalArgumentException("localizacao do coletador não pode ser nulo");
        }
        
		String sql = "INSERT INTO T_LOCALIZACAO_COLETADOR "
				+ "(cep_coletador, rua_coletador, praia_coletador, coordenas, id_coletador) VALUES (?, ?, ?, ?, ?)";
		
		try(PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
			stmt.setString(1, localizacaoColetador.getCepColetador());
			stmt.setString(2, localizacaoColetador.getRuaColetador());
			stmt.setString(3, localizacaoColetador.getPraiaColetador());
			stmt.setString(4, localizacaoColetador.getCoordenadas());
			stmt.setLong(5, localizacaoColetador.getColetadorEJL().getIdColetador());
			
			int affectRows = stmt.executeUpdate();
			
			if(affectRows == 0) {
				throw new ColetadorException("Falha ao cadastrar o localização do coletador, nenhuma linha afetada");
			}
			
			try (ResultSet generatedKeys = stmt.getGeneratedKeys()){
				if(generatedKeys.next()) {
					localizacaoColetador.setIdLocalizacaoColetador(generatedKeys.getLong(1));
					return "Localizacao Cadastrada cadastrado com sucesso!";
				} else {
					throw new ColetadorException("Falha ao cadastrar o localização, nenhum ID obtido");
				}
			}
		} catch (SQLException e) {
			throw new ColetadorException("Erro ao salvar localização: " + e.getMessage(), e);
		} finally {
			fecharConexao();
		}
	}
	
	public List<LocalizacaoColetador> listarLocalizacao() {
        List<LocalizacaoColetador> localizacoes = new ArrayList<>();
        String sql = "SELECT * FROM T_LOCALIZACAO_COLETADOR";
        try (PreparedStatement statement = conexao.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Long idLocalizacaoColetador = resultSet.getLong("id_localizacao_coletador");
                String cep_coletador = resultSet.getString("cep_coletador");
                String rua_coletador = resultSet.getString("rua_coletador");
                String praia_coletador = resultSet.getString("praia_coletador");
                String coordenadas = resultSet.getString("coordenadas");
                Long idColetadorEJL = resultSet.getLong("id_coletador");
                		

                ColetadorEJL coletadorEJL = new ColetadorEJL();
                coletadorEJL.setIdColetador(idColetadorEJL);

                LocalizacaoColetador localizacaoColetador = new LocalizacaoColetador(idLocalizacaoColetador, cep_coletador, rua_coletador, praia_coletador, coordenadas, coletadorEJL);
                localizacoes.add(localizacaoColetador);
            }
        } catch (SQLException e) {
            throw new ColetadorException("Erro ao listar localização: " + e.getMessage(), e);
        } finally {
        	fecharConexao();
        }
        return localizacoes;
    }
	
	
	public void atualizar(LocalizacaoColetador localizacaoColetador) {
		if (localizacaoColetador == null || localizacaoColetador.getIdLocalizacaoColetador() == null) {
            throw new IllegalArgumentException("localizacao do coletador e seu ID não podem ser nulos");
        }
		
        String sql = "UPDATE T_LOCALIZACAO_COLETADOR SET cep_coletador = ?, rua_coletador = ?, praia_coletador = ?, coordenadas = ? WHERE id_localizacao_coletador = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, localizacaoColetador.getCepColetador());
            stmt.setString(2, localizacaoColetador.getRuaColetador());
            stmt.setString(3, localizacaoColetador.getPraiaColetador());
            stmt.setString(3, localizacaoColetador.getCoordenadas());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new ColetadorException("Localizacao do coletador não encontrado para atualização");
            }
        } catch (SQLException e) {
            throw new ColetadorException("Erro ao atualizar o localizacao do coletador: " + e.getMessage(), e);
        } finally {
        	fecharConexao();
        }
    }
	
	
	public void deletar(Long idLocalizacaoColetador) {
		if (idLocalizacaoColetador == null) {
            throw new IllegalArgumentException("ID do ProdutorMaterial não pode ser nulo");
        }
		
        String sql = "DELETE FROM T_LOCALIZACAO_COLETADOR WHERE id_localizacao_coletador = ?";
        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setLong(1, idLocalizacaoColetador);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new ColetadorException("Localização do coletador não encontrado para exclusão");
            }
        } catch (SQLException e) {
            throw new ColetadorException("Erro ao excluir localização do coletador: " + e.getMessage(), e);
        } finally {
        	fecharConexao();
        }
    }
	
	
    public LocalizacaoColetador buscarPorId(Long idLocalizacaoColetador) {
    	if (idLocalizacaoColetador == null) {
            return null;
        }
    	
        String sql = "SELECT * FROM T_LOCALIZACAO_COLETADOR WHERE id_localizacao_coletador = ?";
        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setLong(1, idLocalizacaoColetador);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String cepColetador = resultSet.getString("cep_coletador");
                String ruaColetador = resultSet.getString("rua_coletador");
                String praiaColetador = resultSet.getString("praia_coletador");
                String coordenadas = resultSet.getString("coordenadas");
                Long idColetadorEJL = resultSet.getLong("id_coletador");

                ColetadorEJL coletadorEJL = new ColetadorEJL();
                coletadorEJL.setIdColetador(idColetadorEJL);

                return new LocalizacaoColetador(idLocalizacaoColetador, cepColetador, ruaColetador, praiaColetador, coordenadas, coletadorEJL);
            } else {
                throw new ColetadorException("Localizacao do coletador não encontrado");
            }
        } catch (SQLException e) {
            throw new ColetadorException("Erro ao buscar localizacao do coletador por ID: " + e.getMessage(), e);
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

