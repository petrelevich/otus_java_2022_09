package ru.otus.monitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MonitorDemo {
    private static final Logger logger = LoggerFactory.getLogger(MonitorDemo.class);

    private int count = 0;
    private static final int LIMIT = 100_000_000;
    private final Object monitor = new Object();

    public static void main(String[] args) throws InterruptedException {
        var counter = new MonitorDemo();
        counter.go();
    }

    //ошибочное импользование мониторов - у каждого потока свой монитор
    //что является монитором здесь?
    private void inc1() {
        synchronized (monitor) {
            for (int i = 0; i < LIMIT; i++) {
                count++;
            }
        }
    }

    //что является монитором здесь?
    private static synchronized void inc2(MonitorDemo demo) {
        for (int i = 0; i < LIMIT; i++) {
            demo.count++;
        }
    }

    //что является монитором здесь?
    private synchronized void inc3() {
        for (int i = 0; i < LIMIT; i++) {
            count++;
        }
    }

    private void go() throws InterruptedException {
        Thread thread1 = new Thread(this::inc1);
        Thread thread2 = new Thread(() -> inc2(this));
        Thread thread3 = new Thread(this::inc3);

        thread1.start();
        thread2.start();
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();
        logger.info("CounterBroken: {}", count);
    }
}
