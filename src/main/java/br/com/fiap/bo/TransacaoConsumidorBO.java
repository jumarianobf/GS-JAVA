package br.com.fiap.bo;

import br.com.fiap.dao.TransacaoConsumidorDAO;
import br.com.fiap.model.TransacaoConsumidor;
import br.com.fiap.exception.ColetadorException;

import java.sql.SQLException;
import java.util.List;

public class TransacaoConsumidorBO {

	    private TransacaoConsumidorDAO transacaoConsumidorDAO;

	    public TransacaoConsumidorBO() throws ClassNotFoundException {
	        this.transacaoConsumidorDAO = new TransacaoConsumidorDAO();
	    }

	    public void salvar(TransacaoConsumidor transacaoConsumidor) throws ClassNotFoundException, SQLException {
	    	validartTransacaoConsumidor(transacaoConsumidor);
	    	transacaoConsumidorDAO.salvar(transacaoConsumidor);
	    }

	    public TransacaoConsumidor buscarPorId(Long id) throws ClassNotFoundException, SQLException {
	        if (id == null || id <= 0) {
	            throw new IllegalArgumentException("ID inválido.");
	        }
	        return transacaoConsumidorDAO.buscarPorId(id);
	    }

	    public void atualizarTransacaoConsumidor(TransacaoConsumidor transacaoConsumidor) throws ClassNotFoundException, SQLException {
	    	validartTransacaoConsumidor(transacaoConsumidor);
	    	TransacaoConsumidor transacaoConsumidorExistente = transacaoConsumidorDAO.buscarPorId(transacaoConsumidor.getIdTransacaoConsumidor());
	        if (transacaoConsumidorExistente == null) {
	            throw new ColetadorException("Contato do Coletador não encontrado.");
	        }
	        transacaoConsumidorDAO.atualizar(transacaoConsumidor);
	    }

	    public void excluirTransacaoConsumidor(Long id) throws ClassNotFoundException, SQLException {
	        if (id == null || id <= 0) {
	            throw new IllegalArgumentException("ID inválido.");
	        }
	        transacaoConsumidorDAO.deletar(id);
	    }

	    public List<TransacaoConsumidor> listarTransacao() throws ClassNotFoundException, SQLException {
	        return transacaoConsumidorDAO.listarTransacoes();
	    }

	    private void validartTransacaoConsumidor(TransacaoConsumidor transacaoConsumidor) {
	        if (transacaoConsumidor.getDataTransicao() == null) {
	            throw new IllegalArgumentException("A Data não pode ser nula.");
	        }
	        if (transacaoConsumidor.getQtdTransacaoConsumidor() < 0) {
	            throw new IllegalArgumentException("Quantidade de transação não pode ser negativa.");
	        }
	        if (transacaoConsumidor.getValorTotalTransacao() < 0) {
	            throw new IllegalArgumentException("Valor total da transação não pode ser negativa.");
	        }
	    }
	}


