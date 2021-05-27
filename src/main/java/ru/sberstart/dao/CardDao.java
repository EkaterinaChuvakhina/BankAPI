package ru.sberstart.dao;

import ru.sberstart.entity.Card;

import java.util.List;

public interface CardDao {
    List<Card> findAllCards(long accountId);

    Card save(Card card);
}
