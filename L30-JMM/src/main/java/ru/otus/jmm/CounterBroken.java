package ru.otus.jmm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CounterBroken {
    private static final Logger logger = LoggerFactory.getLogger(CounterBroken.class);
    private int count = 0;
    private static final int LIMIT = 100_000_000;

    public static void main(String[] args) throws InterruptedException {
        new CounterBroken().go();
    }

    private void inc() {
        for (var idx = 0; idx < LIMIT; idx++) {
            count++;
        }
    }

    private void go() throws InterruptedException {
        var thread1 = new Thread(this::inc);
        var thread2 = new Thread(this::inc);
        var thread3 = new Thread(this::inc);

        thread1.start();
        thread2.start();
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();
        logger.info("CounterBroken:{}", count);
    }
}
