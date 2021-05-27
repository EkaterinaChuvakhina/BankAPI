package ru.sberstart.dto;

import java.math.BigDecimal;

public class AccountDto {
    private final long accountId;
    private final BigDecimal amount;

    public AccountDto(long accountId, BigDecimal amount) {
        this.accountId = accountId;
        this.amount = amount;
    }

    public long getAccountId() {
        return accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

}
