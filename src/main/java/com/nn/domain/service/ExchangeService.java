package com.nn.domain.service;

import com.nn.domain.model.Account;
import com.nn.domain.model.AccountBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ExchangeService {

    public Account exchangeUsdToPln(Account account, BigDecimal amount, BigDecimal rate) {
        BigDecimal amountInPln = amount.multiply(rate);
        if (account.getBalancePln().compareTo(amount) >= 0) {
            return new AccountBuilder()
                    .withAccount(account)
                    .withBalancePln(account.getBalancePln().add(amountInPln))
                    .withBalanceUsd(account.getBalanceUsd().subtract(amount))
                    .build();

        } else {
            throw new RuntimeException("Insufficient amount of money in PLN.");
        }
    }

    public Account exchangePlnToUsd(Account account, BigDecimal amount, BigDecimal rate) {
        BigDecimal amountInUsd = amount.divide(rate, 2, RoundingMode.HALF_UP);
        if (account.getBalancePln().compareTo(amount) >= 0) {
            return new AccountBuilder()
                    .withAccount(account)
                    .withBalancePln(account.getBalancePln().subtract(amount))
                    .withBalanceUsd(account.getBalanceUsd().add(amountInUsd))
                    .build();

        } else {
            throw new RuntimeException("Insufficient amount of money in USD.");
        }
    }

}
