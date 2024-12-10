package com.nn

import com.nn.application.controller.AccountController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import spock.lang.Specification

@SpringBootTest
class ContextSpec extends Specification {
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
