package ru.otus.str;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Cache {
    private static final Logger logger = LoggerFactory.getLogger(Cache.class);

    private final Map<String, Data> dataStore = new HashMap<>();

    public static void main(String[] args) {
        var cache = new Cache();
        cache.fillCache();
        cache.go();
    }

    private void fillCache() {
        var data = new ArrayList<String>();
        for (var idx = 0; idx < 10; idx++) {
            data.add("v" + idx);
        }
        dataStore.put("k1", new Data(1, data));
        dataStore.put("k2", new Data(2, data));
        dataStore.put("k3", new Data(3, data));
    }

    private void go() {
        final Data d1 = dataStore.get("k1");
        DataProcessor.process(d1);
        final Data d12 = dataStore.get("k2"); // Обратите внимение, что ключ другой
        logger.info("key:{}, values: {}", d12.getId(), d12.getValues());
    }
}
