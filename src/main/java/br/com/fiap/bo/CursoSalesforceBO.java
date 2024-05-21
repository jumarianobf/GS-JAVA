package br.com.fiap.bo;

import br.com.fiap.dao.CursoSalesforceDAO;
import br.com.fiap.dao.UsuarioSalesforceDAO;
import br.com.fiap.model.CursoSalesforce;

import java.sql.SQLException;
import java.util.List;

public class CursoSalesforceBO {

    private CursoSalesforceDAO cursoSalesforceDAO;

    public CursoSalesforceBO() {
        try {
            this.cursoSalesforceDAO = new CursoSalesforceDAO();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Erro ao inicializar CursoSalesforceBO: " + e.getMessage(), e);
        }
    }
    public void salvar(CursoSalesforce curso) throws ClassNotFoundException, SQLException {
        validarCurso(curso);
        if (idJaExiste(curso.getId())) {
            throw new IllegalArgumentException("ID já cadastrado.");
        }
        cursoSalesforceDAO.salvar(curso);
    }

    public CursoSalesforce buscarPorId(Long id) throws ClassNotFoundException, SQLException {
        return cursoSalesforceDAO.buscarPorId(id);
    }

    public void atualizarCurso(CursoSalesforce curso) throws ClassNotFoundException, SQLException {
        validarCurso(curso);
        CursoSalesforce cursoExistente = cursoSalesforceDAO.buscarPorId(curso.getId());
        if (cursoExistente != null && !cursoExistente.getId().equals(curso.getId())) {
            throw new IllegalArgumentException("ID não pode ser alterado.");
        }
        cursoSalesforceDAO.atualizarCurso(curso);
    }

    public void excluirCurso(Long id) throws ClassNotFoundException, SQLException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido.");
        }
        cursoSalesforceDAO.excluirCurso(id);
    }

    public List<CursoSalesforce> listarCursos() throws ClassNotFoundException, SQLException {
        return cursoSalesforceDAO.listarCursos();
    }
    

    private void validarCurso(CursoSalesforce curso) {
        if (curso.getNomeCurso() == null || curso.getNomeCurso().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do curso não pode ser nulo ou vazio.");
        }
        if (curso.getAreaCurso() == null || curso.getAreaCurso().trim().isEmpty()) {
            throw new IllegalArgumentException("Área do curso não pode ser nula ou vazia.");
        }
        
    }

    private boolean idJaExiste(Long id) throws ClassNotFoundException, SQLException {
        List<CursoSalesforce> cursos = cursoSalesforceDAO.listarCursos();
        for (CursoSalesforce curso : cursos) {
            if (curso.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }


}

