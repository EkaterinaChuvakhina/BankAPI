package ru.sberstart.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import ru.sberstart.dto.BalanceRefillDto;
import ru.sberstart.service.ClientService;

import java.io.IOException;

public class BalanceRefillHandler extends AbstractHandler {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final ClientService clientService;

    public BalanceRefillHandler(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    protected void doPost(HttpExchange exchange) {
        BalanceRefillDto balanceRefillDto = null;
        try {
            balanceRefillDto = MAPPER.readValue(exchange.getRequestBody(), BalanceRefillDto.class);
            clientService.refillBalance(balanceRefillDto);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doGet(HttpExchange exchange) {

    }
}

