package ru.otus;

public class Main {
    public static void main(String[] args) {
        TestLoggingInterface testLoggingInterface = Creator.create(new TestLogging());
        testLoggingInterface.calculation(1);
        testLoggingInterface.calculation(1,2);
        testLoggingInterface.calculation(1,2,3);
    }
}
