package ru.otus.neo4jdemo.repository;

import ru.otus.neo4jdemo.model.Phone;
import ru.otus.neo4jdemo.model.PhoneUser;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PhoneUserRepository {
    void insert(PhoneUser phoneUser);

    Optional<PhoneUser> findOne(String id);

    List<PhoneUser> findAll();
}
