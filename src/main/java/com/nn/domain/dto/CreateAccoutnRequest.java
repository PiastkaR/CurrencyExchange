package com.nn.domain.dto;

import java.math.BigDecimal;

public class CreateAccoutnRequest {
    @NotNull
    @Size(min = 1, max = 50)
    private String firstName;

    @NotNull
    @Size(min = 1, max = 50)
    private String lastName;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false, message = "balancePln must be greater than zero")
    private BigDecimal balancePln;

    // Getters and Setters
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
}
