package ru.otus.mongodemo;

import com.mongodb.client.result.InsertOneResult;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;
import lombok.val;
import org.bson.Document;
import ru.otus.mongodemo.helpers.ReactiveMongoHelper;
import ru.otus.mongodemo.subscribers.ObservableSubscriber;

import java.util.List;

public class Demo {

    public static final String MONGODB_URL = "mongodb://localhost:30001"; // Работа без DockerToolbox
    //public static final String MONGODB_URL = "mongodb://192.168.99.100:30001"; // Работа через DockerToolbox
    private static final String DB_NAME = "mongo-db-test";
    private static final String PRODUCTS_COLLECTION = "products";

    public static void main(String[] args) throws Throwable {
        try (MongoClient mongoClient = MongoClients.create(MONGODB_URL)) {
            MongoDatabase database = mongoClient.getDatabase(DB_NAME);
            MongoCollection<Document> collection = database.getCollection(PRODUCTS_COLLECTION);

            System.out.println("\n");

            ReactiveMongoHelper.dropDatabase(database);

            doInsertAndFindDemo(collection);

            System.out.println("\n");
        }
    }

    private static void doInsertAndFindDemo(MongoCollection<Document> collection) throws Throwable {
        val doc = new Document("key", System.currentTimeMillis())
                .append("item", "apple")
                .append("qty", 112);

        System.out.println("Insert one document");
        val subscriber = new ObservableSubscriber<InsertOneResult>();
        collection.insertOne(doc).subscribe(subscriber);

        // Что-то очень важное

        subscriber.await();

        System.out.println();

        System.out.println("Find all apples");
        val subscriberPrinter = new ObservableSubscriber<Document>();
        collection.find(Document.parse("{\"item\":\"apple\"}")).subscribe(subscriberPrinter);
        subscriberPrinter.await();

        List<Document> results = subscriberPrinter.getResult();
        System.out.println(String.format("result.size: %d", results.size()));
    }
}
