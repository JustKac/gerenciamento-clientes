package service;

import java.io.Serializable;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import client.ViaCepClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.json.JsonObject;
import model.Cliente;

@Named
@ApplicationScoped
public class ViaCepService implements Serializable {

    @Inject
    @RestClient
    ViaCepClient viaCepClient;

    public Cliente buscarEnderecoPorCep(String cep) {
        Cliente cliente = new Cliente();
        JsonObject jsonObject = viaCepClient.getEndereco(cep);

        cliente.setCep(jsonObject.getString("cep"));
        cliente.setEndereco(jsonObject.getString("logradouro"));
        cliente.setBairro(jsonObject.getString("bairro"));
        cliente.setCidade(jsonObject.getString("localidade"));
        cliente.setEstado(jsonObject.getString("uf"));

        return cliente;
    }
}
