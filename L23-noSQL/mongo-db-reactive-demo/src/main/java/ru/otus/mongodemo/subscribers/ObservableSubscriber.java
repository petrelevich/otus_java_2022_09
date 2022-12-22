package ru.otus.mongodemo.subscribers;

import com.mongodb.MongoTimeoutException;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static java.util.concurrent.TimeUnit.MINUTES;

public class ObservableSubscriber<T> implements Subscriber<T> {
    private final CountDownLatch latch = new CountDownLatch(1);
    private volatile Throwable error;
    private List<T> result = new ArrayList<>();

    private final boolean printOnNextRecord;

    public ObservableSubscriber() {
        printOnNextRecord = true;
    }

    public ObservableSubscriber(boolean printOnNextRecord) {
        this.printOnNextRecord = printOnNextRecord;
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        subscription.request(Integer.MAX_VALUE);
    }

    @Override
    public void onNext(T record) {
        result.add(record);
        if (printOnNextRecord) {
            System.out.println(String.format("onNext, result: %s", record));
        }
    }

    @Override
    public void onError(Throwable t) {
        error = t;
        System.err.println(t.getMessage());
        onComplete();
    }

    @Override
    public void onComplete() {
        latch.countDown();
    }

    public void await() throws Throwable {
        if (!latch.await(10, MINUTES)) {
            throw new MongoTimeoutException("Publisher timed out");
        }
        if (error != null) {
            throw error;
        }
    }

    public List<T> getResult() {
        return result;
    }
}
