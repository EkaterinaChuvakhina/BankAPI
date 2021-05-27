package ru.sberstart.service;

import org.mapstruct.factory.Mappers;
import ru.sberstart.dao.CardDao;
import ru.sberstart.dto.AccountDto;
import ru.sberstart.dto.CardDto;
import ru.sberstart.dto.CreateCardDto;
import ru.sberstart.entity.Card;
import ru.sberstart.mapper.CardMapper;

import java.util.List;
import java.util.Random;

public class CardService {
    private static final Random random = new Random();
    private final CardMapper cardMapper = Mappers.getMapper(CardMapper.class);
    private final AccountService accountService;
    private final CardDao cardDao;
    private final CardNumberGenerator cardNumberGenerator;


    public CardService(AccountService accountService,
                       CardDao cardDao,
                       CardNumberGenerator cardNumberGenerator) {
        this.accountService = accountService;
        this.cardDao = cardDao;
        this.cardNumberGenerator = cardNumberGenerator;
    }

    public CardDto create(CreateCardDto createCardDto) {
        AccountDto account = accountService.getByNumber(createCardDto.getAccountNumber());
        String generatedCardNumber = cardNumberGenerator.generate();
        Card createdCard = cardDao.save(new Card(account.getAccountId(), generatedCardNumber));
        return cardMapper.toCardDto(createdCard);
    }

    public List<CardDto> findAllCards(long accountId) {
        return cardMapper.toListCardDto(cardDao.findAllCards(accountId));
    }

    public static class CardNumberGenerator {
        public CardNumberGenerator() {
        }

        public String generate() {
            return String.valueOf(Math.abs(random.nextInt()));
        }

        ;
    }
}
