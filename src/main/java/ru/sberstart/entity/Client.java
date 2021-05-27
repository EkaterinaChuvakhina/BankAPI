package ru.sberstart.entity;

import ru.sberstart.entity.Card;

import java.util.List;

public class Client {
    private long id;
    private String name;
    private String surname;
    private String patronymic;
    private List<Account> accounts;

    public Client(String name, String surname, String patronymic, List<Account> accounts) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.accounts = accounts;
    }

    public Client(long id, String name, String surname, String patronymic, List<Card> cards) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", accounts=" + accounts +
                '}';
    }
}
