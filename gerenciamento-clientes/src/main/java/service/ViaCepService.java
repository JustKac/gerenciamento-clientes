package service;

import java.io.IOException;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import model.Cliente;

@Named
@ApplicationScoped
public class ViaCepService implements Serializable {

    public Cliente buscarEnderecoPorCep(String cep) {
        Cliente cliente = new Cliente();
        try {
            URL url = new URL("https://viacep.com.br/ws/" + cep + "/json/");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            JsonReader jsonReader = Json.createReader(conn.getInputStream());
            JsonObject jsonObject = jsonReader.readObject();
            jsonReader.close();

            cliente.setCep(jsonObject.getString("cep"));
            cliente.setEndereco(jsonObject.getString("logradouro"));
            cliente.setBairro(jsonObject.getString("bairro"));
            cliente.setCidade(jsonObject.getString("localidade"));
            cliente.setEstado(jsonObject.getString("uf"));

            conn.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return cliente;
    }
}