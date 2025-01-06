package com.nn.application.service

import com.nn.domain.model.Account
import com.nn.domain.model.NotFoundException
import com.nn.domain.service.ExchangeService
import com.nn.infra.api.NBPApiService
import com.nn.infra.repository.JpaAccountRepository
import com.nn.utils.TestUtils
import spock.lang.Specification

class AccountServiceSpec extends Specification {


    JpaAccountRepository accountRepository = Mock()
    NBPApiService nbpApiService = Mock()
    ExchangeService exchangeService = Mock()
    AccountService accountService = new AccountService(accountRepository, nbpApiService, exchangeService)

    Account account = new Account(1L,TestUtils.FIRST_NAME, TestUtils.LAST_NAME, new BigDecimal(100), BigDecimal.ZERO)

    def "Should create a new account with initial balance"() {
        given:
        String firstName = TestUtils.FIRST_NAME
        String lastName = TestUtils.LAST_NAME
        BigDecimal balancePln = new BigDecimal(1000)

        when:
        def result = accountService.createAccount(firstName, lastName, balancePln)

        then:
        1 * accountRepository.save(_) >> { Account account -> account }
        result != null
        result.firstName == firstName
        result.lastName == lastName
        result.balancePln == balancePln
        result.balanceUsd == BigDecimal.ZERO
    }

}
