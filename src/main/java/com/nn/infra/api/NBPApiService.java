package com.nn.infra.api;

import com.nn.domain.model.NBPResponse;
import com.nn.domain.model.NBPResponseBuilder;
import com.nn.domain.model.Rate;
import com.nn.utils.ListUtils;
import org.apache.logging.log4j.util.Strings;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class NBPApiService {
    private final Logger logger = LoggerFactory.getLogger(NBPApiService.class);
    private static final String NBP_API_URL = "http://api.nbp.pl/api/exchangerates/rates/A/USD";
    private static final String REQUEST_TRACE = "Requesting NBP rate for: [{}]";
    private final RestTemplate restTemplate;

    public NBPApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public BigDecimal getExchangeRate() {
        if (ListUtils.isNotEmpty(handleNBPExchange().rates())) {
            Optional<Rate> optionalRate = handleNBPExchange().rates().stream()
                    .findFirst();

            return optionalRate.map(rate -> new BigDecimal(rate.mid()))
                    .orElse(BigDecimal.ZERO);
        }

        return BigDecimal.ZERO;
    }

    protected NBPResponse handleNBPExchange(){
        URI uri = UriComponentsBuilder.fromHttpUrl(NBP_API_URL)
                .build()
                .toUri();
        RequestEntity<Void> requestEntity = RequestEntity.get(uri)
                .headers(prepareHeaders())
                .build();
        logger.trace(REQUEST_TRACE, NBP_API_URL);

        ResponseEntity<NBPResponse> response = restTemplate.exchange(requestEntity, NBPResponse.class);

        return Optional.ofNullable(response.getBody())
                .orElseGet(() -> {
                    logger.warn("Response body is null for URI: {}", uri);
                    return supplyWithEmptyResponse();
                });
    }

    private static HttpHeaders prepareHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON, MediaType.TEXT_HTML));

        return headers;
    }

    private NBPResponse supplyWithEmptyResponse(){
        return new NBPResponseBuilder()
                .withTable(Strings.EMPTY)
                .withCurrency(Strings.EMPTY)
                .withCodes(Strings.EMPTY)
                .withRates(Collections.emptyList())
                .build();
    }

}
