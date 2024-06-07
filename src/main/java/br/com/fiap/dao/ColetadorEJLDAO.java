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



public class ColetadorEJLDAO {
	
	private Connection conexao;
	
	public ColetadorEJLDAO() {
		try {
			this.conexao = new ConnectionFactory().conexao();
		} catch (ClassNotFoundException | SQLException e) {
			throw new DatabaseConnectionException("Erro ao conectar com o banco de dados", e);
		}
	}
	
	public String salvar (ColetadorEJL coletadorEJL) throws SQLException {
		if (coletadorEJL == null) {
            throw new IllegalArgumentException("Coletador não pode ser nulo");
        }
		String sql = "INSERT INTO T_COLETADOR_EJL "
				+ "(primeiro_nome_coletador, sobrenome_coletador, qtd_coletado) VALUES (?, ?, ?)";
		
		try(PreparedStatement stmt = conexao.prepareStatement(sql, new String[] { "id_coletador"})){
			stmt.setString(1, coletadorEJL.getPrimeiroNomeColetador());
			stmt.setString(2, coletadorEJL.getSobrenomeColetador());
			stmt.setLong(3, coletadorEJL.getQtdColetado());
			stmt.executeUpdate();
			ResultSet generatedKeys = stmt.getGeneratedKeys();
				
				if(generatedKeys.next()) {
					coletadorEJL.setIdColetador(generatedKeys.getLong(1));
					return "Coletador cadastrado com sucesso!";
				} else {
					throw new ColetadorException("Falha ao cadastrar o coletador EJL, nenhum ID obtido");
				}
			}
		
	} 
	
	
	
	public List<ColetadorEJL> listarColetador() {
        List<ColetadorEJL> coletadores = new ArrayList<>();
        String sql = "SELECT * FROM T_COLETADOR_EJL";
        try (PreparedStatement statement = conexao.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Long idColetador = resultSet.getLong("id_coletador");
                String primeiroNome = resultSet.getString("primeiro_nome_coletador");
                String sobrenome = resultSet.getString("sobrenome_coletador");
                Long qtdColetado = resultSet.getLong("qtd_coletado");

                ColetadorEJL coletadorEJL = new ColetadorEJL();
                coletadorEJL.setIdColetador(idColetador);

                ColetadorEJL coletador = new ColetadorEJL(idColetador, primeiroNome, sobrenome, qtdColetado);
                coletadores.add(coletador);
            }
        } catch (SQLException e) {
            throw new ColetadorException("Erro ao listar coletador: " + e.getMessage(), e);
        } 
			
		
        return coletadores;

    }
	
	
	public void atualizar(ColetadorEJL coletadorEJL) {
        String sql = "UPDATE T_COLETADOR_EJL SET primeiro_nome_coletador = ?, sobrenome_coletador = ?, qtd_coletado = ? WHERE id_coletador = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, coletadorEJL.getPrimeiroNomeColetador());
            stmt.setString(2, coletadorEJL.getSobrenomeColetador());
            stmt.setLong(3, coletadorEJL.getQtdColetado());
            stmt.setLong(4, coletadorEJL.getIdColetador());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new ColetadorException("Coletador não encontrado para atualização");
            }
        } catch (SQLException e) {
            throw new ColetadorException("Erro ao atualizar o coletador: " + e.getMessage(), e);
        } 
    }
	
	
	public void deletar(Long idColetador) {
        String sql = "DELETE FROM T_COLETADOR_EJL WHERE id_coletador = ?";
        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setLong(1, idColetador);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new ColetadorException("Contato coletador não encontrado para exclusão");
            }
        } catch (SQLException e) {
            throw new ColetadorException("Erro ao excluir contato coletador: " + e.getMessage(), e);
        } 
    }
	
	
    public ColetadorEJL buscarPorId(Long idColetador) {
        String sql = "SELECT * FROM T_COLETADOR_EJL WHERE id_coletador = ?";
        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setLong(1, idColetador);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String primeiroNome = resultSet.getString("primeiro_nome_coletador");
                String sobrenome = resultSet.getString("sobrenome_coletador");
                Long qtdColetado = resultSet.getLong("qtd_coletado");

                ColetadorEJL coletadorEJL = new ColetadorEJL();
                coletadorEJL.setIdColetador(idColetador);

                return new ColetadorEJL(idColetador, primeiroNome, sobrenome, qtdColetado);
            } else {
                throw new ColetadorException("Coletador não encontrado");
            }
        } catch (SQLException e) {
            throw new ColetadorException("Erro ao buscar coletador por ID: " + e.getMessage(), e);
        } 
    }

	

}
