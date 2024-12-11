package com.nn.infra.api

import com.nn.domain.model.NBPResponse
import com.nn.domain.model.NBPResponseBuilder
import com.nn.domain.model.RateBuilder
import org.apache.logging.log4j.util.Strings
import org.assertj.core.util.Lists
import org.springframework.http.HttpStatus
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

class NBPApiServiceSpec extends Specification {
    NBPApiService nbpApiService

    RestTemplate restTemplate = Mock()

    def setup() {
        nbpApiService = new NBPApiService(restTemplate)
    }

    def "should return the exchange rate if rates list has correct values"() {
        given:
        def rate = new RateBuilder()
                .withNo("123")
                .withEffectiveDate("2023-01-01")
                .withMid("4.1234")
                .build()
        def nbpResponse = new NBPResponse("table", "PLN", "codes", List.of(rate))
        def responseEntity = ResponseEntity.ok(nbpResponse)

        restTemplate.exchange(_ as RequestEntity, _ as Class) >> responseEntity

        when:
        BigDecimal exchangeRate = nbpApiService.getExchangeRate()

        then:
        exchangeRate == new BigDecimal("4.1234")
    }

    def "should return zero if rates list is empty"() {
        given:
        def nbpResponse = new NBPResponse("table", "PLN", "codes", Lists.emptyList())
        def responseEntity = ResponseEntity.ok(nbpResponse)

        restTemplate.exchange(_ as RequestEntity, _ as Class) >> responseEntity

        when:
        BigDecimal exchangeRate = nbpApiService.getExchangeRate()

        then:
        exchangeRate == BigDecimal.ZERO
    }

    def "should return zero if NBP response is null"() {
        given:
        restTemplate.exchange(_ as RequestEntity, _ as Class) >> new ResponseEntity<NBPResponse>(HttpStatus.OK)

        when:
        def exchange = nbpApiService.handleNBPExchange()

        then:
        exchange == new NBPResponseBuilder()
                .withTable(Strings.EMPTY)
                .withCurrency(Strings.EMPTY)
                .withCodes(Strings.EMPTY)
                .withRates(Collections.emptyList())
                .build();
    }

}
