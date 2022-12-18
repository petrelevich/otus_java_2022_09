package ru.otus.references;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.WeakHashMap;

public class WeakMapDemo {
    private static final Logger logger = LoggerFactory.getLogger(WeakMapDemo.class);

    public static void main(String[] args) throws InterruptedException {
        new WeakMapDemo().start();
        // new WeakMapDemo().startStrange();
    }

    private void start() throws InterruptedException {
        Map<String, Integer> cache = new WeakHashMap<>();
        var limit = 100;

        var d = "key:21";  // для для демонстрации intern
        var d2 = "key:22"; // для для демонстрации intern
        for (var idx = 0; idx < limit; idx++) {
            var key = ("key:" + idx).intern(); // останутся значения для "key:21" и "key:22"
            // var key = "key:" + idx; // при таком подходе значение удалится.
            cache.put(key, idx);
        }

        logger.info("before gc: {}", cache.size());
        for (Map.Entry<String, Integer> element : cache.entrySet()) {
            logger.info("key:{}, value:{}", element.getKey(), element.getValue());
        }

        System.gc();
        Thread.sleep(100);
        logger.info("after gc: {}", cache.size());

        for (Map.Entry<String, Integer> element : cache.entrySet()) {
            logger.info("key:{}, value:{}", element.getKey(), element.getValue());
        }
    }


    private void startStrange() throws InterruptedException {
        Map<Integer, Integer> cache = new WeakHashMap<>();
        var limit = 100;
        for (var idx = 0; idx < limit; idx++) {
            cache.put(idx, idx);
        }

        logger.info("before gc: {}", cache.size());
        for (Map.Entry<Integer, Integer> element : cache.entrySet()) {
            logger.info("key:{}, value:{}", element.getKey(), element.getValue());
        }

        System.gc();
        Thread.sleep(100);
        logger.info("\n\nafter gc: {}", cache.size());

        for (Map.Entry<Integer, Integer> element : cache.entrySet()) {
            logger.info("key:{}, value:{}", element.getKey(), element.getValue());
        }
    }
}
