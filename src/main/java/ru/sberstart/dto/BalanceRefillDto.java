package ru.sberstart.dto;

import java.math.BigDecimal;

public class BalanceRefillDto {
    private String accountNumber;
    private BigDecimal amount;

    public BalanceRefillDto() {
    }

    public BalanceRefillDto(String accountNumber, BigDecimal amount) {
        this.accountNumber = accountNumber;
        this.amount = amount;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
