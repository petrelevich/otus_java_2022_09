package ru.otus.neo4jdemo.repository;

import ru.otus.neo4jdemo.model.Phone;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PhoneRepository {
    void insert(Phone phone);

    Optional<Phone> findOne(String id);

    List<Phone> findAll();

    List<Phone> findAllByUserId(String userId);
}
