package ru.otus.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadDemo {
    private static final Logger logger = LoggerFactory.getLogger(ThreadDemo.class);

    public static void main(String[] args) {
        case1();
        case2();
    }

    private static void case1() {
        logger.info("{}. Main program started", Thread.currentThread().getName());

        var thread = new Thread(
                () -> logger.info("from thread:{}", Thread.currentThread().getName()));
        thread.start();

        logger.info("{}. Main program finished", Thread.currentThread().getName());
    }

    private static void case2() {
        logger.info("{}. Main program started", Thread.currentThread().getName());

        var thread = new CustomThread();
        thread.start();

        logger.info("{}. Main program finished", Thread.currentThread().getName());
    }

    private static class CustomThread extends Thread {
        @Override
        public void run() {
            logger.info("from thread: {}", Thread.currentThread().getName());
        }
    }
}
