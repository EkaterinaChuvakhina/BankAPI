package ru.sberstart.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.sberstart.configuration.DatabaseConfiguration;
import ru.sberstart.entity.Card;
import ru.sberstart.exception.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class JdbcCardDaoTest {
    private static MockedStatic<DatabaseConfiguration> databaseConfiguration;
    private static Connection connectionMock = Mockito.mock(Connection.class);
    private static PreparedStatement preparedStatementMock = Mockito.mock(PreparedStatement.class);
    @Captor
    private static ArgumentCaptor<String> stringCaptor;

    @Captor
    private static ArgumentCaptor<Long> longCaptor;

    static {
        databaseConfiguration = Mockito.mockStatic(DatabaseConfiguration.class);
        databaseConfiguration.when(DatabaseConfiguration::getConnection).thenReturn(connectionMock);

        try {
            Mockito.when(connectionMock.prepareStatement(ArgumentMatchers.anyString()))
                    .thenReturn(preparedStatementMock);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @InjectMocks
    JdbcCardDao jdbcCardDao;

    @BeforeEach
    void setup() {
        Mockito.reset(preparedStatementMock);
    }

    @Test
    void save_success() throws SQLException {
        Card card = new Card(1L, "123");
        Card savedCard = jdbcCardDao.save(card);
        Mockito.verify(preparedStatementMock).setString(ArgumentMatchers.eq(1), stringCaptor.capture());
        Mockito.verify(preparedStatementMock).setLong(ArgumentMatchers.eq(2), longCaptor.capture());
        Assertions.assertEquals("123", stringCaptor.getValue());
        Assertions.assertEquals(1L, ((long) longCaptor.getValue()));

        Assertions.assertEquals("123", savedCard.getCardNumber());
        Assertions.assertEquals(1L, savedCard.getAccountId());
    }

    @Test
    void save_failed() throws SQLException {
        Card card = new Card(1L, "123");
        Mockito.when(preparedStatementMock.executeUpdate()).thenThrow(new SQLException());
        DaoException exception = Assertions.assertThrows(DaoException.class,
                () -> jdbcCardDao.save(card));

        Assertions.assertEquals("Internal service error!", exception.getMessage());
    }

    @Test
    void findAllCards_success() throws SQLException {
        ResultSet resultSetMock = Mockito.mock(ResultSet.class);
        Mockito.when(resultSetMock.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        Mockito.when(resultSetMock.getLong(1))
                .thenReturn(1L).thenReturn(2L);
        Mockito.when(preparedStatementMock.getResultSet()).thenReturn(resultSetMock);

        Mockito.when(resultSetMock.getString(2))
                .thenReturn("123").thenReturn("321");

        List<Card> cards = jdbcCardDao.findAllCards(433L);
        Mockito.verify(preparedStatementMock).setLong(ArgumentMatchers.eq(1), longCaptor.capture());
        Assertions.assertEquals(433L, (long) longCaptor.getValue());

        Card firstCard = cards.get(0);
        Assertions.assertEquals("123", firstCard.getCardNumber());
        Assertions.assertEquals(433L, firstCard.getAccountId());
        Card secondCard = cards.get(1);
        Assertions.assertEquals("321", secondCard.getCardNumber());
        Assertions.assertEquals(433L, secondCard.getAccountId());
    }

    @Test
    void findAllCards_failed() throws SQLException {
        Mockito.when(preparedStatementMock.execute()).thenThrow(new SQLException());
        DaoException exception = Assertions.assertThrows(DaoException.class,
                () -> jdbcCardDao.findAllCards(1L));

        Assertions.assertEquals("Internal service error!", exception.getMessage());
    }
}