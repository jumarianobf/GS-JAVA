package br.com.fiap.bo;

import br.com.fiap.dao.ConsumidorProdutoDAO;
import br.com.fiap.model.ConsumidorProduto;
import br.com.fiap.exception.ColetadorException;

import java.sql.SQLException;
import java.util.List;

public class ConsumidorProdutoBO {

	    private ConsumidorProdutoDAO consumidorProdutoDAO;

	    public ConsumidorProdutoBO() throws ClassNotFoundException {
	        this.consumidorProdutoDAO = new ConsumidorProdutoDAO();
	    }

	    public void salvar(ConsumidorProduto consumidorProduto) throws ClassNotFoundException, SQLException {
	        validarConsumidorProduto(consumidorProduto);
	        consumidorProdutoDAO.salvar(consumidorProduto);
	    }

	    public ConsumidorProduto buscarPorId(Long id) throws ClassNotFoundException, SQLException {
	        if (id == null || id <= 0) {
	            throw new IllegalArgumentException("ID inválido.");
	        }
	        return consumidorProdutoDAO.buscarPorId(id);
	    }

	    public void atualizarConsumidorProduto(ConsumidorProduto consumidorProduto) throws ClassNotFoundException, SQLException {
	    	validarConsumidorProduto(consumidorProduto);
	    	ConsumidorProduto consumidorProdutoExistente = consumidorProdutoDAO.buscarPorId(consumidorProduto.getIdConsumidorProduto());
	        if (consumidorProdutoExistente == null) {
	            throw new ColetadorException("Consumidor não encontrado.");
	        }
	        consumidorProdutoDAO.atualizar(consumidorProduto);
	    }

	    public void excluirConsumidor(Long id) throws ClassNotFoundException, SQLException {
	        if (id == null || id <= 0) {
	            throw new IllegalArgumentException("ID inválido.");
	        }
	        consumidorProdutoDAO.deletar(id);
	    }

	    public List<ConsumidorProduto> listarConsumidor() throws ClassNotFoundException, SQLException {
	        return  consumidorProdutoDAO.listarConsumidores();
	    }

	    private void validarConsumidorProduto(ConsumidorProduto consumidorProduto) {
	        if (consumidorProduto.getPrimeiroNomeConsumidor() == null || consumidorProduto.getPrimeiroNomeConsumidor().trim().isEmpty()) {
	            throw new IllegalArgumentException("E-mail do consumidor não pode ser nulo ou vazio.");
	        }
	        if (consumidorProduto.getSobrenomeConsumidor() == null || consumidorProduto.getSobrenomeConsumidor().trim().isEmpty()) {
	            throw new IllegalArgumentException("Sobrenome do consumidor não pode ser nulo ou vazio.");
	        }
	        if (consumidorProduto.getCpfConsumidor() == null || consumidorProduto.getCpfConsumidor().trim().isEmpty()) {
	            throw new IllegalArgumentException("CPF do consumidor não pode ser nulo ou vazio.");
	        }
	    }
	}


