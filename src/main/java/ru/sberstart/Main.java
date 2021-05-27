package ru.sberstart;

import com.sun.net.httpserver.HttpServer;
import ru.sberstart.configuration.DatabaseConfiguration;
import ru.sberstart.dao.AccountDao;
import ru.sberstart.dao.AccountDaoJdbcImpl;
import ru.sberstart.dao.CardDao;
import ru.sberstart.dao.CardDaoJdbcImpl;
import ru.sberstart.handler.BalanceRefillHandler;
import ru.sberstart.handler.CheckBalanceHandler;
import ru.sberstart.handler.CreateCardHandler;
import ru.sberstart.handler.GetAllCardsHandler;
import ru.sberstart.service.AccountService;
import ru.sberstart.service.CardService;
import ru.sberstart.service.ClientService;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URISyntaxException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws IOException, SQLException, URISyntaxException {
        DatabaseConfiguration databaseConfiguration = new DatabaseConfiguration();
        databaseConfiguration.startupH2();
        AccountDao accountDao = new AccountDaoJdbcImpl(DatabaseConfiguration.getDatasource());
        AccountService accountService = new AccountService(accountDao);
        CardDao cardDao = new CardDaoJdbcImpl(DatabaseConfiguration.getDatasource());

        CardService.CardNumberGenerator cardNumberGenerator = new CardService.CardNumberGenerator();
        CardService cardService = new CardService(accountService, cardDao, cardNumberGenerator);

        ClientService clientService = new ClientService(cardService, accountService);

        HttpServer server = HttpServer.create();
        server.bind(new InetSocketAddress(8081), 0);
        server.createContext("/client/card/create", new CreateCardHandler(clientService));
        server.createContext("/client/cards", new GetAllCardsHandler(clientService));
        server.createContext("/client/balance", new CheckBalanceHandler(clientService));
        server.createContext("/client/refill-balance", new BalanceRefillHandler(clientService));
        server.start();
    }
}
