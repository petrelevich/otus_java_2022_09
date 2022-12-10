package ru.otus.singleton;

public class FirstSingleton {
    private static FirstSingleton instance = new FirstSingleton();

    // ! private constructor
    private FirstSingleton() {
        System.out.println("run constructor");
    }

    // public API
    public static FirstSingleton getInstance() {
        return instance;
    }
}

class FirstSingletonDemo {
    public static void main(String[] args) {
        System.out.println("--- begin ---");

        FirstSingleton singleton1 = FirstSingleton.getInstance();
        FirstSingleton singleton2 = FirstSingleton.getInstance();

        System.out.println(singleton1);
        System.out.println(singleton2);

        System.out.printf("singleton1 == singleton2 => %b\n", singleton1 == singleton2);
        System.out.println("---end ---");
    }
}