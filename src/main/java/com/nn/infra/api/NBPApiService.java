package com.nn.infra.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nn.domain.model.NPBResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

@Service
public class NBPApiService {

    private final Logger logger = LoggerFactory.getLogger(NBPApiService.class);
    private static final String NBP_API_URL = "http://api.nbp.pl/api/exchangerates/rates/A/USD";
//TOD response
// <200 OK OK,
// {"table":"A","currency":"dolar amerykański","code":"USD","rates":[{"no":"237/
//A/NBP/2024","effectiveDate":"2024-12-06","mid":4.0341}]},

// [Date:"Sun, 08 Dec 2024 11:31:10 GMT", Cache-Control:"no-cache", Pragma:"no-cache",
// Content-Length:"134", Content-Type:"application/json; charset=utf-8",
// Expires:"-1", ETag:""E+/A5Kh6Hop01JvV7Cj3LwBZ3erYAzneitfNrw7mxmY="",
// Set-Cookie:"ee3la5eizeiY4Eix=jei1Xah3; path=/"]>

    public BigDecimal getExchangeRate(){
                 RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.getForEntity(NBP_API_URL, String.class);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    NPBResponse npbResponse = objectMapper.readValue(response.getBody(), NPBResponse.class);

                    if (npbResponse != null && npbResponse.rates() != null) {
                        Optional<String> optionalMid = Arrays.stream(npbResponse.rates()).findFirst().map(rate -> rate.mid());
                        optionalMid.orElseThrow(()-> new RuntimeException("No exchange rates found in NBP response" + Arrays.stream(npbResponse.rates()).findFirst()));
//                         npbResponse;  // Zwrócenie odpowiedzi z danymi kursów
                    } else {
                        throw new RuntimeException("No exchange rates found in NBP response" +response.getBody());
                    }
                } catch (Exception e) {
                    throw new RuntimeException("Error parsing NBP response", e);
                }
            } else {
                throw new RuntimeException("Error fetching exchange rates from NBP");
            }
return null;
}
//        RestTemplate restTemplate = new RestTemplate();
//        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(NBP_API_URL);
//
//        ResponseEntity<NPBResponse> responseEntity = restTemplate.getForEntity(builder.toUriString(), NPBResponse.class);
//
//        if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
//            NPBResponse npbResponse = responseEntity.getBody();
//
//            if (npbResponse != null && npbResponse.rate() != null && npbResponse.rate().length > 0) {
//                return npbResponse.rate()[0].mid();
//            } else {
//                throw new RuntimeException("No exchange rates found in NBP response." + responseEntity.getBody());
//            }
//        } else {
//            throw new RuntimeException("Failed to get exchange rate from NBP API. Status code: " + responseEntity);
//        }
    }
