package ru.outs.chain;


public class Demo {
    public static void main(String[] args) {
        var appl = new Application();

        var input = new ApplicationInput();
        var reader = new ApplicationReader();
        var result = new ApplicationResult();

        input.setNext(reader);
        reader.setNext(result);

        input.process(appl);
        appl.printHistory();
    }
}
