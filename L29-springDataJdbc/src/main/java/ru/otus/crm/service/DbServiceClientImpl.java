package ru.otus.crm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.crm.model.Client;
import ru.otus.sessionmanager.TransactionManager;
import ru.otus.crm.repository.ClientRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DbServiceClientImpl implements DBServiceClient {
    private static final Logger log = LoggerFactory.getLogger(DbServiceClientImpl.class);

    private final TransactionManager transactionManager;
    private final ClientRepository clientRepository;

    public DbServiceClientImpl(TransactionManager transactionManager, ClientRepository clientRepository) {
        this.transactionManager = transactionManager;
        this.clientRepository = clientRepository;
    }

    @Override
    public Client saveClient(Client client) {
        return transactionManager.doInTransaction(() -> {
            var savedClient = clientRepository.save(client);
            log.info("saved client: {}", savedClient);
            return savedClient;
        });
    }

    @Override
    public Optional<Client> getClient(long id) {
        var clientOptional = clientRepository.findById(id);
        log.info("client: {}", clientOptional);
        return clientOptional;
    }

    @Override
    public List<Client> findAll() {
        var clientList = new ArrayList<Client>();
        clientRepository.findAll().forEach(clientList::add);
        log.info("clientList:{}", clientList);
        return clientList;
    }
}
