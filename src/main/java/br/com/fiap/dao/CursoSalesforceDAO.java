package br.com.fiap.dao;

import br.com.fiap.connection.ConnectionFactory;
import br.com.fiap.model.CursoSalesforce;

import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CursoSalesforceDAO {

    public Connection conexao;

    public CursoSalesforceDAO() throws ClassNotFoundException, SQLException {
        this.conexao = new ConnectionFactory().conexao();
    }

    //Insert
    public String salvar(CursoSalesforce curso) {
        String sql = "INSERT INTO T_CURSO_SALESFORCE (nome_curso, area_curso, certificado_curso) VALUES (?, ?, ?)";

        try (PreparedStatement statement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, curso.getNomeCurso());
            statement.setString(2, curso.getAreaCurso());
            statement.setString(3, curso.getCertificadoCurso());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                return "Falha ao cadastrar curso, nenhuma linha afetada.";
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    curso.setId(generatedKeys.getLong(1));
                    return "Curso cadastrado com sucesso!";
                } else {
                    return "Falha ao cadastrar curso, nenhum ID obtido.";
                }
            }
        } catch (SQLException e) {
            return "Erro ao salvar curso: " + e.getMessage();
        } finally {
            fecharConexao();
        }
    }

	
	//Select id
	public CursoSalesforce buscarPorId(Long id) {
	    if (id == null) {
	        return null;
	    }
	    CursoSalesforce curso = new CursoSalesforce();
	    try (PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM T_CURSO_SALESFORCE WHERE id_curso = ?")) {
	        stmt.setLong(1, id);
	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                curso.setId(rs.getLong("id_curso"));
	                curso.setNomeCurso(rs.getString("nome_curso"));
	                curso.setAreaCurso(rs.getString("area_curso"));
	                curso.setCertificadoCurso(rs.getString("certificado_curso"));
	            }
	        }
	    } catch (SQLException e) {
	    	throw new RuntimeException("Erro ao buscar por ID", e);
		}

	    return curso;
	}

	//Update
	public void atualizarCurso(CursoSalesforce curso) {
	    try (PreparedStatement stmt = conexao.prepareStatement(
	                 "UPDATE T_CURSO_SALESFORCE SET nome_curso = ?, area_curso = ?, certificado_curso = ? WHERE id_curso = ?")) {
	        stmt.setString(1, curso.getNomeCurso());
	        stmt.setString(2, curso.getAreaCurso());
	        stmt.setString(3, curso.getCertificadoCurso());
	        stmt.setLong(4, curso.getId());
	        stmt.executeUpdate();
	    } catch (SQLException e) {
	        throw new RuntimeException("Erro ao atualizar T_CURSO_SALESFORCE", e);
	    }
	}

	//Delete
	public void excluirCurso(Long id) {
	    try (PreparedStatement stmt = conexao.prepareStatement("DELETE FROM T_CURSO_SALESFORCE WHERE id_curso = ?")) {
	        stmt.setLong(1, id);
	        stmt.executeUpdate();
	    } catch (SQLException e) {
	        throw new RuntimeException("Erro ao excluir T_CURSO_SALESFORCE", e);
	    }
	}

	//Select
	public List<CursoSalesforce> listarCursos() {
	    List<CursoSalesforce> cursos = new ArrayList<>();
	    try (PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM T_CURSO_SALESFORCE");
	         ResultSet rs = stmt.executeQuery()) {
	        while (rs.next()) {
	            CursoSalesforce curso = new CursoSalesforce();
	            curso.setId(rs.getLong("id_curso"));
	            curso.setNomeCurso(rs.getString("nome_curso"));
	            curso.setAreaCurso(rs.getString("area_curso"));
	            curso.setCertificadoCurso(rs.getString("certificado_curso"));
	            cursos.add(curso);
	        }
	    } catch (SQLException e) {
	        throw new RuntimeException("Erro ao listar T_CURSO_SALESFORCE", e);
	    }
	    return cursos;
	}
	
	public List<CursoSalesforce> buscarPorNome(String nome) throws SQLException {
	    List<CursoSalesforce> cursos = new ArrayList<>();
	    PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM T_CURSO_SALESFORCE WHERE nome_curso LIKE ?");
	    stmt.setString(1, "%" + nome + "%");
	    ResultSet rs = stmt.executeQuery();
	    while (rs.next()) {
	         CursoSalesforce curso = new CursoSalesforce();
	         curso.setId(rs.getLong("id_curso"));
	         curso.setNomeCurso(rs.getString("nome_curso"));
	         curso.setAreaCurso(rs.getString("area_curso"));
	         curso.setCertificadoCurso(rs.getString("certificado_curso"));
	         cursos.add(curso);
	    }
	    rs.close();
	    stmt.close();
	    return cursos;
	}
	
	public int contarCursos() throws SQLException{
		PreparedStatement stmt = conexao.prepareStatement("SELECT COUNT(*) FROM T_CURSO_SALESFORCE");
	    ResultSet rs = stmt.executeQuery();
	    if (rs.next()) {
	            return rs.getInt(1);
	        }
	    rs.close();
	    stmt.close();
	    return 0;
	}
	
	public List<CursoSalesforce> buscarPorArea(String area) throws SQLException {
	    List<CursoSalesforce> cursos = new ArrayList<>();
	    PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM T_CURSO_SALESFORCE WHERE area_curso = ?");
	    stmt.setString(1, area);
	    ResultSet rs = stmt.executeQuery();
	    while (rs.next()) {
	    	CursoSalesforce curso = new CursoSalesforce();
	        curso.setId(rs.getLong("id_curso")); 
	        curso.setNomeCurso(rs.getString("nome_curso"));
	        curso.setAreaCurso(rs.getString("area_curso"));
	        curso.setCertificadoCurso(rs.getString("certificado_curso"));
	        cursos.add(curso);
	     }
	    rs.close();
	    stmt.close();
	    return cursos;
	 }
	
	public List<CursoSalesforce> buscarPorCertificado(String certificado) throws SQLException {
	    List<CursoSalesforce> cursos = new ArrayList<>();
	    PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM T_CURSO_SALESFORCE WHERE certificado_curso = ?");
	    stmt.setString(1, certificado);
	    ResultSet rs = stmt.executeQuery();
	    while (rs.next()) {
	    	CursoSalesforce curso = new CursoSalesforce();
	        curso.setId(rs.getLong("id_curso"));
	        curso.setNomeCurso(rs.getString("nome_curso"));
	        curso.setAreaCurso(rs.getString("area_curso"));
	        curso.setCertificadoCurso(rs.getString("certificado_curso"));
	        cursos.add(curso);
	    }
	    rs.close();
	    stmt.close();
	    return cursos;
	}
	
	private void fecharConexao() {
        try {
            if (conexao != null) {
                conexao.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	
}

