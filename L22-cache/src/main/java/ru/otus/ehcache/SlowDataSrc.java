package ru.otus.ehcache;

import java.util.concurrent.TimeUnit;


class SlowDataSrc {

    private SlowDataSrc() {
    }

    static long getValue(int key) {
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(1));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return key;
    }
}
