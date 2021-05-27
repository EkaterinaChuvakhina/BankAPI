package ru.sberstart.service;

import ru.sberstart.dao.CardDao;
import ru.sberstart.entity.Account;
import ru.sberstart.entity.Card;

import java.util.List;

public class CardService {
    private final CardDao cardDao;

    public CardService(CardDao cardDao) {
        this.cardDao = cardDao;
    }

    public Card create(Account account) {
        Card card = new Card(account);
        cardDao.add(card);
        return card;
    }

    public List<Card> findAllCards(long accountId) {
        return cardDao.findAllCards(accountId);
    }
}
