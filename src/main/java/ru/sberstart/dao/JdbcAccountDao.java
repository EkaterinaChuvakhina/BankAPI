package ru.sberstart.dao;

import ru.sberstart.configuration.DatabaseConfiguration;
import ru.sberstart.entity.Account;
import ru.sberstart.exception.DaoException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcAccountDao implements AccountDao {
    private static final String UPDATE_QUERY = "UPDATE accounts SET amount = amount + ?  WHERE accounts.account_number = ?";
    private static final String SELECT_BY_NUMBER_QUERY = "SELECT * FROM accounts WHERE accounts.account_number = ?";
    private static final String SELECT_BY_ACCOUNT_ID_QUERY = "SELECT * FROM accounts WHERE accounts.account_id = ?";
    private static final String SELECT_FROM_CLIENTS_ACCOUNT_BY_ACCOUNT_ID_QUERY = "SELECT accounts.account_id FROM clients INNER JOIN accounts \n" +
            "WHERE accounts.account_id = ?";

    @Override
    public Account findById(long accountId) {
        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ACCOUNT_ID_QUERY);) {
            statement.setLong(1, accountId);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()) {
                long id = resultSet.getLong(1);
                String accountNumber = resultSet.getString(2);
                BigDecimal amount = resultSet.getBigDecimal(3);
                long clientId = resultSet.getLong(4);
                return new Account(id, accountNumber, amount, clientId);
            } else {
                throw new IllegalArgumentException("Account by id " + accountId + " not found");
            }

        } catch (SQLException ex) {
            throw new DaoException("Internal service error!");
        }
    }

    @Override
    public Long findAccountIdByClientId(long clientId) {
        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_FROM_CLIENTS_ACCOUNT_BY_ACCOUNT_ID_QUERY);) {
            statement.setLong(1, clientId);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()) {
                return resultSet.getLong(1);
            } else {
                throw new IllegalArgumentException("Account by client id " + clientId + " not found");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        throw new DaoException("Internal service error!");
    }

    @Override
    public Account findByNumber(String number) {
        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_NUMBER_QUERY)) {
            statement.setString(1, number);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()) {
                long id = resultSet.getLong(1);
                String accountNumber = resultSet.getString(2);
                BigDecimal amount = resultSet.getBigDecimal(3);
                long clientId = resultSet.getLong(4);
                return new Account(id, accountNumber, amount, clientId);
            } else {
                throw new IllegalArgumentException("Account by number " + number + " not found");
            }
        } catch (SQLException ex) {
            throw new DaoException("Internal service error!");
        }
    }

    @Override
    public Account updateAmount(String number, BigDecimal amount) {
        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setBigDecimal(1, amount);
            statement.setString(2, number);
            statement.executeUpdate();
            return findByNumber(number);
        } catch (SQLException e) {
            throw new DaoException("Internal service error!");
        }
    }
}
