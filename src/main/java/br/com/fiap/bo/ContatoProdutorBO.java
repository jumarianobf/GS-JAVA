package br.com.fiap.bo;

import br.com.fiap.dao.ContatoProdutorDAO;
import br.com.fiap.model.ContatoProdutor;
import br.com.fiap.exception.ColetadorException;

import java.sql.SQLException;
import java.util.List;

public class ContatoProdutorBO {

	    private ContatoProdutorDAO contatoProdutorDAO;

	    public ContatoProdutorBO() throws ClassNotFoundException {
	        this.contatoProdutorDAO = new ContatoProdutorDAO();
	    }

	    public void salvar(ContatoProdutor contatoProdutor) throws ClassNotFoundException, SQLException {
	        validarContatoProdutor(contatoProdutor);
	        contatoProdutorDAO.salvar(contatoProdutor);
	    }

	    public ContatoProdutor buscarPorId(Long id) throws ClassNotFoundException, SQLException {
	        if (id == null || id <= 0) {
	            throw new IllegalArgumentException("ID inválido.");
	        }
	        return contatoProdutorDAO.buscarPorId(id);
	    }

	    public void atualizarProdutor(ContatoProdutor contatoProdutor) throws ClassNotFoundException, SQLException {
	    	validarContatoProdutor(contatoProdutor);
	    	ContatoProdutor contatoProdutorExistente = contatoProdutorDAO.buscarPorId(contatoProdutor.getIdContatoProdutor());
	        if (contatoProdutorExistente == null) {
	            throw new ColetadorException("Contato do produtor não encontrado.");
	        }
	        contatoProdutorDAO.atualizar(contatoProdutor);
	    }

	    public void excluirContatoProdutor(Long id) throws ClassNotFoundException, SQLException {
	        if (id == null || id <= 0) {
	            throw new IllegalArgumentException("ID inválido.");
	        }
	        contatoProdutorDAO.deletar(id);
	    }

	    public List<ContatoProdutor> listarProdutor() throws ClassNotFoundException, SQLException {
	        return  contatoProdutorDAO.listarContatos();
	    }

	    private void validarContatoProdutor(ContatoProdutor contatoProdutor) {
	        if (contatoProdutor.getEmailProdutor() == null || contatoProdutor.getEmailProdutor().trim().isEmpty()) {
	            throw new IllegalArgumentException("E-mail do produtor não pode ser nulo ou vazio.");
	        }
	        if (contatoProdutor.getTelProdutor() == null || contatoProdutor.getTelProdutor().trim().isEmpty()) {
	            throw new IllegalArgumentException("Sobrenome do produtor não pode ser nulo ou vazio.");
	        }
	    }
	}


