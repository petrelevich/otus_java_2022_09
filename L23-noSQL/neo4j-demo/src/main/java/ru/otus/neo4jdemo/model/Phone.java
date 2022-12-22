package ru.otus.neo4jdemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Phone {
    private String id;
    private String model;
    private String color;
    private String serialNumber;
}
