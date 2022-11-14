package ru.outs.command;

public class SomeObject {
    private String value;

    public SomeObject(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "SomeObject{" +
                "value='" + value + '\'' +
                '}';
    }
}
