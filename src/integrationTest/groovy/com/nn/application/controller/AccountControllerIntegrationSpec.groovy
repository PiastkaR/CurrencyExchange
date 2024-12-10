package com.nn.application.controller

import com.nn.CurrencyExchangeIntegrationSpec
import com.nn.domain.model.Account
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestTemplate
import spock.lang.Shared

import static org.springframework.http.RequestEntity.post

class AccountControllerIntegrationSpec extends CurrencyExchangeIntegrationSpec {

    @LocalServerPort
    int serverPort

    @Autowired
    AccountController accountController

    @Shared
    RestTemplate restTemplate = new RestTemplate()

    //Integration tests run on local DB now therefore they impact the state. Testcontainer setup is required for idempotent behaviour
    //DB state should be loaded and reset every time tests are run
    def "Should create an account"() {
        given:
        def map = new LinkedMultiValueMap<>()
        map.add("firstName", "Rafal")
        map.add("lastName", "Zablocki")
        map.add("balancePln", "1000.00")

        def uri = URI.create("http://localhost:${serverPort}/api/accounts")
        def requestEntity = post(uri)
                .body(map)

        when:
        def exchange = restTemplate.exchange(requestEntity, Account)

        then:
        exchange != null
        exchange.statusCode.is2xxSuccessful()
        exchange.body.firstName == "Rafal"
        exchange.body.lastName == "Zablocki"
        exchange.body.balancePln == new BigDecimal("1000.00")
    }

    def "Should get account by id"() {
        given:
        Long accountId = 1L

        def uri = URI.create("http://localhost:${serverPort}/api/accounts/${accountId}")

        when:
        def exchange = restTemplate.getForEntity(uri, Account)

        then:
        exchange != null
        exchange.statusCode.is2xxSuccessful()
        exchange.body.id == accountId
    }
    //TODO:Integration tests run on local DB now therefore they impact the state. Testcontainer setup is required for idempotent behaviour
    /**def "Should exchange PLN to USD"() {
        given:
        Long accountId = 1L
        BigDecimal amount = new BigDecimal("500.00")

        def map = new LinkedMultiValueMap<>()
        map.add("amount", amount.toString())

        def uri = URI.create("http://localhost:${serverPort}/api/accounts/${accountId}/exchange/pln-to-usd")
        def requestEntity = post(uri)
                .contentType(APPLICATION_FORM_URLENCODED)
                .accept(APPLICATION_JSON)
                .body(map)

        when:
        def exchange = restTemplate.exchange(requestEntity, Account)

        then:
        exchange != null
        exchange.statusCode.is2xxSuccessful()
        exchange.body.balancePln < new BigDecimal("1000.00")
        exchange.body.balanceUsd > BigDecimal.ZERO
    }
**/
}
