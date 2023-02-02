package ru.otus.protobuf.service;

import ru.otus.protobuf.model.User;

import java.util.List;

public interface RealDBService {
    User saveUser(String firstName, String lastName);
    List<User> findAllUsers();
}
