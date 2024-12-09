package com.nn

import com.nn.application.controller.AccountController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext

class CEApplicationIntegrationSpec extends CurrencyExchangeIntegrationSpec {
    @Autowired
    ApplicationContext applicationContext
    @Autowired
    AccountController accountController

    def "Should execute clean"() {
        expect:
        applicationContext != null
        AccountController != null
    }
}
