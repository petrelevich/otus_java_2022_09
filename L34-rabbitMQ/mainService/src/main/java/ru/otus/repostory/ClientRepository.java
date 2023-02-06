package ru.otus.repostory;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.domain.Client;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {

    List<Client> findAll();

    Client save(Client client);
}
