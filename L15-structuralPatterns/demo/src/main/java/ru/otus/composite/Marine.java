package ru.otus.composite;

/**
 * Морской пехотинец.
 */
public class Marine implements Unit {
    @Override
    public void move() {
        System.out.println("Marine is moving");
    }
}
