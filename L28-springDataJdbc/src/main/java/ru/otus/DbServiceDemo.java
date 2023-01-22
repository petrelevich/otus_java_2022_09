package ru.otus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class DbServiceDemo {
    public static void main(String[] args) {
        var context = SpringApplication.run(DbServiceDemo.class, args);

        context.getBean("actionDemo", ActionDemo.class).action();
    }
}
