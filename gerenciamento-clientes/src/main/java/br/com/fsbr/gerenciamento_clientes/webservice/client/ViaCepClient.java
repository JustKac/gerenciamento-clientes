package br.com.fsbr.gerenciamento_clientes.webservice.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.fsbr.gerenciamento_clientes.webservice.dto.ViaCepResponseDTO;

@Service
public class ViaCepClient {

    private static final Logger logger = LoggerFactory.getLogger(ViaCepClient.class);

    // Valor obtido diretamente do application.properties``
    @Value("${via-cep.url}")
    private String url;

    @Autowired
    private RestTemplate template;

    public ViaCepResponseDTO consultaCep(String cep) throws Exception {
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String url = UriComponentsBuilder
            .fromUriString(this.url)
            .buildAndExpand(cep)
            .toUriString();
        
        try {
            ResponseEntity<ViaCepResponseDTO> response = template.exchange(
                    url,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    new ParameterizedTypeReference<ViaCepResponseDTO>() {}
            );
            
            return response.getBody();
        
        // Pega erros HTTP de Status Code entre os valores (4_xx) e (5_xx)
        } catch (HttpStatusCodeException e) {
            logger.error("Detailed error message: {}", e.getResponseBodyAsString());
            throw new Exception(e);
        } catch (Exception e) {
            logger.error("Detailed error message: {}", e.getMessage());
            e.printStackTrace();
            throw new Exception(e);
        }
    }

}
