package ru.otus.neo4jdemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class PhoneUser {
    private String id;
    private String name;

    private List<Phone> phones;
}
