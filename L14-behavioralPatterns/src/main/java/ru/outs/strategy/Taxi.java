package ru.outs.strategy;

public class Taxi implements Strategy {
    @Override
    public void transportation() {
        System.out.println("доехать на такси");
    }
}
