package ru.sberstart.dao;

import ru.sberstart.entity.Card;
import ru.sberstart.exception.DaoException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CardDaoJdbcImpl implements CardDao {
    private static final String INSERT_QUERY = "INSERT INTO cards (card_number, account_id) VALUES (?, ?)";
    private static final String SELECT_BY_ACCOUNT_ID = "SELECT * FROM cards WHERE cards.account_id = ?";
    private final DataSource dataSource;

    public CardDaoJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Card save(Card card) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_QUERY)) {
            statement.setString(1, card.getCardNumber());
            statement.setLong(2, card.getAccountId());
            statement.executeUpdate();
            return card;
        } catch (SQLException ex) {
            throw new DaoException("Internal service error!");
        }
    }

    @Override
    public List<Card> findAllCards(long accountId) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ACCOUNT_ID)) {
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

        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    private Connection getConnection() {
        Connection connection;
        try {
            connection = dataSource.getConnection();
            return connection;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }
}
