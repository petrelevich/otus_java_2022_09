package ru.outs.observer;

public class ConsumerB {

    /*
     * Большой и жирный класс со множеством полей
     *
     */

    private static final Listener listener = data -> System.out.println("ConsumerB data:" + data);


    public Listener getListener() {
        return listener;
    }
}
