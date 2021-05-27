package ru.sberstart.entity;

import java.math.BigDecimal;

public class Account {
    private long accountId;
    private String number;
    private BigDecimal amount;
    private long clientId;

    public Account() {
    }

    public Account(long accountId, String number, BigDecimal amount, long clientId) {
        this.accountId = accountId;
        this.number = number;
        this.amount = amount;
        this.clientId = clientId;
    }

    public long getAccountId() {
        return accountId;
    }

    public String getNumber() {
        return number;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public long getClientId() {
        return clientId;
    }
}
