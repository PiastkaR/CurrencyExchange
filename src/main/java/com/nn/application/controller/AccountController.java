package com.nn.application.controller;

import com.nn.application.service.AccountMapper;
import com.nn.application.service.AccountService;
import com.nn.domain.dto.AccountDto;
import com.nn.domain.dto.CreateAccountRequest;
import com.nn.domain.model.Account;
import jakarta.validation.Valid;
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
    public ResponseEntity<AccountDto> createAccount(@RequestBody @Valid CreateAccountRequest createAccountRequest) {
        Account account = accountService.createAccount(createAccountRequest);
        AccountDto resultDto = AccountMapper.toDto(account);

        return ResponseEntity.created(URI.create("/api/accounts/" + resultDto.getId())).body(resultDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccount(@PathVariable Long id) {
        Account account = accountService.getAccount(id);
        AccountDto accountDto = AccountMapper.toDto(account);

        return ResponseEntity.ok(accountDto);
    }

    @PostMapping("/{accountId}/exchange")
    public ResponseEntity exchangeCurrency(
            @PathVariable Long accountId,
            @RequestParam BigDecimal amount,
            @RequestParam String fromCurrency,
            @RequestParam String toCurrency
    ) {
        Account result = accountService.exchangeCurrency(accountId, amount, fromCurrency, toCurrency);
        AccountDto resultDto = AccountMapper.toDto(result);
        return ResponseEntity.accepted().body(resultDto);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    Map<String, String> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        return exception.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage
                ));
    }

}
