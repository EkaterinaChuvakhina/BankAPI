package ru.sberstart.repository;

import ru.sberstart.entity.Account;
import ru.sberstart.entity.Card;

import java.util.ArrayList;
import java.util.List;

public class CardRepository {
    private final List<Card> cards = new ArrayList<>();

    public void add(Card card){
        cards.add(card);
        System.out.println(cards.size());
    }

    public List<Card> findAll(){
        return cards;
    }


}
