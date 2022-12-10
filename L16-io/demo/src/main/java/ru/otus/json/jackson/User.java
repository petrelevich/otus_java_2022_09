package ru.otus.json.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties({"transientProperty"})
public class User {
    private int age;

    @JsonProperty("nameForSerialization")
    private String name;


    private String transientProperty = "lostData";

    @JsonCreator
    public User(@JsonProperty("age") int age, @JsonProperty("nameForSerialization") String name) {
        System.out.println("JsonCreator makes object...");
        this.age = age;
        this.name = name;
    }


    User(int age, String name, String transientProperty) {
        this.age = age;
        this.name = name;
        this.transientProperty = transientProperty;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTransientProperty() {
        return transientProperty;
    }

    public void setTransientProperty(String transientProperty) {
        this.transientProperty = transientProperty;
    }

    @Override
    public String toString() {
        return "User{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", transientProperty='" + transientProperty + '\'' +
                '}';
    }
}
