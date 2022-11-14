package ru.outs.state;

public class Demo {
    public static void main(String[] args) {

        var context = new BulbContext();
        context.performAction();

        context.performAction();

        context.performAction();
    }
}
