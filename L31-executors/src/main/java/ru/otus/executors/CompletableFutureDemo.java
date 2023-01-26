package ru.otus.executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class CompletableFutureDemo {
    private static final Logger logger = LoggerFactory.getLogger(CompletableFutureDemo.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //simpleAsync();
        asyncThen();
        //asyncError();
        //asyncAcceptBoth();
        //asyncAcceptEither();
    }

    private static void simpleAsync() throws ExecutionException, InterruptedException {
        logger.info("start");
        var future1 = CompletableFuture.supplyAsync(() -> task(1));
        logger.info("thread is not blocked");
        logger.info("result:{}", future1.get());
    }

    private static void asyncThen() {
        logger.info("start");
        var future2 = CompletableFuture.supplyAsync(() -> task(2));
        future2.thenAccept(val -> logger.info("result:{}", val));
        future2.join();
    }

    private static void asyncError() {
        var future3 = CompletableFuture.supplyAsync(CompletableFutureDemo::errorAction);
        future3.exceptionally(Throwable::getMessage).thenAccept(msg -> logger.info("msg:{}", msg));
    }

    private static void asyncAcceptBoth() {
        logger.info("start");
        var futureT1 = CompletableFuture.supplyAsync(() -> task(100));
        var futureT2 = CompletableFuture.supplyAsync(() -> task(200));
        var joinedResult = futureT1.thenAcceptBoth(futureT2, (s1, s2) -> logger.info("joinedResult: {}, {}", s1, s2));
        joinedResult.join();
    }

    private static void asyncAcceptEither() {
        logger.info("start");
        var futureT1 = CompletableFuture.supplyAsync(() -> {
            sleep(1);
            return task(100);
        }
    );
    var futureT2 = CompletableFuture.supplyAsync(() -> task(200));
    var firstResult = futureT1.acceptEither(futureT2, s -> logger.info("firstResult: {}", s));
    firstResult.join();
}

    private static String errorAction() {
        throw new RuntimeException("error for Test");
    }

    private static String task(int id) {
        sleep(5);
        logger.info("task is done: {}", id);
        return "done" + id;
    }

    private static void sleep(int seconds) {
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(seconds));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
