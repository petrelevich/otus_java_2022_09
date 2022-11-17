package ru.otus.bridge;

public class MirPS implements PaymentSystem {
    @Override
    public void printName() {
        System.out.println("Mir");
    }
}
