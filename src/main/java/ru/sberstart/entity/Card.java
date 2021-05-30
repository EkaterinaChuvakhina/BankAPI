package ru.sberstart.entity;

import java.util.Objects;

public class Card {
    private long cardId;
    private String cardNumber;
    private long accountId;


    public Card(Long accountId, String cardNumber) {
        this.accountId = accountId;
        this.cardNumber = cardNumber;
    }

    public Card(long cardId, String cardNumber, long accountId) {
        this.cardId = cardId;
        this.cardNumber = cardNumber;
        this.accountId = accountId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public long getAccountId() {
        return accountId;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + cardId +
                ", number='" + cardNumber + '\'' +
                ", accountId=" + accountId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return cardId == card.cardId && accountId == card.accountId && Objects.equals(cardNumber, card.cardNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardId, cardNumber, accountId);
    }
}
