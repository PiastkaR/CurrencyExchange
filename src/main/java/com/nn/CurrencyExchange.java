package com.nn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.nn")
public class CurrencyExchange {
    public static void main(String[] args) {
        SpringApplication.run(CurrencyExchange.class, args);
    }
}