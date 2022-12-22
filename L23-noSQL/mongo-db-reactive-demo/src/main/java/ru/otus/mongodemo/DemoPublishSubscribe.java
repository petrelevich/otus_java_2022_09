package ru.otus.mongodemo;

import com.mongodb.client.result.InsertOneResult;
import com.mongodb.reactivestreams.client.*;
import lombok.val;
import org.bson.Document;
import ru.otus.mongodemo.helpers.ReactiveMongoHelper;
import ru.otus.mongodemo.subscribers.ObservableSubscriber;
import ru.otus.mongodemo.subscribers.ObservableSubscriberChangeDocument;

import java.util.concurrent.TimeUnit;

public class DemoPublishSubscribe {

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

            doWriteAndReadDemo(collection);

            System.out.println("\n");
        }
    }
    private static void doWriteAndReadDemo(MongoCollection<Document> collection) throws Throwable {
        startWritingToCollectionInANewThread(collection);
        subscribeForCollectionChanges(collection);
    }

    private static void startWritingToCollectionInANewThread(MongoCollection<Document> collection) {
        val thread = new Thread(() -> {
            try {
                int counter = 0;
                val subscriber = new ObservableSubscriber<InsertOneResult>();
                while (true) {
                    Thread.sleep(TimeUnit.SECONDS.toMillis(3));
                    System.out.println(String.format("counter: %d", counter));
                    val doc = new Document("key", System.currentTimeMillis())
                            .append("item", "apple")
                            .append("counter", counter++)
                            .append("qty", 11);

                    collection.insertOne(doc).subscribe(subscriber);
                    subscriber.await();
                }
            } catch (Throwable ex) {
                System.err.println(ex.getMessage());
            }
        });

        thread.setName("Writer");
        thread.start();
    }

    private static void subscribeForCollectionChanges(MongoCollection<Document> collection) throws Throwable {
        // Create the change stream publisher.
        ChangeStreamPublisher<Document> publisher = collection.watch();

        // Create a subscriber
        val subscriber = new ObservableSubscriberChangeDocument();
        publisher.subscribe(subscriber);

        subscriber.await();
    }
}
