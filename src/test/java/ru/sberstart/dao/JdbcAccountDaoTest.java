package ru.sberstart.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.sberstart.configuration.DatabaseConfiguration;
import ru.sberstart.entity.Account;
import ru.sberstart.exception.DaoException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@ExtendWith(MockitoExtension.class)
class JdbcAccountDaoTest {
    private static MockedStatic<DatabaseConfiguration> databaseConfiguration;
    private static Connection connectionMock = Mockito.mock(Connection.class);
    private static PreparedStatement preparedStatementMock = Mockito.mock(PreparedStatement.class);

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
    private JdbcAccountDao jdbcAccountDao;

    @BeforeEach
    void setup() {
        Mockito.reset(preparedStatementMock);
    }

    @Test
    void findById_empty() throws SQLException {
        accountEmptyResultSetMock();
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> jdbcAccountDao.findById(1));

        Assertions.assertEquals("Account by id 1 not found", exception.getMessage());
    }


    @Test
    void findById_success() throws SQLException {
        accountResultSetMock();
        Account account = jdbcAccountDao.findById(1);

        Assertions.assertEquals(1, account.getAccountId());
        Assertions.assertEquals("123", account.getAccountNumber());
        Assertions.assertEquals(BigDecimal.valueOf(100.1), account.getAmount());
        Assertions.assertEquals(13L, account.getClientId());
    }

    @Test
    void findById_daoException() throws SQLException {
        accountResultSetExceptionallyMock();
        DaoException daoException = Assertions.assertThrows(DaoException.class,
                () -> jdbcAccountDao.findById(1));

        Assertions.assertEquals("Internal service error!", daoException.getMessage());
    }

    @Test
    void findAccountIdByClientId_empty() throws SQLException {
        accountEmptyResultSetMock();
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> jdbcAccountDao.findAccountIdByClientId(1L));

        Assertions.assertEquals("Account by client id 1 not found", exception.getMessage());
    }


    @Test
    void findAccountIdByClientId_success() throws SQLException {
        clientIdResultSetMock();
        long accountId = jdbcAccountDao.findAccountIdByClientId(1L);

        Assertions.assertEquals(1L, accountId);
    }

    @Test
    void findAccountIdByClientId_daoException() throws SQLException {
        accountResultSetExceptionallyMock();
        DaoException daoException = Assertions.assertThrows(DaoException.class,
                () -> jdbcAccountDao.findAccountIdByClientId(1L));

        Assertions.assertEquals("Internal service error!", daoException.getMessage());
    }

    @Test
    void findByNumber_empty() throws SQLException {
        accountEmptyResultSetMock();

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> jdbcAccountDao.findByNumber("1"));

        Assertions.assertEquals("Account by number 1 not found", exception.getMessage());
    }


    @Test
    void findByNumber_success() throws SQLException {
        accountResultSetMock();
        Account account = jdbcAccountDao.findByNumber("1");

        Assertions.assertEquals(1, account.getAccountId());
        Assertions.assertEquals("123", account.getAccountNumber());
        Assertions.assertEquals(BigDecimal.valueOf(100.1), account.getAmount());
        Assertions.assertEquals(13L, account.getClientId());
    }

    @Test
    void findByNumber_daoException() throws SQLException {
        accountResultSetExceptionallyMock();
        DaoException daoException = Assertions.assertThrows(DaoException.class,
                () -> jdbcAccountDao.findByNumber("1"));

        Assertions.assertEquals("Internal service error!", daoException.getMessage());
    }


    @Test
    void updateAmount_success() throws SQLException {
        accountResultSetMock();
        Account account = jdbcAccountDao.updateAmount("123", BigDecimal.TEN);

        Assertions.assertEquals(1, account.getAccountId());
        Assertions.assertEquals("123", account.getAccountNumber());
        Assertions.assertEquals(BigDecimal.valueOf(100.1), account.getAmount());
        Assertions.assertEquals(13L, account.getClientId());
    }

    @Test
    void updateAmount_empty() throws SQLException {
        Mockito.when(preparedStatementMock.executeUpdate()).thenThrow(new SQLException("Internal service error!"));
        DaoException daoException = Assertions.assertThrows(DaoException.class,
                () -> jdbcAccountDao.updateAmount("123", BigDecimal.TEN));

        Assertions.assertEquals("Internal service error!", daoException.getMessage());

    }

    private void accountResultSetExceptionallyMock() throws SQLException {
        ResultSet resultSetMock = Mockito.mock(ResultSet.class);
        Mockito.when(preparedStatementMock.getResultSet()).thenReturn(resultSetMock);
        Mockito.when(resultSetMock.next()).thenThrow(new SQLException("Internal service error!"));
    }

    private void accountEmptyResultSetMock() throws SQLException {
        ResultSet resultSetMock = Mockito.mock(ResultSet.class);
        Mockito.when(preparedStatementMock.getResultSet()).thenReturn(resultSetMock);
        Mockito.when(resultSetMock.next()).thenReturn(false);
    }

    private void clientIdResultSetMock() throws SQLException {
        ResultSet resultSetMock = Mockito.mock(ResultSet.class);
        Mockito.when(preparedStatementMock.getResultSet()).thenReturn(resultSetMock);
        Mockito.when(resultSetMock.next()).thenReturn(true);
        Mockito.when(resultSetMock.getLong(ArgumentMatchers.eq(1))).thenReturn(1L);
    }

    private void accountResultSetMock() throws SQLException {
        ResultSet resultSetMock = Mockito.mock(ResultSet.class);
        Mockito.when(preparedStatementMock.getResultSet()).thenReturn(resultSetMock);
        Mockito.when(resultSetMock.next()).thenReturn(true);
        Mockito.when(resultSetMock.getLong(ArgumentMatchers.eq(1))).thenReturn(1L);
        Mockito.when(resultSetMock.getString(ArgumentMatchers.eq(2))).thenReturn("123");
        Mockito.when(resultSetMock.getBigDecimal(ArgumentMatchers.eq(3))).thenReturn(BigDecimal.valueOf(100.1));
        Mockito.when(resultSetMock.getLong(ArgumentMatchers.eq(4))).thenReturn(13L);
    }
}