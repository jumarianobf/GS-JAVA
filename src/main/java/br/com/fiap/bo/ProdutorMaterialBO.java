package br.com.fiap.bo;

import br.com.fiap.dao.ProdutorMaterialDAO;
import br.com.fiap.model.ProdutorMaterial;
import br.com.fiap.exception.ColetadorException;

import java.sql.SQLException;
import java.util.List;

public class ProdutorMaterialBO {

	    private ProdutorMaterialDAO produtorMaterialDAO;

	    public ProdutorMaterialBO() throws ClassNotFoundException {
	        this.produtorMaterialDAO = new ProdutorMaterialDAO();
	    }

	    public void salvar(ProdutorMaterial produtorMaterial) throws ClassNotFoundException, SQLException {
	    	validarProdutorMaterial(produtorMaterial);
	    	produtorMaterialDAO.salvar(produtorMaterial);
	    }

	    public ProdutorMaterial buscarPorId(Long id) throws ClassNotFoundException, SQLException {
	        if (id == null || id <= 0) {
	            throw new IllegalArgumentException("ID inválido.");
	        }
	        return produtorMaterialDAO.buscarPorId(id);
	    }

	    public void atualizarProdutoProdutor(ProdutorMaterial produtorMaterial) throws ClassNotFoundException, SQLException {
	    	validarProdutorMaterial(produtorMaterial);
	    	ProdutorMaterial ProdutorMaterialExistente = produtorMaterialDAO.buscarPorId(produtorMaterial.getIdProdutor());
	        if (ProdutorMaterialExistente == null) {
	            throw new ColetadorException("Contato do Coletador não encontrado.");
	        }
	        produtorMaterialDAO.atualizar(produtorMaterial);
	    }

	    public void excluirProdutoProdutor(Long id) throws ClassNotFoundException, SQLException {
	        if (id == null || id <= 0) {
	            throw new IllegalArgumentException("ID inválido.");
	        }
	        produtorMaterialDAO.deletar(id);
	    }

	    public List<ProdutorMaterial> listarProdutor() throws ClassNotFoundException, SQLException {
	        return produtorMaterialDAO.listarProdutores();
	    }

	    private void validarProdutorMaterial(ProdutorMaterial produtorMaterial) {
	        if (produtorMaterial.getNomeEmpresaProdutor() == null || produtorMaterial.getNomeEmpresaProdutor().trim().isEmpty()) {
	            throw new IllegalArgumentException("Nome da empresa do produtor não pode ser nulo ou vazio.");
	        }
	        if (produtorMaterial.getTipoProdutor() == null || produtorMaterial.getTipoProdutor().trim().isEmpty()) {
	            throw new IllegalArgumentException("Tipo produtor não pode ser nulo ou vazio.");
	        }
	    }
	}


