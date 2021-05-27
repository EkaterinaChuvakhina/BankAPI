package ru.sberstart.dto;

import ru.sberstart.entity.Account;

import java.time.LocalDateTime;

public class CardResponseDto {
    private String number;
    private LocalDateTime cardExpiry;
    private String nameOwner;
    private Account account;

    public CardResponseDto(String number, LocalDateTime cardExpiry, String nameOwner, Account account) {
        this.number = number;
        this.cardExpiry = cardExpiry;
        this.nameOwner = nameOwner;
        this.account = account;
    }
}
