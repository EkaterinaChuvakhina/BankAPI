package ru.sberstart.dto;

import java.math.BigDecimal;

public class AccountDto {
    private final Long accountId;
    private final BigDecimal amount;

    public AccountDto(Long accountId, BigDecimal amount) {
        this.accountId = accountId;
        this.amount = amount;
    }

    public Long getAccountId() {
        return accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

}
