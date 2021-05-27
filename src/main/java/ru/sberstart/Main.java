package ru.sberstart;

import com.sun.net.httpserver.HttpServer;
import ru.sberstart.dao.AccountDao;
import ru.sberstart.dao.AccountDaoJdbcImpl;
import ru.sberstart.dao.CardDao;
import ru.sberstart.dao.CardDaoJdbcImpl;
import ru.sberstart.handler.BalanceRefillHandler;
import ru.sberstart.handler.CheckBalanceHandler;
import ru.sberstart.handler.CreateCardHandler;
import ru.sberstart.handler.GetAllCardsHandler;
import ru.sberstart.repository.ClientRepository;
import ru.sberstart.service.AccountService;
import ru.sberstart.service.CardService;
import ru.sberstart.service.ClientService;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) throws IOException {
        DatabaseConfiguration databaseConfiguration = new DatabaseConfiguration();
        AccountDao accountDao = new AccountDaoJdbcImpl(databaseConfiguration.getDatasource());
        AccountService accountService = new AccountService(accountDao);
        CardDao cardDao = new CardDaoJdbcImpl(databaseConfiguration.getDatasource());

        CardService cardService = new CardService(cardDao);

        ClientRepository clientRepository = new ClientRepository();
        ClientService clientService = new ClientService(clientRepository, cardService, accountService);


        HttpServer server = HttpServer.create();
        server.bind(new InetSocketAddress(8081), 0);
        server.createContext("/client/card/create", new CreateCardHandler(clientService));
        server.createContext("/client/cards", new GetAllCardsHandler(clientService));
        server.createContext("/client/balance", new CheckBalanceHandler(clientService));
        server.createContext("/client/refill-balance", new BalanceRefillHandler(clientService));
        server.start();
    }
}
