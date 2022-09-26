package ru.otus;

import com.google.common.collect.ImmutableList;

public class HelloOtus {
    public static void main(String[] args) {
        ImmutableList.of(1, 2, 3, 4, 5, 6).forEach(System.out::println);
    }
}