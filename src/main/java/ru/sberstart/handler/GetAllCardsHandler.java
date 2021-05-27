package ru.sberstart.handler;

import com.sun.net.httpserver.HttpExchange;
import ru.sberstart.dto.CardDto;
import ru.sberstart.service.ClientService;

import java.util.List;
import java.util.Map;

public class GetAllCardsHandler extends AbstractHandler {
    private final ClientService clientService;

    public GetAllCardsHandler(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    protected List<CardDto> doGet(HttpExchange exchange) {
        Map<String, String> parameters = queryToMap(exchange.getRequestURI().getQuery());
        Long clientId = Long.parseLong(parameters.get("client_id"));
        return clientService.getAllCards(clientId);
    }
}
