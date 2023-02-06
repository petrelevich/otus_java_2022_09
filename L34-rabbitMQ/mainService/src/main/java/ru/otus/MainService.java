package ru.otus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan("ru.otus.domain")
@SpringBootApplication
public class MainService {
    public static void main(String[] args) {
        SpringApplication.run(MainService.class, args);
    }

}
