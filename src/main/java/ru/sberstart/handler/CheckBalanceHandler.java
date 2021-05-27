package ru.sberstart.handler;

import com.sun.net.httpserver.HttpExchange;
import ru.sberstart.service.ClientService;

import java.util.Map;

public class CheckBalanceHandler extends AbstractHandler{
    private final ClientService clientService;

    public CheckBalanceHandler(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    protected void doPost(HttpExchange exchange) {

    }

    @Override
    protected void doGet(HttpExchange exchange) {
        Map<String,String> parameters = queryToMap(exchange.getRequestURI().getQuery());
        Long parameter = Long.parseLong(parameters.get("id_account"));
        //clientService.getAmount(parameter);
        System.out.println( clientService.getAmount(parameter));
    }
}
