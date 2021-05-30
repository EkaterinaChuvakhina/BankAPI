package ru.sberstart.service;

import ru.sberstart.dto.AccountDto;
import ru.sberstart.dto.BalanceRefillDto;
import ru.sberstart.dto.CardDto;
import ru.sberstart.dto.CreateCardDto;

import java.util.List;

public class ClientService {
    private final CardService cardService;
    private final AccountService accountService;

    public ClientService(CardService cardService, AccountService accountService) {
        this.cardService = cardService;
        this.accountService = accountService;
    }

    public CardDto createNewCard(CreateCardDto createCardDto) {
        return cardService.create(createCardDto);
    }


    public List<CardDto> getAllCards(Long clientId) {
        Long accountId = accountService.getAccountIdByClientId(clientId);
        return cardService.findAllCards(accountId);
    }

    public AccountDto checkBalance(Long accountId) {
        return accountService.getAmountById(accountId);
    }

    public AccountDto refillBalance(BalanceRefillDto balanceRefillDto) {
        return accountService.balanceRefill(balanceRefillDto);
    }
}
