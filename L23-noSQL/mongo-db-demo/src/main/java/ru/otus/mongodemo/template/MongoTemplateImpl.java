package ru.otus.mongodemo.template;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;

@RequiredArgsConstructor
public class MongoTemplateImpl implements MongoTemplate {
    private final String collectionName;
    private final MongoDatabase database;

    @SuppressWarnings("unchecked")
    @Override
    public <T> void insert(T value) {
        MongoCollection<T> collection = (MongoCollection<T>) database.getCollection(collectionName, value.getClass());
        collection.insertOne(value);
    }

    @Override
    public <T> Optional<T> findOne(ObjectId id, Class<T> tClass) {
        MongoCollection<T> collection = database.getCollection(collectionName, tClass);
        val res = collection.find(eq("_id", id)).first();
        return Optional.ofNullable(res);
    }

    @Override
    public <T> List<T> find(Bson query, Class<T> tClass) {
        MongoCollection<T> collection = database.getCollection(collectionName, tClass);
        val iterable = collection.find(query);
        val res = new ArrayList<T>();
        iterable.forEach(res::add);
        return res;
    }

    @Override
    public <T> List<T> findAll(Class<T> tClass)  {
        return find(Document.parse("{}"), tClass);
    }
}
