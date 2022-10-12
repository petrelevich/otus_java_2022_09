package ru.otus;

import java.util.ArrayList;

public class MyArrayDemo {

    public static void main(String[] args) throws Exception {
        var arraySizeMax = 1_000_000;
        var arraySizeInit = 10;
        ///////////
        long sum1 = 0;
        try (var myArr = new MyArrayInt(arraySizeInit)) {
            var begin = System.currentTimeMillis();

            for (var idx = 0; idx < arraySizeMax; idx++) {
                myArr.setValue(idx, idx);
            }

            for (var idx = 0; idx < arraySizeMax; idx++) {
                sum1 += myArr.getValue(idx);
            }
            System.out.println("myArr time:" + (System.currentTimeMillis() - begin));
        }
        ////
        System.out.println("-----");
        long sum2 = 0;
        var arrayList = new ArrayList<Integer>(arraySizeInit);
        var begin = System.currentTimeMillis();

        for (var idx = 0; idx < arraySizeMax; idx++) {
            arrayList.add(idx, idx);
        }

        for (var idx = 0; idx < arraySizeMax; idx++) {
            sum2 += arrayList.get(idx);
        }
        System.out.println("ArrayList time:" + (System.currentTimeMillis() - begin));
        System.out.println("sum1:" + sum1 + ", sum2:" + sum2);
    }
}
