package ru.otus.locks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {
    private static final Logger logger = LoggerFactory.getLogger(ReentrantLockDemo.class);

    private final Lock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        new ReentrantLockDemo().go();
    }

    private void go() throws InterruptedException {
        var t1 = new Thread(this::criticalSection);
        t1.setName("t1");
        t1.start();

        var t2 = new Thread(this::criticalSection);
        t2.setName("t2");
        t2.start();

        t1.join();
        t2.join();
    }

    private void criticalSection() {
        logger.info("before critical section");
        lock.lock();
        try {
            logger.info("in the critical section");
            sleep();
        } finally {
            lock.unlock();
        }
        logger.info("after critical section");
    }

    private void criticalSectionDoubleLock() {
        logger.info("before critical section");
        lock.lock();
        logger.info("reentrant into locked section");
        lock.lock();
        try {
            logger.info("in the critical section");
            sleep();
        } finally {
            //сколько раз заблокировали, столько надо и разблокировать
            lock.unlock();
            lock.unlock();
        }
        logger.info("after critical section");
    }


    private static void sleep() {
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(10));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
