package com.nn.domain.service;

import com.nn.domain.model.Account;
import com.nn.domain.model.AccountBuilder;
import com.nn.infra.api.ExchangeRateProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class ExchangeService {
    private static final String PLN_EXCEPTION_MESSAGE = "Insufficient amount of money in PLN.";
    private static final String USD_EXCEPTION_MESSAGE = "Insufficient amount of money in USD.";
    private static final Logger logger = LoggerFactory.getLogger(ExchangeService.class);

    private final ExchangeRateProvider exchangeRateProvider;

    public ExchangeService(ExchangeRateProvider exchangeRateProvider) {
        this.exchangeRateProvider = exchangeRateProvider;
    }

    public Account exchangeUsdToPln(Account account, BigDecimal amount, BigDecimal rate) {
        return exchange(account, amount, rate, USD_EXCEPTION_MESSAGE, true);
    }

    public Account exchangePlnToUsd(Account account, BigDecimal amount, BigDecimal rate) {
        return exchange(account, amount, rate, PLN_EXCEPTION_MESSAGE, false);
    }

    public Account exchange(Account account, BigDecimal amount, String fromCurrency, String toCurrency) {
        BigDecimal rate = exchangeRateProvider.getExchangeRate(fromCurrency, toCurrency);
        BigDecimal amountInTargetCurrency = fromCurrency.equals("USD") ? amount.multiply(rate) : amount.divide(rate, 2, RoundingMode.HALF_UP);

        if (fromCurrency.equals("USD") && account.getBalanceUsd().compareTo(amount) >= 0) {
            return new AccountBuilder()
                    .withAccount(account)
                    .withBalancePln(account.getBalancePln().add(amountInTargetCurrency).setScale(2, RoundingMode.HALF_UP))
                    .withBalanceUsd(account.getBalanceUsd().subtract(amount).setScale(2, RoundingMode.HALF_UP))
                    .build();

        } else if (fromCurrency.equals("PLN") && account.getBalancePln().compareTo(amount) >= 0) {
            return new AccountBuilder()
                    .withAccount(account)
                    .withBalancePln(account.getBalancePln().subtract(amount).setScale(2, RoundingMode.HALF_UP))
                    .withBalanceUsd(account.getBalanceUsd().add(amountInTargetCurrency).setScale(2, RoundingMode.HALF_UP))
                    .build();

        } else {
            String errorMessage = fromCurrency.equals("USD") ?
                    "Insufficient amount of money in USD." :
                    "Insufficient amount of money in PLN.";
            logger.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
    }

}
