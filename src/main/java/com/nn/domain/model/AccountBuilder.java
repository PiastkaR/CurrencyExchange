package com.nn.domain.model;

import java.math.BigDecimal;

public class AccountBuilder {
    private Long id;
    private String firstName;
    private String lastName;
    private BigDecimal balancePln;
    private BigDecimal balanceUsd;

    public AccountBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public AccountBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public AccountBuilder withLastname(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public AccountBuilder withBalancePln(BigDecimal balancePln) {
        this.balancePln = balancePln;
        return this;
    }

    public AccountBuilder withBalanceUsd(BigDecimal balanceUsd) {
        this.balanceUsd = balanceUsd;
        return this;
    }

    public AccountBuilder withAccount(Account account) {
        this.id = account.getId();
        this.firstName = account.getFirstName();
        this.lastName = account.getLastName();
        this.balancePln = account.getBalancePln();
        this.balanceUsd = account.getBalanceUsd();
        return this;
    }
    //TODO: obsluzyc generowanie randomowych wartosci i domyslnej w postaci 0 dla usd.
    public Account build() {
        return new Account(id, firstName, lastName, balancePln, balanceUsd);
    }
}

