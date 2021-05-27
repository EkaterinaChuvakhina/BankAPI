package ru.sberstart.dao;

public interface AccountDao {
    Long findAccountIdByClientId(long id);

    double findAmountById(long accountId);

    void updateAmount(String number, double amount);
}
