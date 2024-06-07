package br.com.fiap.bo;

import br.com.fiap.dao.LocalizacaoConsumidorDAO;
import br.com.fiap.model.LocalizacaoColetador;
import br.com.fiap.model.LocalizacaoConsumidor;
import br.com.fiap.service.ViaCepService;
import br.com.fiap.exception.ColetadorException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

public class LocalizacaoConsumidorBO {

	    private LocalizacaoConsumidorDAO localizacaoConsumidorDAO;
	    private ViaCepService viaCepService;

	    public LocalizacaoConsumidorBO() throws ClassNotFoundException {
	        this.localizacaoConsumidorDAO = new LocalizacaoConsumidorDAO();
	        this.viaCepService = new ViaCepService();
	    }

	    public void salvar(LocalizacaoConsumidor localizacaoConsumidor) throws ClassNotFoundException, SQLException, ClientProtocolException, IOException {
	    	validarLocalizacaoConsumidor(localizacaoConsumidor);
	    	
	    	LocalizacaoColetador endereco = viaCepService.getLocalizacaoColetador(LocalizacaoColetador.getCepColetador());
	        if (endereco != null) {
	            endereco.setRuaColetador(endereco.getRuaColetador());

	        }
	    	localizacaoConsumidorDAO.salvar(localizacaoConsumidor);
	    	
	    }

	    public LocalizacaoConsumidor buscarPorId(Long id) throws ClassNotFoundException, SQLException {
	        if (id == null || id <= 0) {
	            throw new IllegalArgumentException("ID inválido.");
	        }
	        return localizacaoConsumidorDAO.buscarPorId(id);
	    }

	    public void atualizarLocalizacao(LocalizacaoConsumidor localizacaoConsumidor) throws ClassNotFoundException, SQLException, ClientProtocolException, IOException {
	    	validarLocalizacaoConsumidor(localizacaoConsumidor);
	    	LocalizacaoConsumidor LocalizacaoConsumidorExistente = localizacaoConsumidorDAO.buscarPorId(localizacaoConsumidor.getIdLocalizacaoConsumidor());
	        if (LocalizacaoConsumidorExistente == null) {
	            throw new ColetadorException("Localizacao do consumidor não encontrado.");
	        }
	        LocalizacaoColetador endereco = viaCepService.getLocalizacaoColetador(LocalizacaoColetador.getCepColetador());
	        if (endereco != null) {
	        	endereco.setRuaColetador(endereco.getRuaColetador());

	        }
	        
	        localizacaoConsumidorDAO.atualizar(localizacaoConsumidor);
	    }

	    public void excluirContatoColetador(Long id) throws ClassNotFoundException, SQLException {
	        if (id == null || id <= 0) {
	            throw new IllegalArgumentException("ID inválido.");
	        }
	        localizacaoConsumidorDAO.deletar(id);
	    }

	    public List<LocalizacaoConsumidor> listarLocalizacao() throws ClassNotFoundException, SQLException {
	        return localizacaoConsumidorDAO.listar();
	    }

	    private void validarLocalizacaoConsumidor(LocalizacaoConsumidor localizacaoConsumidor) {
	        if (localizacaoConsumidor.getLogradouroConsumidor() == null || localizacaoConsumidor.getLogradouroConsumidor().trim().isEmpty()) {
	            throw new IllegalArgumentException("Logradouro não pode ser nulo ou vazio.");
	        }
	        if (localizacaoConsumidor.getBairroResidenciaConsumidor() == null || localizacaoConsumidor.getBairroResidenciaConsumidor().trim().isEmpty()) {
	            throw new IllegalArgumentException("Bairro não pode ser nulo ou vazio.");
	        }
	        if (localizacaoConsumidor.getNumeroResidenciaConsumidor() < 0) {
	            throw new IllegalArgumentException("Numero residencial não pode ser negativo.");
	        }
	        if (localizacaoConsumidor.getComplementoResidenciaConsumidor() == null || localizacaoConsumidor.getComplementoResidenciaConsumidor().trim().isEmpty()) {
	            throw new IllegalArgumentException("Complemento não pode ser nulo ou vazio.");
	        }
	        if (localizacaoConsumidor.getCepConsumidor() == null || localizacaoConsumidor.getCepConsumidor().trim().isEmpty()) {
	            throw new IllegalArgumentException("CEP não pode ser nulo ou vazio.");
	        }
	    }
	}


