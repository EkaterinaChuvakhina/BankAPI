package ru.sberstart.dto;

public class BalanceRefillDto {
    private String accountNumber;
    private double amount;

    public BalanceRefillDto() {
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getAmount() {
        return amount;
    }
}
