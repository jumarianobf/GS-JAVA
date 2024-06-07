package br.com.fiap.bo;

import br.com.fiap.dao.MaterialColetadoDAO;
import br.com.fiap.model.MaterialColetado;
import br.com.fiap.exception.ColetadorException;

import java.sql.SQLException;
import java.util.List;

public class MaterialColetadoBO {

	    private MaterialColetadoDAO materialColetadoDAO;

	    public MaterialColetadoBO() throws ClassNotFoundException {
	        this.materialColetadoDAO = new MaterialColetadoDAO();
	    }

	    public void salvar(MaterialColetado materialColetado) throws ClassNotFoundException, SQLException {
	    	validarMaterialColetado(materialColetado);
	        materialColetadoDAO.salvar(materialColetado);
	    }

	    public MaterialColetado buscarPorId(Long id) throws ClassNotFoundException, SQLException {
	        if (id == null || id <= 0) {
	            throw new IllegalArgumentException("ID inválido.");
	        }
	        return materialColetadoDAO.buscarPorId(id);
	    }

	    public void atualizarMaterialColetado(MaterialColetado materialColetado) throws ClassNotFoundException, SQLException {
	    	validarMaterialColetado(materialColetado);
	    	MaterialColetado materialColetadoExistente = materialColetadoDAO.buscarPorId(materialColetado.getIdMaterial());
	        if (materialColetadoExistente == null) {
	            throw new ColetadorException("Contato do Coletador não encontrado.");
	        }
	        materialColetadoDAO.atualizar(materialColetado);
	    }

	    public void excluirMaterialColetado(Long id) throws ClassNotFoundException, SQLException {
	        if (id == null || id <= 0) {
	            throw new IllegalArgumentException("ID inválido.");
	        }
	        materialColetadoDAO.deletar(id);
	    }

	    public List<MaterialColetado> listarMaterialColetado() throws ClassNotFoundException, SQLException {
	        return materialColetadoDAO.listarMaterial();
	    }

	    private void validarMaterialColetado(MaterialColetado materialColetado) {
	        if (materialColetado.getTipoMaterial() == null || materialColetado.getTipoMaterial().trim().isEmpty()) {
	            throw new IllegalArgumentException("O Tipo do material não pode ser nulo ou vazio.");
	        }
	        if (materialColetado.getQtdMaterial() < 0) {
	            throw new IllegalArgumentException("Quantidade de material não pode ser negativo.");
	        }
	    }
	}


