package ru.otus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


import java.util.HashSet;
import org.junit.jupiter.api.Test;

class MyMapIntTest {

    @Test
    void putAndGet() {
        MyMapInt map = new MyMapInt(1);
        int value = 88;
        String key = "key";

        map.put(key, value);
        assertEquals(value, map.get(key));
    }

    @Test
    void putAndGetSequence() {
        int size = 10;
        String keyStr = "k";
        MyMapInt map = new MyMapInt(size);

        for (int idx = 0; idx < size; idx++) {
            map.put(keyStr + idx, idx);
        }

        for (int idx = 0; idx < size; idx++) {
            assertEquals(idx, map.get(keyStr + idx));
        }
    }

    @Test
    void getNotExists() {
        MyMapInt map = new MyMapInt(1);
        String key = "key";

        assertThrows(IndexOutOfBoundsException.class, () -> map.get(key));
    }

    @Test
    void calcHash() {
        var mapSize = 200_000;
        var keyStr = "k";

        var hashSetAll = new HashSet<>();
        var hashSetNeg = new HashSet<>();
        var hashSetAbs = new HashSet<>();
        for (var idx = 0; idx < mapSize; idx++) {
            var key = keyStr + idx;
            var hash = key.hashCode();
            hashSetAll.add(hash);
            if (hash < 0) {
                hashSetNeg.add(hash);
            }
            hashSetAbs.add(Math.abs(key.hashCode()));
        }
        System.out.println("hashSetAll:" + hashSetAll.size() +
                ", hashSetNeg:" + hashSetNeg.size() +
                ", hashSetAbs:" + hashSetAbs.size()
        );
    }
}
