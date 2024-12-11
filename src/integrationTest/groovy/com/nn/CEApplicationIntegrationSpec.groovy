package com.nn
/**
class CEApplicationIntegrationSpec extends CurrencyExchangeIntegrationSpec {

    @Autowired
    ApplicationContext applicationContext
    @Autowired
    AccountController accountController
//will work after adding test containers or having local postgres server turned on
    def "Should execute clean"() {
        expect:
        applicationContext != null
        AccountController != null
    }
}
**/