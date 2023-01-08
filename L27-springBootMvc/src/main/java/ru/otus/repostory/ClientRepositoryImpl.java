package ru.otus.repostory;

import org.springframework.stereotype.Repository;
import ru.otus.domain.Client;
import ru.otus.generators.ClientIdGenerator;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ClientRepositoryImpl implements ClientRepository {

    private final List<Client> clients = new ArrayList<>();
    private final ClientIdGenerator idGenerator;

    public ClientRepositoryImpl(ClientIdGenerator idGenerator) {
        this.idGenerator = idGenerator;

        clients.add(new Client(idGenerator.generateId(), "Крис Гир"));
        clients.add(new Client(idGenerator.generateId(), "Ая Кэш"));
        clients.add(new Client(idGenerator.generateId(), "Десмин Боргес"));
        clients.add(new Client(idGenerator.generateId(), "Кетер Донохью"));
        clients.add(new Client(idGenerator.generateId(), "Стивен Шнайдер"));
        clients.add(new Client(idGenerator.generateId(), "Джанет Вэрни"));
        clients.add(new Client(idGenerator.generateId(), "Брэндон Смит"));
    }

    @Override
    public List<Client> findAll() {
        return clients;
    }

    @Override
    public Client save(Client client) {
        client.setId(idGenerator.generateId());
        clients.add(client);
        return client;
    }

    @Override
    public Client findById(long id) {
        return clients.stream().filter(u -> u.getId() == id).findFirst().orElse(null);
    }

    @Override
    public Client findByName(String name) {
        return clients.stream().filter(u -> u.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}
