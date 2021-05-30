package ru.sberstart;

import com.sun.net.httpserver.HttpServer;
import ru.sberstart.configuration.DatabaseConfiguration;
import ru.sberstart.dao.AccountDao;
import ru.sberstart.dao.JdbcAccountDao;
import ru.sberstart.dao.CardDao;
import ru.sberstart.dao.JdbcCardDao;
import ru.sberstart.handler.BalanceRefillHandler;
import ru.sberstart.handler.CheckBalanceHandler;
import ru.sberstart.handler.CreateCardHandler;
import ru.sberstart.handler.GetAllCardsHandler;
import ru.sberstart.service.AccountService;
import ru.sberstart.service.CardNumberGenerator;
import ru.sberstart.service.CardService;
import ru.sberstart.service.ClientService;
import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) throws IOException {
        new DatabaseConfiguration().startupH2();
        AccountDao accountDao = new JdbcAccountDao();
        AccountService accountService = new AccountService(accountDao);
        CardDao cardDao = new JdbcCardDao();

        CardNumberGenerator cardNumberGenerator = new CardNumberGenerator();
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
