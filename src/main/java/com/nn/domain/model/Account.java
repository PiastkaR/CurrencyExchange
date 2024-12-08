package com.nn.domain.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 2, max = 100)
    private String firstName;

    @NotNull
    @Size(min = 2, max = 100)
    private String lastName;

    @NotNull
    private BigDecimal balancePln;

    private BigDecimal balanceUsd;

    public Account(Long id, String firstName, String lastName, BigDecimal balancePln, BigDecimal balanceUsd) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.balancePln = balancePln;
        this.balanceUsd = balanceUsd;
    }

    public Long getId() {
        return id;
    }

    public @NotNull @Size(min = 2, max = 100) String getFirstName() {
        return firstName;
    }

    public @NotNull @Size(min = 2, max = 100) String getLastName() {
        return lastName;
    }

    public @NotNull BigDecimal getBalancePln() {
        return balancePln;
    }

    public BigDecimal getBalanceUsd() {
        return balanceUsd;
    }

}
