package ru.sberstart.entity;

import java.math.BigDecimal;

public class Account {
    private long accountId;
    private String accountNumber;
    private BigDecimal amount;
    private long clientId;

    public Account() {
    }

    public Account(long accountId, String number, BigDecimal amount, long clientId) {
        this.accountId = accountId;
        this.accountNumber = number;
        this.amount = amount;
        this.clientId = clientId;
    }

    public long getAccountId() {
        return accountId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public long getClientId() {
        return clientId;
    }
}
