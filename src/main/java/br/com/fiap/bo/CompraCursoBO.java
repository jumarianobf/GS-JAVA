package br.com.fiap.bo;

import br.com.fiap.dao.CompraCursoDAO;
import br.com.fiap.model.CompraCurso;
import br.com.fiap.model.CursoSalesforce;

import java.sql.SQLException;
import java.util.List;

public class CompraCursoBO {

    private CompraCursoDAO compraCursoDAO;
    private CursoSalesforceBO cursoSalesforceBO;

    public CompraCursoBO() throws ClassNotFoundException, SQLException {
        this.compraCursoDAO = new CompraCursoDAO();
        this.cursoSalesforceBO = new CursoSalesforceBO();
    }

    public void salvar(CompraCurso compraCurso) throws ClassNotFoundException, SQLException {
        validarCompraCurso(compraCurso);
        if (idJaExiste(compraCurso.getIdCompraCurso())) {
            throw new IllegalArgumentException("ID já cadastrado.");
        }
        compraCursoDAO.inserir(compraCurso);
    }

    public CompraCurso buscarPorId(Long id) throws ClassNotFoundException, SQLException {
        return compraCursoDAO.buscarPorId(id);
    }

    public void atualizarCompraCurso(CompraCurso compraCurso) throws ClassNotFoundException, SQLException {
        validarCompraCurso(compraCurso);
        CompraCurso compraCursoExistente = compraCursoDAO.buscarPorId(compraCurso.getIdCompraCurso());
        if (compraCursoExistente != null && !compraCursoExistente.getIdCompraCurso().equals(compraCurso.getIdCompraCurso())) {
            throw new IllegalArgumentException("ID não pode ser alterado.");
        }
        compraCursoDAO.atualizar(compraCurso);
    }

    public void excluirCompraCurso(Long id) throws ClassNotFoundException, SQLException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido.");
        }
        compraCursoDAO.deletar(id);
    }

    public List<CompraCurso> listarCompraCursos() throws ClassNotFoundException, SQLException {
        return compraCursoDAO.listarCompraCursos();
    }


    private void validarCompraCurso(CompraCurso compraCurso) {
        if (compraCurso.getUsuarioSalesforce() == null) {
            throw new IllegalArgumentException("Usuário da compra não pode ser nulo.");
        }
        if (compraCurso.getCursoSalesforce() == null) {
            throw new IllegalArgumentException("Curso da compra não pode ser nulo.");
        }
        if (compraCurso.getFormaPagamento() == null) {
            throw new IllegalArgumentException("Forma de pagamento da compra não pode ser nula.");
        }
        if (compraCurso.getValorCompra() <= 0) {
            throw new IllegalArgumentException("Valor da compra inválido.");
        }
    }

    private boolean idJaExiste(Long id) throws ClassNotFoundException, SQLException {
        List<CompraCurso> compraCursos = compraCursoDAO.listarCompraCursos();
        for (CompraCurso compraCurso : compraCursos) {
            if (compraCurso.getIdCompraCurso().equals(id)) {
                return true;
            }
        }
        return false;
    }
}
