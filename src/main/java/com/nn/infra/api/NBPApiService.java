package com.nn.infra.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nn.domain.model.NPBResponse;
import com.nn.domain.model.Rate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class NBPApiService {
    private final Logger logger = LoggerFactory.getLogger(NBPApiService.class);
    private static final String NBP_API_URL = "http://api.nbp.pl/api/exchangerates/rates/A/USD";
    private static final String REQUEST_TRACE = "Requesting NBP rate for: [{}]";
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    public NBPApiService(ObjectMapper objectMapper, RestTemplate restTemplate) {
        this.objectMapper = objectMapper;
        this.restTemplate = restTemplate;
    }


    public BigDecimal getExchangeRate() {
        URI uri = UriComponentsBuilder.fromHttpUrl(NBP_API_URL)
                .build()
                .toUri();
        RequestEntity<Void> requestEntity = RequestEntity.get(uri)
                .headers(prepareHeaders())
                .build();
        logger.trace(REQUEST_TRACE, NBP_API_URL);

        ResponseEntity<NPBResponse> response = restTemplate.exchange(requestEntity,NPBResponse.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            Optional<Rate> optionalRate = Arrays.stream(response.getBody().rates())
                    .findFirst();
            return optionalRate.map(rate -> new BigDecimal(rate.mid())).orElse(BigDecimal.ZERO);
        }

        return BigDecimal.ZERO;
    }

    private static HttpHeaders prepareHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON, MediaType.TEXT_HTML));

        return headers;
    }

}
