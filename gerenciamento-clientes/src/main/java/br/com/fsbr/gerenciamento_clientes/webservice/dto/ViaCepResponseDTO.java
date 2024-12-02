package br.com.fsbr.gerenciamento_clientes.webservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ViaCepResponseDTO(
        @JsonProperty("cep") String cep,
        @JsonProperty("logradouro") String logradouro,
        @JsonProperty("complemento") String complemento,
        @JsonProperty("bairro") String bairro,
        @JsonProperty("localidade") String localidade,
        @JsonProperty("uf") String uf,
        @JsonProperty("estado") String estado,
        @JsonProperty("regiao") String regiao,
        @JsonProperty("ibge") String ibge,
        @JsonProperty("gia") String gia,
        @JsonProperty("ddd") String ddd,
        @JsonProperty("siafi") String siafi
) {}
