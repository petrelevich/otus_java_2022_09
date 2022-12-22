package ru.otus.cassandrademo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SmartPhone {
    private UUID id;
    private String model;
    private String color;
    private String serialNumber;

    private String operatingSystem;
}
