package ru.otus.mongodemo.helpers;

import com.mongodb.reactivestreams.client.MongoDatabase;
import lombok.val;
import ru.otus.mongodemo.subscribers.ObservableSubscriber;

public final class ReactiveMongoHelper {

    private ReactiveMongoHelper() {
    }

    public static void dropDatabase(MongoDatabase database) throws Throwable {
        val subscriber = new ObservableSubscriber<Void>(false);
        database.drop().subscribe(subscriber);
        subscriber.await();
    }

}
