package br.com.fiap.bo;

import br.com.fiap.dao.ContatoConsumidorDAO;
import br.com.fiap.model.ContatoConsumidor;
import br.com.fiap.exception.ColetadorException;

import java.sql.SQLException;
import java.util.List;

public class ContatoConsumidorBO {

	    private ContatoConsumidorDAO contatoConsumidorDAO;

	    public ContatoConsumidorBO() throws ClassNotFoundException {
	        this.contatoConsumidorDAO = new ContatoConsumidorDAO();
	    }

	    public void salvar(ContatoConsumidor contatoConsumidor) throws ClassNotFoundException, SQLException {
	        validarContatoConsumidor(contatoConsumidor);
	        contatoConsumidorDAO.salvar(contatoConsumidor);
	    }

	    public ContatoConsumidor buscarPorId(Long id) throws ClassNotFoundException, SQLException {
	        if (id == null || id <= 0) {
	            throw new IllegalArgumentException("ID inválido.");
	        }
	        return contatoConsumidorDAO.buscarPorId(id);
	    }

	    public void atualizarConsumidor(ContatoConsumidor contatoConsumidor) throws ClassNotFoundException, SQLException {
	    	validarContatoConsumidor(contatoConsumidor);
	    	ContatoConsumidor contatoConsumidorExistente = contatoConsumidorDAO.buscarPorId(contatoConsumidor.getIdContatoConsumidor());
	        if (contatoConsumidorExistente == null) {
	            throw new ColetadorException("Contato do consumidor não encontrado.");
	        }
	        contatoConsumidorDAO.atualizar(contatoConsumidor);
	    }

	    public void excluirContatoConsumidor(Long id) throws ClassNotFoundException, SQLException {
	        if (id == null || id <= 0) {
	            throw new IllegalArgumentException("ID inválido.");
	        }
	        contatoConsumidorDAO.deletar(id);
	    }

	    public List<ContatoConsumidor> listarConsumidor() throws ClassNotFoundException, SQLException {
	        return contatoConsumidorDAO.listarContatos();
	    }

	    private void validarContatoConsumidor(ContatoConsumidor contatoConsumidor) {
	        if (contatoConsumidor.getEmailConsumidor() == null || contatoConsumidor.getEmailConsumidor().trim().isEmpty()) {
	            throw new IllegalArgumentException("E-mail do consumidor não pode ser nulo ou vazio.");
	        }
	        if (contatoConsumidor.getTelConsumidor() == null || contatoConsumidor.getTelConsumidor().trim().isEmpty()) {
	            throw new IllegalArgumentException("Telefone do consumidor não pode ser nulo ou vazio.");
	        }
	    }
	}


