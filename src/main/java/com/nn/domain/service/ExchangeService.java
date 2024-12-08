package com.nn.domain.service;

import com.nn.domain.model.Account;
import com.nn.domain.model.AccountBuilder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class ExchangeService {
    public Account exchangeUsdToPln(Account account, BigDecimal amount, BigDecimal rate) {
        BigDecimal amountInPln = amount.multiply(rate);
        if (account.getBalanceUsd().compareTo(amount) >= 0) {
            return new AccountBuilder()
                    .withAccount(account)
                    .withBalancePln(account.getBalancePln().add(amountInPln).setScale(2, java.math.RoundingMode.HALF_UP))
                    .withBalanceUsd(account.getBalanceUsd().subtract(amount).setScale(2, java.math.RoundingMode.HALF_UP))
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
                    .withBalancePln(account.getBalancePln().subtract(amount).setScale(2, java.math.RoundingMode.HALF_UP))
                    .withBalanceUsd(account.getBalanceUsd().add(amountInUsd).setScale(2, java.math.RoundingMode.HALF_UP))
                    .build();

        } else {
            throw new RuntimeException("Insufficient amount of money in USD.");
        }
    }

}
