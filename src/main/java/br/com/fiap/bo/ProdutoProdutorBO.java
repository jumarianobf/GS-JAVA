package br.com.fiap.bo;

import br.com.fiap.dao.ProdutoProdutorDAO;
import br.com.fiap.model.ProdutoProdutor;
import br.com.fiap.exception.ColetadorException;

import java.sql.SQLException;
import java.util.List;

public class ProdutoProdutorBO {

	    private ProdutoProdutorDAO produtoProdutorDAO;

	    public ProdutoProdutorBO() throws ClassNotFoundException {
	        this.produtoProdutorDAO = new ProdutoProdutorDAO();
	    }

	    public void salvar(ProdutoProdutor produtoProdutor) throws ClassNotFoundException, SQLException {
	    	validarProdutoProdutor(produtoProdutor);
	    	produtoProdutorDAO.salvar(produtoProdutor);
	    }

	    public ProdutoProdutor buscarPorId(Long id) throws ClassNotFoundException, SQLException {
	        if (id == null || id <= 0) {
	            throw new IllegalArgumentException("ID inválido.");
	        }
	        return produtoProdutorDAO.buscarPorId(id);
	    }

	    public void atualizarProdutoProdutor(ProdutoProdutor produtoProdutor) throws ClassNotFoundException, SQLException {
	    	validarProdutoProdutor(produtoProdutor);
	    	ProdutoProdutor materialColetadoExistente = produtoProdutorDAO.buscarPorId(produtoProdutor.getIdProdutoProdutor());
	        if (materialColetadoExistente == null) {
	            throw new ColetadorException("Contato do Coletador não encontrado.");
	        }
	        produtoProdutorDAO.atualizar(produtoProdutor);
	    }

	    public void excluirProdutoProdutor(Long id) throws ClassNotFoundException, SQLException {
	        if (id == null || id <= 0) {
	            throw new IllegalArgumentException("ID inválido.");
	        }
	        produtoProdutorDAO.deletar(id);
	    }

	    public List<ProdutoProdutor> listarProduto() throws ClassNotFoundException, SQLException {
	        return produtoProdutorDAO.listarProdutos();
	    }

	    private void validarProdutoProdutor(ProdutoProdutor produtoProdutor) {
	        if (produtoProdutor.getNomeProduto() == null || produtoProdutor.getNomeProduto().trim().isEmpty()) {
	            throw new IllegalArgumentException("Nome do produto não pode ser nulo ou vazio.");
	        }
	        if (produtoProdutor.getDescricaoProduto() == null || produtoProdutor.getDescricaoProduto().trim().isEmpty()) {
	            throw new IllegalArgumentException("Descrição do produto não pode ser nulo ou vazio.");
	        }
	        if (produtoProdutor.getPrecoProduto() < 0 || produtoProdutor.getDescricaoProduto().trim().isEmpty()) {
	            throw new IllegalArgumentException("Preço do produto não pode ser negativo ou vazio.");
	        }
	    }
	}


