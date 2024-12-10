package com.nn.application.controller

import com.nn.application.service.AccountService
import com.nn.domain.model.Account
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import spock.lang.Specification

@WebMvcTest(AccountController.class)
class AccountControllerTest extends Specification {

    @Autowired
    MockMvc mvcController

    @MockBean
    AccountService accountService


    def "Should return account"() {
        given:
        {
            def mockAccount = new Account(id: 1, firstName: "John", lastName: "Doe", balancePln: BigDecimal.valueOf(1000))
            accountService.getAccount(1) >> mockAccount
        }

        when:
        def resultActions = mvcController.perform(MockMvcRequestBuilders.get("/api/accounts/1"))

        then:
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
    }

    def "Should exchange PLN to USD"() {
        given:
        def mockAccount = new Account(id: 3, firstName: "John", lastName: "Doe", balancePln: BigDecimal.valueOf(1000))
        accountService.exchangePlnToUsd(3L, BigDecimal.valueOf(100)) >> mockAccount

        when:
        def resultActions = mvcController.perform(
                MockMvcRequestBuilders.post("/api/accounts/3/exchange/pln-to-usd")
                        .param("amount", "100")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )

        then:
        resultActions.andExpect(MockMvcResultMatchers.status().isAccepted())
    }

    def "Should exchange USD to PLN"() {
        given:
        def mockAccount = new Account(id: 4, firstName: "John", lastName: "Doe", balancePln: BigDecimal.valueOf(1000))
        accountService.exchangeUsdToPln(4L, BigDecimal.valueOf(100)) >> mockAccount

        when:
        def resultActions = mvcController.perform(
                MockMvcRequestBuilders.post("/api/accounts/4/exchange/usd-to-pln")
                        .param("amount", "100")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )

        then:
        resultActions.andExpect(MockMvcResultMatchers.status().isAccepted())
    }

}
