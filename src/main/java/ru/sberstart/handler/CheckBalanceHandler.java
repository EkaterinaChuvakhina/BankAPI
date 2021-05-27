package ru.sberstart.handler;

import com.sun.net.httpserver.HttpExchange;
import ru.sberstart.dto.AccountDto;
import ru.sberstart.service.ClientService;

import java.util.Map;

public class CheckBalanceHandler extends AbstractHandler {
    private final ClientService clientService;

    public CheckBalanceHandler(ClientService clientService) {
        this.clientService = clientService;
    }


    @Override
    protected AccountDto doGet(HttpExchange exchange) {
        Map<String, String> parameters = queryToMap(exchange.getRequestURI().getQuery());
        Long parameter = Long.parseLong(parameters.get("account_id"));
        return clientService.checkBalance(parameter);
    }
}
