package ru.otus.testing.example;

import ru.otus.testing.example.services.*;

public class Main {

    public static void main(String[] args) {
        IOService closedConsoleIOService = new ClosedConsoleIOService();
        IOService openedConsoleIOService = new OpenedStreamsIOService(System.out, System.in);

        closedConsoleIOService.out("Hello World");
        openedConsoleIOService.out("Hello World 2");
/*

        CalculatorService calculatorService = new CalculatorServiceImpl(openedConsoleIOService);
        calculatorService.readTwoDigitsAndMultiply();
        openedConsoleIOService.out("---------------------------------------------------------");
        calculatorService.readTwoDigitsAndMultiply("Введите два числа и мы их перемножим!");
        openedConsoleIOService.out("---------------------------------------------------------");
        calculatorService.multiplyTwoDigits("Мы перемножили для вас два числа. Результат смотри ниже", 53, 42);
        openedConsoleIOService.out("---------------------------------------------------------");
*/
    }
}
