package br.com.fiap.dao;



import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import br.com.fiap.connection.ConnectionFactory;
import br.com.fiap.model.ProfessorSalesforce;

public class ProfessorSalesforceDAO {

    private Connection conexao;

    public ProfessorSalesforceDAO() throws ClassNotFoundException, SQLException {
        this.conexao = new ConnectionFactory().conexao();
    }

    public String salvar(ProfessorSalesforce professor) {
        String sql = "INSERT INTO T_PROFESSOR_SALESFORCE "
                + "(especializacao_professor, qtd_aulas, qtd_materias, descricao_materias) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, professor.getEspecializacaoProfessor());
            stmt.setInt(2, professor.getQtdAulas());
            stmt.setInt(3, professor.getQtdMaterias());
            stmt.setString(4, professor.getDescricaoMaterias());
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        long id = generatedKeys.getLong(1);
                        professor.setId(id);
                    } else {
                        throw new SQLException("Falha ao obter o ID gerado.");
                    }
                }
            } else {
                throw new SQLException("Falha ao cadastrar o usuário.");
            }
            return "Cadastrado com sucesso!";
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar usuário: " + e.getMessage(), e);
        }
    }

    public ProfessorSalesforce buscarPorId(Long id) {
        try (PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM T_PROFESSOR_SALESFORCE WHERE id_professor = ?")) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            ProfessorSalesforce professor = null;
            if (rs.next()) {
                professor = new ProfessorSalesforce();
                professor.setId(rs.getLong("id_professor"));
                professor.setEspecializacaoProfessor(rs.getString("especializacao_professor"));
                professor.setQtdAulas(rs.getInt("qtd_aulas"));
                professor.setQtdMaterias(rs.getInt("qtd_materias"));
                professor.setDescricaoMaterias(rs.getString("descricao_materias"));
            }
            rs.close();
            return professor;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar professor por ID: " + e.getMessage(), e);
        }
    }

    public String atualizarProfessor(ProfessorSalesforce professor) {
        try (PreparedStatement stmt = conexao.prepareStatement("UPDATE T_PROFESSOR_SALESFORCE SET especializacao_professor = ?, "
                + "qtd_aulas = ?, qtd_materias = ?, descricao_materias = ? WHERE id_professor = ?")) {
            stmt.setString(1, professor.getEspecializacaoProfessor());
            stmt.setInt(2, professor.getQtdAulas());
            stmt.setInt(3, professor.getQtdMaterias());
            stmt.setString(4, professor.getDescricaoMaterias());
            stmt.setLong(5, professor.getId());
            stmt.executeUpdate();
            return "Atualizado com sucesso!";
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar professor: " + e.getMessage(), e);
        }
    }

    public String excluirProfessor(Long id) {
        try (PreparedStatement stmt = conexao.prepareStatement("DELETE FROM T_PROFESSOR_SALESFORCE WHERE id_professor = ?")) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
            return "Deletado com sucesso";
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir professor: " + e.getMessage(), e);
        }
    }

    public List<ProfessorSalesforce> listarProfessor() {
        List<ProfessorSalesforce> professores = new ArrayList<>();
        try (PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM T_PROFESSOR_SALESFORCE")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ProfessorSalesforce professor = new ProfessorSalesforce();
                professor.setId(rs.getLong("id_professor"));
                professor.setEspecializacaoProfessor(rs.getString("especializacao_professor"));
                professor.setQtdAulas(rs.getInt("qtd_aulas"));
                professor.setQtdMaterias(rs.getInt("qtd_materias"));
                professor.setDescricaoMaterias(rs.getString("descricao_materias"));
                professores.add(professor);
            }
            return professores;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar professores: " + e.getMessage(), e);
        }
    }
}


