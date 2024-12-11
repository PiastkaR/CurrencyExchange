package com.nn.domain.model;

import com.google.common.collect.ImmutableList;

import java.util.List;

public class NBPResponseBuilder {

    private String table;
    private String currency;
    private String codes;
    private List<Rate> rates;

    public NBPResponseBuilder withTable(String table) {
        this.table = table;
        return this;
    }

    public NBPResponseBuilder withCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public NBPResponseBuilder withCodes(String codes) {
        this.codes = codes;
        return this;
    }

    public NBPResponseBuilder withRates(List<Rate> rates) {
        this.rates = ImmutableList.copyOf(rates);
        return this;
    }

    public NBPResponse build() {
        return new NBPResponse(table, currency, codes, rates);
    }

}
