package com.nn.infra.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class NBPApiService {
    private static final String NBP_API_URL = "http://api.nbp.pl/api/exchangerates/rates/A/USD";
    //TODO sparsuj response: Adolar amerykańskiUSD237/A/NBP/20242024-12-064.0341

    public BigDecimal getExchangeRate(){
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(NBP_API_URL);
        ResponseEntity responseEntity = restTemplate.getForEntity(builder.toUriString(), Map.class);

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Failed to get Exchange rate from NBP API");
        }
        responseEntity.getBody();

        return restTemplate.getForObject(NBP_API_URL, BigDecimal.class);
    }
}
