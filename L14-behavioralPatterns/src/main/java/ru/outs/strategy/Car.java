package ru.outs.strategy;

public class Car implements Strategy {
    @Override
    public void transportation() {
        System.out.println("доехать на машине");
    }
}
