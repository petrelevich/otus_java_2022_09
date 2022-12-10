package ru.otus.io;

import java.io.Serializable;


//implements Serializable - обязательное условие для сериализации
public class Person implements Serializable {
    private static final long serialVersionUID = 1L; // не забывайте про serialVersionUID
    private final int age;
    private final String name;
    private final transient String hidden; //transient - поле будет пропущено при сериализации

  //  public String newField ="ddd";  //опыт с полем

    // Обратите внимание на то, сколько раз вызывается констурктор и сколько объектов создается
    Person(int age, String name, String hidden) {
        System.out.println("new Person");
        this.age = age;
        this.name = name;
        this.hidden = hidden;
    }

    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", hidden='" + hidden + '\'' +
 //               ", newField='" + newField + '\'' +
                '}';
    }
}
