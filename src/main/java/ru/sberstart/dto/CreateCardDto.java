package ru.sberstart.dto;

public class CreateCardDto {
    private long id;
    private String number;
    private double amount;
    private long clientId;

    public CreateCardDto() {
    }

    public CreateCardDto(long id, String number, double amount, long clientId) {
        this.id = id;
        this.number = number;
        this.amount = amount;
        this.clientId = clientId;
    }

    public long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public double getAmount() {
        return amount;
    }

    public long getClientId() {
        return clientId;
    }
}
