package br.com.fiap.service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class CoordenadaService {
	
	public static String obterCoordenadas(String endereco) throws IOException {
		try {
        endereco = endereco.replace(" ", "+");
        URL url = new URL("https://nominatim.openstreetmap.org/search?q=" + endereco + "&format=json");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        // Verifica se a requisição foi bem sucedida
        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Erro ao obter coordenadas: " + conn.getResponseCode());
        }

        // Lê a resposta da API
        Scanner scanner = new Scanner(url.openStream());
        StringBuilder resposta = new StringBuilder();
        while (scanner.hasNext()) {
            resposta.append(scanner.nextLine());
        }
        scanner.close();

        // Parseia a resposta para obter as coordenadas
        String jsonResposta = resposta.toString();
        int startIndex = jsonResposta.indexOf("\"lat\":") + "\"lat\":".length();
        int endIndex = jsonResposta.indexOf(",", startIndex);
        String latitude = jsonResposta.substring(startIndex, endIndex);

        startIndex = jsonResposta.indexOf("\"lon\":") + "\"lon\":".length();
        endIndex = jsonResposta.indexOf("}", startIndex);
        String longitude = jsonResposta.substring(startIndex, endIndex);

        return latitude + ", " + longitude;
    } catch (Exception e) {
        throw new RuntimeException("Erro ao obter coordenadas da API externa: " + e.getMessage());
    }

	}
}


