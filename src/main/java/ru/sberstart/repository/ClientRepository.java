package ru.sberstart.repository;

import ru.sberstart.entity.Card;
import ru.sberstart.entity.Client;
import ru.sberstart.service.ClientService;

import java.util.ArrayList;
import java.util.List;

public class ClientRepository {
    List<Client> clientList = new ArrayList<>();

    public Client findById(Long id) {
        return new Client(1,"Katy","Chuvahina", "Mikhilovna",null);
    }

}
