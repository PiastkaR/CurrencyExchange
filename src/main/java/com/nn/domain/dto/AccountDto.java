package com.nn.domain.dto;

import java.math.BigDecimal;

public class AccountDto {
    private Long id;
    private String firstName;
    private String lastName;
    private BigDecimal balancePln;
    private BigDecimal balanceUsd;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public BigDecimal getBalancePln() {
        return balancePln;
    }

    public void setBalancePln(BigDecimal balancePln) {
        this.balancePln = balancePln;
    }

    public BigDecimal getBalanceUsd() {
        return balanceUsd;
    }

    public void setBalanceUsd(BigDecimal balanceUsd) {
        this.balanceUsd = balanceUsd;
    }
}
