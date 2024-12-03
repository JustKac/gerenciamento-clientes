package service;

import java.io.Serializable;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import client.ViaCepClient;
import client.dto.ViaCepResponseDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import model.Cliente;

@Named
@ApplicationScoped
public class ViaCepService implements Serializable {

    @Inject
    @RestClient
    ViaCepClient viaCepClient;

    public Cliente buscarEnderecoPorCep(String cep) {
        Cliente cliente = new Cliente();
        ViaCepResponseDTO viaCepResponseDTO = viaCepClient.getEndereco(cep);

        cliente.setCep(viaCepResponseDTO.cep());
        cliente.setEndereco(viaCepResponseDTO.logradouro());
        cliente.setBairro(viaCepResponseDTO.bairro());
        cliente.setCidade(viaCepResponseDTO.localidade());
        cliente.setEstado(viaCepResponseDTO.uf());

        return cliente;
    }
}
