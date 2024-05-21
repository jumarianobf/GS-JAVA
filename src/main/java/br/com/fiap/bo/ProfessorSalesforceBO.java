package br.com.fiap.bo;

import br.com.fiap.dao.ProfessorSalesforceDAO;
import br.com.fiap.dao.UsuarioSalesforceDAO;
import br.com.fiap.model.ProfessorSalesforce;

import java.sql.SQLException;
import java.util.List;

public class ProfessorSalesforceBO {

    private ProfessorSalesforceDAO professorSalesforceDAO;

    public ProfessorSalesforceBO() {
        try {
            this.professorSalesforceDAO = new ProfessorSalesforceDAO();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Erro ao inicializar ProfessorSalesforceBO: " + e.getMessage(), e);
        }
    }

    public void salvar(ProfessorSalesforce professor) throws ClassNotFoundException, SQLException {
        validarProfessor(professor);
        if (idJaExiste(professor.getId())) {
            throw new IllegalArgumentException("ID já cadastrado.");
        }
        professorSalesforceDAO.salvar(professor);
    }

    public ProfessorSalesforce buscarPorId(Long id) throws ClassNotFoundException, SQLException {
        return professorSalesforceDAO.buscarPorId(id);
    }

    public void atualizarProfessor(ProfessorSalesforce professor) throws ClassNotFoundException, SQLException {
        validarProfessor(professor);
        ProfessorSalesforce professorExistente = professorSalesforceDAO.buscarPorId(professor.getId());
        if (professorExistente != null && !professorExistente.getId().equals(professor.getId())) {
            throw new IllegalArgumentException("ID não pode ser alterado.");
        }
        professorSalesforceDAO.atualizarProfessor(professor);
    }

    public void excluirProfessor(Long id) throws ClassNotFoundException, SQLException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido.");
        }
        professorSalesforceDAO.excluirProfessor(id);
    }

    public List<ProfessorSalesforce> listarProfessores() throws ClassNotFoundException, SQLException {
        return professorSalesforceDAO.listarProfessor();
    }

    private void validarProfessor(ProfessorSalesforce professor) {
        if (professor.getEspecializacaoProfessor() == null || professor.getEspecializacaoProfessor().trim().isEmpty()) {
            throw new IllegalArgumentException("Especialização do professor não pode ser nula ou vazia.");
        }
        if (professor.getQtdAulas() <= 0) {
            throw new IllegalArgumentException("Quantidade de aulas inválida.");
        }
        if (professor.getQtdMaterias() <= 0) {
            throw new IllegalArgumentException("Quantidade de matérias inválida.");
        }
        if (professor.getDescricaoMaterias() == null || professor.getDescricaoMaterias().trim().isEmpty()) {
            throw new IllegalArgumentException("Descrição das matérias não pode ser nula ou vazia.");
        }
    }
    private boolean idJaExiste(Long id) throws ClassNotFoundException, SQLException {
        List<ProfessorSalesforce> professores = professorSalesforceDAO.listarProfessor();
        for (ProfessorSalesforce professor : professores) {
            if (professor.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }
}
