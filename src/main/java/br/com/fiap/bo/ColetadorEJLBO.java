package br.com.fiap.bo;

import br.com.fiap.dao.ColetadorEJLDAO;
import br.com.fiap.model.ColetadorEJL;
import br.com.fiap.exception.ColetadorException;

import java.sql.SQLException;
import java.util.List;

public class ColetadorEJLBO {

    private ColetadorEJLDAO coletadorEJLDAO;

    public ColetadorEJLBO() throws ClassNotFoundException {
        this.coletadorEJLDAO = new ColetadorEJLDAO();
    }

    public String salvar(ColetadorEJL coletador) throws ClassNotFoundException, SQLException {
        validarColetador(coletador);
        coletadorEJLDAO.salvar(coletador);
        
        return "Coletador cadastrado com sucesso!";
        
    }
    public ColetadorEJL buscarPorId(Long id) throws ClassNotFoundException, SQLException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido.");
        }
        return coletadorEJLDAO.buscarPorId(id);
    }

    public void atualizarColetador(ColetadorEJL coletador) throws ClassNotFoundException, SQLException {
        validarColetador(coletador);
        ColetadorEJL coletadorExistente = coletadorEJLDAO.buscarPorId(coletador.getIdColetador());
        if (coletadorExistente == null) {
            throw new ColetadorException("Coletador não encontrado.");
        }
        coletadorEJLDAO.atualizar(coletador);
    }

    public void excluirColetador(Long id) throws ClassNotFoundException, SQLException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido.");
        }
        coletadorEJLDAO.deletar(id);
    }

    public List<ColetadorEJL> listarColetadores() throws ClassNotFoundException, SQLException {
        return coletadorEJLDAO.listarColetador();
    }

    private void validarColetador(ColetadorEJL coletador) {
        if (coletador.getPrimeiroNomeColetador() == null || coletador.getPrimeiroNomeColetador().trim().isEmpty()) {
            throw new IllegalArgumentException("Primeiro nome do coletador não pode ser nulo ou vazio.");
        }
        if (coletador.getSobrenomeColetador() == null || coletador.getSobrenomeColetador().trim().isEmpty()) {
            throw new IllegalArgumentException("Sobrenome do coletador não pode ser nulo ou vazio.");
        }
        if (coletador.getQtdColetado() < 0) {
            throw new IllegalArgumentException("Quantidade nao pode ser negativa.");
        }
        
        
    }
}
