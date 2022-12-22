package ru.otus.cassandrademo.db;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PhoneRepository {
    <T> void insert(T phone, Class<T> tClass);

    <T> Optional<T> findOne(UUID id, Class<T> tClass);

    <T> List<T> findAll(Class<T> tClass);
}
