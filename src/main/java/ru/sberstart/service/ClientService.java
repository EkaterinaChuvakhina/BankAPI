package ru.sberstart.service;

import ru.sberstart.dto.BalanceRefillDto;
import ru.sberstart.entity.Account;
import ru.sberstart.entity.Card;
import ru.sberstart.entity.Client;
import ru.sberstart.exception.ClientNotFoundException;
import ru.sberstart.repository.ClientRepository;

import java.util.List;

public class ClientService {
    private final ClientRepository clientRepository;
    private final CardService cardService;
    private final AccountService accountService;

    public ClientService(ClientRepository clientRepository, CardService cardService, AccountService accountService) {
        this.clientRepository = clientRepository;
        this.cardService = cardService;
        this.accountService = accountService;
    }

    private Client getById(Long id) {
        Client client = clientRepository.findById(id);
        if (client == null) {
            throw new ClientNotFoundException("Account with id: " + id + "not found");
        }

        return client;
    }

    public Card createNewCard(Account account) {
        Card newCard = cardService.create(account);
        System.out.println(newCard.toString());
        return newCard;
    }

    public List<Card> getAllCards(Long clientId) {
        long accountId = accountService.getAccountIdByClientId(clientId);
        List<Card> cards = cardService.findAllCards(accountId);
        System.out.println(cards);
        return cards;
    }

    public double getAmount(Long accountId){
        return accountService.getAmountById(accountId);
    }

    public void refillBalance(BalanceRefillDto balanceRefillDto) {
        accountService.amountUpdate(balanceRefillDto.getAccountNumber(), balanceRefillDto.getAmount());
    }
}
