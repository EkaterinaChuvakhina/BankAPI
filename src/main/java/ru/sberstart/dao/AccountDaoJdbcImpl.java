package ru.sberstart.dao;

import ru.sberstart.entity.Account;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDaoJdbcImpl implements AccountDao {
    private final DataSource dataSource;

    public AccountDaoJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public Account findById(long accountId) {
        Connection connection = getConnection();
        try {
            String query = "select * from accounts where accounts.id_account = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, accountId);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()) {
                long id = resultSet.getLong(1);
                String accountNumber = resultSet.getString(2);
                double amount = resultSet.getDouble(3);
                long clientId = resultSet.getLong(4);
                return new Account(id, accountNumber, amount, clientId);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Long findAccountIdByClientId(long clientId) {
        Connection connection = getConnection();
        try {
            String query = "select accounts.id_account from clients inner join accounts \n" +
                    "where accounts.id_account = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, clientId);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();

            if (resultSet.next()) {
                return resultSet.getLong(1);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public double findAmountById(long accountId) {
        return findById(accountId).getAmount();

    }

    @Override
    public void updateAmount(String number, double amount) {
        Connection connection = getConnection();
        try {
            String query = "UPDATE accounts SET amount = amount + ?  WHERE accounts.account_number = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setDouble(1, amount);
            statement.setString(2, number);
            statement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private Connection getConnection() {
        Connection connection = null;

        try {
            connection = dataSource.getConnection();
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
