package ru.sberstart.dto;

public class BalanceRefillResponseDto {
    private String accountNumber;
    private Double amount;

    public BalanceRefillResponseDto(String accountNumber, Double amount) {
        this.accountNumber = accountNumber;
        this.amount = amount;
    }
}
