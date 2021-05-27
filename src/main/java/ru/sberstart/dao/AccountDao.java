package ru.sberstart.dao;

import ru.sberstart.entity.Account;

import java.math.BigDecimal;

public interface AccountDao {
    Long findAccountIdByClientId(long accountId);

    Account findById(long accountId);

    Account updateAmount(String number, BigDecimal amount);

    Account findByNumber(String number);
}
