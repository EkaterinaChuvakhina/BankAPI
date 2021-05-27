package ru.sberstart.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.sberstart.dao.CardDao;
import ru.sberstart.dto.AccountDto;
import ru.sberstart.dto.CardDto;
import ru.sberstart.dto.CreateCardDto;
import ru.sberstart.entity.Card;
import ru.sberstart.exception.DaoException;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {
    @Mock
    private CardService.CardNumberGenerator cardNumberGenerator;
    @Mock
    private AccountService accountService;
    @Mock
    private CardDao cardDao;
    @InjectMocks
    private CardService cardService;

    @Test
    void create_success() {
        String accountNumber = "133";
        CreateCardDto createCardDto = new CreateCardDto(accountNumber);
        AccountDto accountDto = new AccountDto(12L, BigDecimal.TEN);

        Mockito.when(accountService.getByNumber(eq("133"))).thenReturn(accountDto);
        Mockito.when(cardNumberGenerator.generate()).thenReturn("30");
        Card card = new Card(12L, "30");
        Card cardResult = new Card(1, "123", 12);


        Mockito.when(cardDao.save(eq(card))).thenReturn(cardResult);
        CardDto cardDto = cardService.create(createCardDto);

        Assertions.assertEquals("123", cardDto.getCardNumber());
    }

    @Test
    void create_fail_accountNotFound() {
        String accountNumber = "133";
        CreateCardDto createCardDto = new CreateCardDto(accountNumber);

        DaoException daoException = new DaoException("DB ERROR");
        Mockito.when(accountService.getByNumber(eq("133"))).thenThrow(daoException);

        DaoException exception = Assertions.assertThrows(DaoException.class,
                () -> cardService.create(createCardDto));

        Assertions.assertEquals("DB ERROR", exception.getMessage());
//        Mockito.verify(accountService, Mockito.times(1))
//                .getAmountById(1);
//        doNothing().when(cardService.findAllCards(1));
    }

    @Test
    void findAllCards() {
    }
}