package com.nn.application.controller

import com.nn.CurrencyExchangeIntegrationSpec
import com.nn.domain.model.Account
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestTemplate
import spock.lang.Shared

import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.http.RequestEntity.post

class AccountControllerIntegrationSpec extends CurrencyExchangeIntegrationSpec {

    @LocalServerPort
    int serverPort

    @Autowired
    AccountController accountController

    @Shared
    RestTemplate restTemplate = new RestTemplate()

    def "Should create an account"() {
        given:
        def map = new LinkedMultiValueMap<>()
        map.add("firstName", "Rafal")
        map.add("lastName", "Zablocki")
        map.add("balancePln", new BigDecimal(1000.00))

        def uri = URI.create("http://localhost:${serverPort}/api/accounts")
        def requestEntity = post(uri)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .body(map)

        when:
        def exchange = restTemplate.exchange(requestEntity, Account)

        then:
        exchange != null
    }
}
