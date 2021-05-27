package ru.sberstart.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import org.mapstruct.factory.Mappers;
import ru.sberstart.mapper.AccountMapper;
import ru.sberstart.service.ClientService;

import java.io.IOException;
import java.util.Map;

public class GetAllCardsHandler extends AbstractHandler {
    private final AccountMapper accountMapper = Mappers.getMapper(AccountMapper.class);
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final ClientService clientService;

    public GetAllCardsHandler(ClientService clientService) {
        this.clientService = clientService;
    }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String receivedRequestMethod = exchange.getRequestMethod();
            if ("GET".equals(receivedRequestMethod)) {
                doGet(exchange);
            } else if ("POST".equals(receivedRequestMethod)) {
                doPost(exchange);
            }
            exchange.getRequestBody();
        }

    @Override
    protected void doPost(HttpExchange exchange) {

    }

    @Override
    protected void doGet(HttpExchange exchange) {
        Map<String,String> parameters = queryToMap(exchange.getRequestURI().getQuery());
        Long parameter = Long.parseLong(parameters.get("id_client"));
        clientService.getAllCards(parameter);
    }



}
