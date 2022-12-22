package ru.otus.redisdemo.template;

import java.util.List;
import java.util.Optional;

public interface RedisTemplate {
    <T> void insert(String id, T value);

    <T> Optional<T> findOne(String id, Class<T> tClass);

    <T> List<T> findAll(Class<T> tClass);
}
