package ru.otus.protobuf.service;

import ru.otus.protobuf.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RealDBServiceImpl implements RealDBService {
    private final AtomicInteger idCounter;
    private final List<User> users;

    public RealDBServiceImpl() {
        idCounter = new AtomicInteger(0);
        users = new ArrayList<>();
        users.add(new User(idCounter.incrementAndGet(), "Дима", "Жмых"));
        users.add(new User(idCounter.incrementAndGet(), "Оля", "Фитоняшкина"));
    }

    @Override
    public User saveUser(String firstName, String lastName) {
        User user = new User(idCounter.incrementAndGet(), firstName, lastName);
        users.add(user);
        return user;
    }

    @Override
    public List<User> findAllUsers() {
        return users;
    }
}
