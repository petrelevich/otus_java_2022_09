package ru.otus;

import java.util.HashMap;

public class MyMapDemo {

    public static void main(String[] args) {

        var mapSize = 200_000;
        var keyStr = "k";
        ///////////

        long sum1 = 0;
        var hashMap = new HashMap<String, Integer>(mapSize);
        long begin = System.currentTimeMillis();

        for (var idx = 0; idx < mapSize; idx++) {
            hashMap.put(keyStr + idx, idx);
        }

        for (var idx = 0; idx < mapSize; idx++) {
            sum1 += hashMap.get(keyStr + idx);
        }
        System.out.println("HashMap time:" + (System.currentTimeMillis() - begin));

        ////
        System.out.println("-----");
        long sum2 = 0;
        var myMap = new MyMapInt(mapSize);
        begin = System.currentTimeMillis();

        for (var idx = 0; idx < mapSize; idx++) {
            myMap.put(keyStr + idx, idx);
        }

        for (var idx = 0; idx < mapSize; idx++) {
            sum2 += myMap.get(keyStr + idx);
        }
        System.out.println("MyMapInt time:" + (System.currentTimeMillis() - begin));

        System.out.println("sum1:" + sum1 + ", sum2:" + sum2);
    }
}
