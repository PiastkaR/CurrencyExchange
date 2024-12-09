package com.nn.application.controller;

import com.nn.application.service.AccountService;
import com.nn.domain.model.Account;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * @Param balancePln: it is assumed to be compulsory for Polish origin clients, might be easily changed/extended
     * */
    @PostMapping
    public ResponseEntity<Account> createAccount(
            @RequestParam @Valid String firstName,
            @RequestParam @Valid String lastName,
            @RequestParam BigDecimal balancePln
    ) {
        Account result = accountService.createAccount(firstName, lastName, balancePln);

        return ResponseEntity.created(URI.create("api/accounts/" + result.getId())).body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccount(@PathVariable Long id) {
        Account account = accountService.getAccount(id);

        return ResponseEntity.ok(account);
    }

    @PostMapping("/{accountId}/exchange/pln-to-usd")
    public ResponseEntity<Account> exchangePlnToUsd(
            @PathVariable @NotNull Long accountId,
            @RequestParam BigDecimal amount
    ) {
        Account result = accountService.exchangePlnToUsd(accountId, amount);

        return ResponseEntity.accepted().body(result);
    }

    @PostMapping("/{accountId}/exchange/usd-to-pln")
    public ResponseEntity<Account> exchangeUsdToPln(
            @PathVariable @NotNull Long accountId,
            @RequestParam BigDecimal amount
    ) {
        Account result = accountService.exchangeUsdToPln(accountId, amount);

        return ResponseEntity.accepted().body(result);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    Map<String, String> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        return exception.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        DefaultMessageSourceResolvable::getDefaultMessage
                ));
    }

}
