package ru.sberstart.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import ru.sberstart.dto.CardDto;
import ru.sberstart.dto.CreateCardDto;
import ru.sberstart.service.ClientService;

import java.io.IOException;

public class CreateCardHandler extends AbstractHandler {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final ClientService clientService;

    public CreateCardHandler(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    protected CardDto doPost(HttpExchange exchange) {
        try {
            CreateCardDto createCardDto = MAPPER.readValue(exchange.getRequestBody(),
                    CreateCardDto.class);
            return clientService.createNewCard(createCardDto);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
