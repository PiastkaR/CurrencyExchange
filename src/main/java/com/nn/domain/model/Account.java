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
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @Size(min = 2, max = 100)
    @Column(name = "last_name")
    private String lastName;

    @NotNull
    @Column(name = "balance_pln")
    private BigDecimal balancePln;

    @Column(name = "balance_usd")
    private BigDecimal balanceUsd;

    public Account() {
    }

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

    public void setBalanceUsd(BigDecimal balanceUsd) {


        this.balanceUsd = balanceUsd;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(@NotNull @Size(min = 2, max = 100) String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(@NotNull @Size(min = 2, max = 100) String lastName) {
        this.lastName = lastName;
    }

    public void setBalancePln(@NotNull BigDecimal balancePln) {
        this.balancePln = balancePln;
    }
}
