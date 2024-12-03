package client;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import client.dto.ViaCepResponseDTO;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@RegisterRestClient(configKey = "viacep-api")
public interface ViaCepClient {

    @GET
    @Path("/ws/{cep}/json/")
    @Produces(MediaType.APPLICATION_JSON)
    ViaCepResponseDTO getEndereco(@PathParam("cep") String cep);
}