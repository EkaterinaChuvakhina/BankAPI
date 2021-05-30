package ru.sberstart.service;

import org.mapstruct.factory.Mappers;
import ru.sberstart.dao.AccountDao;
import ru.sberstart.dto.AccountDto;
import ru.sberstart.dto.BalanceRefillDto;
import ru.sberstart.entity.Account;
import ru.sberstart.mapper.AccountMapper;

import java.math.BigDecimal;

public class AccountService {
    private final AccountMapper accountMapper = Mappers.getMapper(AccountMapper.class);
    private final AccountDao accountDao;

    public AccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public Long getAccountIdByClientId(long clientId) {
        return accountDao.findAccountIdByClientId(clientId);
    }

    public AccountDto getAmountById(long accountId) {
        Account account = accountDao.findById(accountId);
        return accountMapper.toAccountDto(account);
    }

    public AccountDto balanceRefill(BalanceRefillDto balanceRefillDto) {
        BigDecimal amount = balanceRefillDto.getAmount();
        String accountNumber = balanceRefillDto.getAccountNumber();
        if (amount.compareTo(new BigDecimal(0)) < 0) {
            throw new IllegalArgumentException("Pass negative amount" + amount);
        }

        Account updatedAccount = accountDao.updateAmount(accountNumber, amount);
        return accountMapper.toAccountDto(updatedAccount);
    }

    public AccountDto getByNumber(String accountNumber) {
        return accountMapper.toAccountDto(accountDao.findByNumber(accountNumber));
    }
}
