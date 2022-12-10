package ru.otus.bridge;

public abstract class Card {
    private final PaymentSystem paymentSystem;

    Card(PaymentSystem paymentSystem) {
        this.paymentSystem = paymentSystem;
    }

    public void info() {
        paymentSystem.printName();
        cardType();
    }

    protected abstract void cardType();
}
