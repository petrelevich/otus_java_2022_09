package ru.otus.services;

import org.springframework.stereotype.Service;
import ru.otus.domain.Client;
import ru.otus.repostory.ClientRepository;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final RabbitMqService rabbitMqService;

    public ClientServiceImpl(ClientRepository clientRepository, RabbitMqService rabbitMqService) {
        this.clientRepository = clientRepository;
        this.rabbitMqService = rabbitMqService;
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client save(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Client saveAndSendForApprove(Client client) {
        var savedClient = save(client);
        rabbitMqService.sendClientCreatedEvent(savedClient);
        return savedClient;
    }










    @Override
    public Client saveAndSendForApproveAndWait(Client client) {
        var savedClient = save(client);
        savedClient = rabbitMqService.sendClientCreatedEventAndWait(savedClient);
        savedClient = save(savedClient);
        return savedClient;
    }
}
