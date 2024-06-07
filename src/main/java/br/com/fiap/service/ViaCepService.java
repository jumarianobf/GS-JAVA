package br.com.fiap.service;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;

import br.com.fiap.model.LocalizacaoColetador;

public class ViaCepService {

	//método
	public LocalizacaoColetador getLocalizacaoColetador(String cep) throws ClientProtocolException, IOException {
		
		LocalizacaoColetador endereco = null;
		
		//request
		HttpGet request = new HttpGet("https://viacep.com.br/ws/"+cep+"/json/");
		
		
		//client
		CloseableHttpClient httpClient = HttpClientBuilder.create().disableRedirectHandling().build();
		
		//respose
		CloseableHttpResponse response = httpClient.execute(request);
		
		HttpEntity entity = response.getEntity();
		
		if(entity != null) {
			String result = EntityUtils.toString(entity);
			
			//criando um objeto Gson
			Gson gson = new Gson();
			
			endereco = gson.fromJson(result, LocalizacaoColetador.class);	
		}
		
		return endereco;
		
	}
	
}
