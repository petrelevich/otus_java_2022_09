package ru.otus.factories.simplefactory;

import java.util.*;

public class DemoStaticFactoryMethod {
    public static void main(String[] args) {
        // Простая фабрика (simple factory) или статический фабричный метод (static factory method)
        // Решение проблемы отсутствия имени у конструктора.
        var str1 = String.valueOf(1);
        var str2 = String.valueOf(true);
        var str3 = String.valueOf('a');

        var opt1 = Optional.empty();
        var otp2 = Optional.of("Some String");
        var opt3 = Optional.ofNullable(null);


        Collection<?> originalCollection = null;
        List<Integer> originalList = null;
        Map<String, Integer> originalMap = null;
        var syncedCollection = Collections.synchronizedCollection(originalCollection);
        var syncedSet = Collections.synchronizedSet(new HashSet());
        var unmodifiableList = Collections.unmodifiableList(originalList);
        var unmodifiableMap = Collections.unmodifiableMap(originalMap);

        // Что создает этот конструктор? Что за null?
        Employee user1 = new Employee("Ivanov", null);

        // Так гораздо понятнее:
        Employee user2 = Employee.createWithDefaultDepartment("Ivanov");
        // Можно сделать конструктор приватным, чтобы запретить его использование
    }
}

class Employee {

    private final String name;
    private final String department;

    public Employee(String name, String department) {
        this.name = name;
        this.department = department;
    }

    public static Employee createWithDefaultDepartment(String name) {
        return new Employee(name, "IT");
    }
}
