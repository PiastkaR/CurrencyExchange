package com.nn.application.service;

import com.nn.domain.model.Account;
import com.nn.domain.model.AccountBuilder;
import com.nn.domain.service.ExchangeService;
import com.nn.infra.api.NBPApiService;
import com.nn.infra.repository.JpaAccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountService {

    private final JpaAccountRepository jpaAccountRepository;//TODO: change name without 'Jpa'
    private final NBPApiService nbpApiService;
    private final ExchangeService exchangeService;

    public AccountService(JpaAccountRepository jpaAccountRepository, NBPApiService nbpApiService, ExchangeService exchangeService) {
        this.jpaAccountRepository = jpaAccountRepository;
        this.nbpApiService = nbpApiService;
        this.exchangeService = exchangeService;
    }

    public Account createAccount(String firstName, String lastName, BigDecimal balancePln) {
        Account account = new AccountBuilder()//TODO sprawdz w bazie jakie są i dodaj następny!
                .withFirstName(firstName)
                .withLastname(lastName)
                .withBalancePln(balancePln)
                .withBalanceUsd(BigDecimal.ZERO)
                .build();

        return jpaAccountRepository.save(account);
    }

    @Transactional
    public Account getAccount(Long id) {
        return jpaAccountRepository.getById(id);
    }

    @Transactional
    public Account exchangePlnToUsd(Long accountId, BigDecimal amount) {
        BigDecimal rate = nbpApiService.getExchangeRate();
        Account account = exchangeService.exchangePlnToUsd(getAccount(accountId), amount, rate);

        return jpaAccountRepository.save(account);
    }

    @Transactional
    public Account exchangeUsdToPln(Long accountId, BigDecimal amount) {
        BigDecimal rate = nbpApiService.getExchangeRate();
        Account account = exchangeService.exchangeUsdToPln(getAccount(accountId), amount, rate);

        return jpaAccountRepository.save(account);
    }
}