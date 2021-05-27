package ru.sberstart.dao;

import ru.sberstart.entity.Card;

import java.util.List;

public interface CardDao {
    void add(Card card);

    List<Card> findAllCards(long accountId);
}
