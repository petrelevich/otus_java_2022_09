package ru.otus.json.gson;

import java.util.Objects;

public class BagOfPrimitives {
    private final int value1;
    private final String value2;
    private final int value3;

    BagOfPrimitives(int value1, String value2, int value3) {
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
    }

    @Override
    public String toString() {
        return "BagOfPrimitives{" +
                "value1=" + value1 +
                ", value2='" + value2 + '\'' +
                ", value3=" + value3 +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BagOfPrimitives that = (BagOfPrimitives) o;
        return value1 == that.value1 &&
                value3 == that.value3 &&
                Objects.equals(value2, that.value2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value1, value2, value3);
    }
}
