package ru.sberstart.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import org.mapstruct.factory.Mappers;
import ru.sberstart.dto.CreateCardDto;
import ru.sberstart.entity.Account;
import ru.sberstart.mapper.AccountMapper;
import ru.sberstart.service.ClientService;

import java.io.IOException;

public class CreateCardHandler extends AbstractHandler {
    private final AccountMapper accountMapper = Mappers.getMapper(AccountMapper.class);
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final ClientService clientService;

    public CreateCardHandler(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    protected void doPost(HttpExchange exchange) {
        CreateCardDto createCardDto = null;
        try {
            createCardDto = MAPPER.readValue(exchange.getRequestBody(),
                    CreateCardDto.class);
            Account account = accountMapper.toAccount(createCardDto);
            clientService.createNewCard(account);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doGet(HttpExchange exchange) {

    }
}
