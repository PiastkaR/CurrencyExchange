package com.nn.application.service;

import com.nn.domain.dto.CreateAccountRequest;
import com.nn.domain.model.Account;
import com.nn.domain.dto.AccountDto;

public class AccountMapper {
    public static AccountDto toDto(CreateAccountRequest account) {
        AccountDto dto = new AccountDto();
        dto.setId(account.getId());
        dto.setFirstName(account.getFirstName());
        dto.setLastName(account.getLastName());
        dto.setBalancePln(account.getBalancePln());
        dto.setBalanceUsd(account.getBalanceUsd());
        return dto;
    }

    public static Account toEntity(AccountDto dto) {
        Account account = new Account();
        account.setId(dto.getId());
        account.setFirstName(dto.getFirstName());
        account.setLastName(dto.getLastName());
        account.setBalancePln(dto.getBalancePln());
        account.setBalanceUsd(dto.getBalanceUsd());
        return account;
    }
}
