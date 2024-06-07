package br.com.fiap.bo;

import br.com.fiap.dao.ContatoColetadorDAO;
import br.com.fiap.model.ContatoColetador;
import br.com.fiap.exception.ColetadorException;

import java.sql.SQLException;
import java.util.List;

public class ContatoColetadorBO {

	    private ContatoColetadorDAO contatoColetadorDAO;

	    public ContatoColetadorBO() throws ClassNotFoundException {
	        this.contatoColetadorDAO = new ContatoColetadorDAO();
	    }

	    public void salvar(ContatoColetador contatoColetador) throws ClassNotFoundException, SQLException {
	        validarContatoColetador(contatoColetador);
	        contatoColetadorDAO.salvar(contatoColetador);
	    }

	    public ContatoColetador buscarPorId(Long id) throws ClassNotFoundException, SQLException {
	        if (id == null || id <= 0) {
	            throw new IllegalArgumentException("ID inválido.");
	        }
	        return contatoColetadorDAO.buscarPorId(id);
	    }

	    public void atualizarContato(ContatoColetador contatoColetador) throws ClassNotFoundException, SQLException {
	    	validarContatoColetador(contatoColetador);
	    	ContatoColetador contatoColetadorExistente = contatoColetadorDAO.buscarPorId(contatoColetador.getIdContatoColetador());
	        if (contatoColetadorExistente == null) {
	            throw new ColetadorException("Contato do Coletador não encontrado.");
	        }
	        contatoColetadorDAO.atualizar(contatoColetador);
	    }

	    public void excluirContatoColetador(Long id) throws ClassNotFoundException, SQLException {
	        if (id == null || id <= 0) {
	            throw new IllegalArgumentException("ID inválido.");
	        }
	        contatoColetadorDAO.deletar(id);
	    }

	    public List<ContatoColetador> listarContato() throws ClassNotFoundException, SQLException {
	        return contatoColetadorDAO.listarContatos();
	    }

	    private void validarContatoColetador(ContatoColetador contatoColetador) {
	        if (contatoColetador.getEmailColetador() == null || contatoColetador.getEmailColetador().trim().isEmpty()) {
	            throw new IllegalArgumentException("E-mail do coletador não pode ser nulo ou vazio.");
	        }
	        if (contatoColetador.getTelColetador() == null || contatoColetador.getTelColetador().trim().isEmpty()) {
	            throw new IllegalArgumentException("Sobrenome do coletador não pode ser nulo ou vazio.");
	        }
	    }
	}


