package ru.sberstart.service;

import ru.sberstart.dao.AccountDao;

public class AccountService {
    private final AccountDao accountDao;

    public AccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public Long getAccountIdByClientId(long clientId) {
        return accountDao.findAccountIdByClientId(clientId);
    }

    public double getAmountById(long accountId) {
        return accountDao.findAmountById(accountId);
    }

    public void amountUpdate(String number, double amount) {
        accountDao.updateAmount(number,amount);
    }
}
