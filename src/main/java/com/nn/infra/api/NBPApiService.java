package com.nn.infra.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
public class NBPApiService {

    private final Logger logger = LoggerFactory.getLogger(NBPApiService.class);
    private static final String NBP_API_URL = "http://api.nbp.pl/api/exchangerates/rates/A/USD";
    private static final String REQUEST_TRACE = "Requesting NBP rate for: [{}]";
    private final ObjectMapper objectMapper;
//    private final RestTemplate restTemplate;

    public NBPApiService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
//        this.restTemplate = restTemplate;
    }


//TOD response
// <200 OK OK,
// {"table":"A","currency":"dolar amerykański","code":"USD","rates":[{"no":"237/
//A/NBP/2024","effectiveDate":"2024-12-06","mid":4.0341}]},
// [Date:"Sun, 08 Dec 2024 11:31:10 GMT", Cache-Control:"no-cache", Pragma:"no-cache",
// Content-Length:"134", Content-Type:"application/json; charset=utf-8",
// Expires:"-1", ETag:""E+/A5Kh6Hop01JvV7Cj3LwBZ3erYAzneitfNrw7mxmY="",
// Set-Cookie:"ee3la5eizeiY4Eix=jei1Xah3; path=/"]>

//    public BigDecimal getExchangeRate() {
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<String> response = restTemplate.getForEntity(NBP_API_URL, String.class);
//
//        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
//            try {
//                NPBResponse npbResponse = objectMapper.readValue(response.getBody(), NPBResponse.class);
//                if (npbResponse.rates() != null) {
//                    Optional<String> optionalMid = Arrays.stream(npbResponse.rates()).findFirst().map(com.nn.domain.model.Rate::mid);
//                    if (optionalMid.isPresent()) {
//                        return new BigDecimal(optionalMid.get());
//                    }
//                } else {
//                    logger.error("No exchange rates found in NBP response [{}]", response.getBody());
//                }
//            } catch (Exception e) {
//                logger.error("Error parsing NBP response [{}]", response.getBody());
//            }
//        } else {
//            logger.error("Error fetching exchange rates from NBP, please check if service is available.");
//        }
//        return BigDecimal.ZERO;
//    }

    public BigDecimal getExchangeRate() {
        java.net.URI uri = org.springframework.web.util.UriComponentsBuilder.fromHttpUrl(NBP_API_URL)
                .build()
                .toUri();
        org.springframework.http.RequestEntity<Void> requestEntity = org.springframework.http.RequestEntity.get(uri)
                .headers(prepareHeaders())
                .build();
        logger.trace(REQUEST_TRACE, NBP_API_URL);
        RestTemplate restTemplate = new RestTemplate();

        org.springframework.http.ResponseEntity<com.nn.domain.model.NPBResponse> response = restTemplate.exchange(requestEntity, com.nn.domain.model.NPBResponse.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            java.util.Optional<com.nn.domain.model.Rate> optionalRate = java.util.Arrays.stream(response.getBody().rates()).findFirst();
            return optionalRate.map(rate -> new java.math.BigDecimal(rate.mid())).orElse(java.math.BigDecimal.ZERO);
        }
        return BigDecimal.ZERO;
    }

    private static org.springframework.http.HttpHeaders prepareHeaders() {
        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.setAccept(java.util.List.of(org.springframework.http.MediaType.APPLICATION_JSON, org.springframework.http.MediaType.TEXT_HTML));

        return headers;
    }
}
