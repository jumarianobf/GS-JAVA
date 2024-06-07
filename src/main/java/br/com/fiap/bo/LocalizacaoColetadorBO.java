package br.com.fiap.bo;

import br.com.fiap.dao.LocalizacaoColetadorDAO;
import br.com.fiap.model.LocalizacaoColetador;
import br.com.fiap.service.CoordenadaService;
import br.com.fiap.service.ViaCepService;
import br.com.fiap.exception.ColetadorException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class LocalizacaoColetadorBO {

	    private LocalizacaoColetadorDAO localizacaoColetadorDAO;


	    public LocalizacaoColetadorBO() throws ClassNotFoundException {
	        this.localizacaoColetadorDAO = new LocalizacaoColetadorDAO();

	    }

	    public void salvar(LocalizacaoColetador localizacaoColetador) throws ClassNotFoundException, SQLException, IOException {
	    	validarLocalizacaoColetador(localizacaoColetador);

	    	 String coordenadas = CoordenadaService.obterCoordenadas(localizacaoColetador.getRuaColetador() + ", " + localizacaoColetador.getCepColetador());
	         localizacaoColetador.setCoordenadas(coordenadas);
	         
	         localizacaoColetadorDAO.salvar(localizacaoColetador);
	    }

	    public LocalizacaoColetador buscarPorId(Long id) throws ClassNotFoundException, SQLException {
	        if (id == null || id <= 0) {
	            throw new IllegalArgumentException("ID inválido.");
	        }
	        return localizacaoColetadorDAO.buscarPorId(id);
	    }

	    public void atualizarContato(LocalizacaoColetador localizacaoColetador) throws ClassNotFoundException, SQLException, IOException {
	    	validarLocalizacaoColetador(localizacaoColetador);
	    	LocalizacaoColetador contatoColetadorExistente = localizacaoColetadorDAO.buscarPorId(localizacaoColetador.getIdLocalizacaoColetador());
	        if (contatoColetadorExistente == null) {
	            throw new ColetadorException("Localizacao do Coletador não encontrado.");
	        }
	        
	        String coordenadas = CoordenadaService.obterCoordenadas(localizacaoColetador.getRuaColetador() + ", " + localizacaoColetador.getCepColetador());
	        localizacaoColetador.setCoordenadas(coordenadas);
	        
	        localizacaoColetadorDAO.atualizar(localizacaoColetador);
	    }

	    public void excluirContatoColetador(Long id) throws ClassNotFoundException, SQLException {
	        if (id == null || id <= 0) {
	            throw new IllegalArgumentException("ID inválido.");
	        }
	        localizacaoColetadorDAO.deletar(id);
	    }

	    public List<LocalizacaoColetador> listarLocalizacao() throws ClassNotFoundException, SQLException {
	        return localizacaoColetadorDAO.listarLocalizacao();
	    }

	    private void validarLocalizacaoColetador(LocalizacaoColetador contatoColetador) {
	        if (contatoColetador.getCepColetador() == null || contatoColetador.getCepColetador().trim().isEmpty()) {
	            throw new IllegalArgumentException("CEP do coletador não pode ser nulo ou vazio.");
	        }
	        if (contatoColetador.getRuaColetador() == null || contatoColetador.getRuaColetador().trim().isEmpty()) {
	            throw new IllegalArgumentException("Rua do coletador não pode ser nulo ou vazio.");
	        }
	        if (contatoColetador.getPraiaColetador() == null || contatoColetador.getPraiaColetador().trim().isEmpty()) {
	            throw new IllegalArgumentException("Praia do coletador não pode ser nulo ou vazio.");
	        }
	    }
	}


