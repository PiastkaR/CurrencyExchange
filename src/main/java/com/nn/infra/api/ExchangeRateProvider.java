package com.nn.infra.api;

import java.math.BigDecimal;

public interface ExchangeRateProvider {
    BigDecimal getExchangeRate(String fromCurrency, String toCurrency);
}
