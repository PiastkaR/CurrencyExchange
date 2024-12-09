package com.nn.domain.service

import com.nn.domain.model.Account
import com.nn.utils.TestUtils
import spock.lang.Specification

import java.math.RoundingMode

class ExchangeServiceSpec extends Specification {

    ExchangeService exchangeService = new ExchangeService()

    Account account1 = new Account(1L, TestUtils.FIRST_NAME, TestUtils.LAST_NAME, new BigDecimal("100.50"), new BigDecimal("50.00"))
    Account account2 = new Account(2L, TestUtils.FIRST_NAME, TestUtils.LAST_NAME, new BigDecimal("100.00"), new BigDecimal("50.00"))

    def "Should update balances correctly according to USD -> PLN"() {
        given:
        BigDecimal amount = new BigDecimal("10.00")
        BigDecimal rate = new BigDecimal("4.00")

        when:
        Account updatedAccount = exchangeService.exchangeUsdToPln(account1, amount, rate)

        then:
        updatedAccount.balancePln == new BigDecimal("140.50").setScale(2, RoundingMode.HALF_UP)
        updatedAccount.balanceUsd == new BigDecimal("40.00").setScale(2, RoundingMode.HALF_UP)
    }

    def "Should throw exception in case of insufficient funds in USD"() {
        given:
        BigDecimal amount = new BigDecimal("60.00")
        BigDecimal rate = new BigDecimal("4.00")

        when:
        exchangeService.exchangeUsdToPln(account1, amount, rate)

        then:
        IllegalArgumentException e = thrown()
        e.message == "Insufficient amount of money in USD."
    }

    def "Should update balances correctly according to PLN -> USD"() {
        given:
        BigDecimal amount = new BigDecimal("40.00")
        BigDecimal rate = new BigDecimal("4.00")

        when:
        Account updatedAccount = exchangeService.exchangePlnToUsd(account2, amount, rate)

        then:
        updatedAccount.balancePln == new BigDecimal("60.00").setScale(2, RoundingMode.HALF_UP)
        updatedAccount.balanceUsd == new BigDecimal("60.00").setScale(2, RoundingMode.HALF_UP)
    }

    def "Should throw exception in case of insufficient funds in PLN"() {
        given:
        BigDecimal amount = new BigDecimal("400.00")
        BigDecimal rate = new BigDecimal("4.00")

        when:
        exchangeService.exchangePlnToUsd(account1, amount, rate)

        then:
        IllegalArgumentException e = thrown()
        e.message == "Insufficient amount of money in PLN."
    }

}
