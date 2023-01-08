package ru.otus.services;

import ru.otus.domain.Client;

import java.util.List;

public interface ClientService {
    List<Client> findAll();
    Client findById(long id);
    Client findByName(String name);
    Client findRandom();
    Client save(Client client);
}
