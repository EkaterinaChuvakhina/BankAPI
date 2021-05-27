package ru.sberstart.dao;

import ru.sberstart.entity.Card;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CardDaoJdbcImpl implements CardDao {
    private final DataSource dataSource;

    public CardDaoJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Card card) {
        Connection connection = getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO cards(card_number, id_account)" +
                    " VALUES (?, ?)");
            statement.setString(1, card.getNumber());
            statement.setLong(2, card.getAccountId());
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<Card> findAllCards(long accountId) {
        Connection connection = getConnection();
        try {

            String query = "select * from cards where cards.id_account = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, accountId);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            List<Card> cards = new ArrayList<>();
            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                String number = resultSet.getString(2);
                long clientId = resultSet.getLong(3);
                cards.add(new Card(id, number, clientId));
            }
            return cards;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
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
