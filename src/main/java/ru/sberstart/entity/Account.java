package ru.sberstart.entity;

import java.util.List;

public class Account {
    private long id;
    private String number;
    private double amount;
    private long idClient;

    public Account(long id, String number, double amount, long idClient) {
        this.id = id;
        this.number = number;
        this.amount = amount;
        this.idClient = idClient;
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

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", amount=" + amount +
                ", client=" + idClient +
                '}';
    }
}
