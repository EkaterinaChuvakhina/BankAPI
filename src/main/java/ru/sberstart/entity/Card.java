package ru.sberstart.entity;

import ru.sberstart.dategenerator.DateExpireAfterThreeYears;
import ru.sberstart.dategenerator.DateExpiryGenerator;

import java.util.Random;

public class Card {
    private long id;
    private String number;
    //private LocalDate cardExpiry;
    private long accountId;


    public Card(Account account) {
        DateExpiryGenerator dateExpiryGenerator = new DateExpireAfterThreeYears();
        number = String.valueOf(new Random().nextInt());
        //cardExpiry = dateExpiryGenerator.generate();
        accountId = account.getId();
    }

    public Card(long id, String number, long accountId) {
        this.id = id;
        this.number = number;
        this.accountId = accountId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

//    public LocalDate getCardExpiry() {
//        return cardExpiry;
//    }

//    public void setCardExpiry(LocalDate cardExpiry) {
//        this.cardExpiry = cardExpiry;
//    }

//    @Override
//    public String toString() {
//        return "Card{" +
//                "number='" + number + '\'' +
//                ", cardExpiry=" + cardExpiry +
//                ", dispositionId=" + id_account +
//                '}';
//    }

    public long getAccountId() {
        return accountId;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", accountId=" + accountId +
                '}';
    }
}
