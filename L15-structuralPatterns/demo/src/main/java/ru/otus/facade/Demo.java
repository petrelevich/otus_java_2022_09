package ru.otus.facade;

public class Demo {
    public static void main(String[] args) {
        // Есть сложные системы
        var systemA = new HellSystemA();
        var systemB = new HellSystemB();

        // Передаем сложные системы в фасад
        var facade = new Facade(systemA, systemB);
        // Вызываем "простое" действие
        facade.simpleAction();
        // Работаем только с фасадом => проще изменять системы за фасадом
    }
}
