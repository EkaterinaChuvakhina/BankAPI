package ru.sberstart.dto;

public class CreateCardDto {
    private String accountNumber;

    public CreateCardDto() {
    }

    public CreateCardDto(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
}
