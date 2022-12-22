package ru.otus.mongodemo.template;

import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

public interface MongoTemplate {
    <T> void insert(T value);

    <T> Optional<T> findOne(ObjectId id, Class<T> tClass);

    <T> List<T> find(Bson query, Class<T> tClass);
    <T> List<T> findAll(Class<T> tClass);
}
