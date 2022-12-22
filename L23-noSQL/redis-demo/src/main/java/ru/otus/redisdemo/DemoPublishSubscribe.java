package ru.otus.redisdemo;

import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class DemoPublishSubscribe {
    private static final Logger logger = LoggerFactory.getLogger(DemoPublishSubscribe.class);
    private static final String MESSAGE_QUEUE_CHANNEL_NAME = "message-queue";

    private static final int REDIS_PORT = 6379;
    private static final String REDIS_HOST = "localhost"; // Работа без DockerToolbox
    //private static final String REDIS_HOST = "192.168.99.100"; // Работа через DockerToolbox

    public static void main(String[] args) throws Throwable {
        val publisher = new Jedis(REDIS_HOST, REDIS_PORT);
        val subscriber = new Jedis(REDIS_HOST, REDIS_PORT);

        val latch = new CountDownLatch(5);
        val executorService = Executors.newScheduledThreadPool(1);

        executorService.scheduleAtFixedRate(() -> publisher.publish(MESSAGE_QUEUE_CHANNEL_NAME, "I believe"),
                5,
                1,
                TimeUnit.SECONDS);

        subscriber.subscribe(new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                logger.info("{}, I can fly!", message);
                latch.countDown();
                if (latch.getCount() == 0) {
                    this.unsubscribe();
                }
            }
        },MESSAGE_QUEUE_CHANNEL_NAME);

        latch.await();
        executorService.shutdown();
    }
}
