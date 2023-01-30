package ru.otus.api.model;


import java.time.LocalDateTime;

public class SensorData {
    private final LocalDateTime measurementTime;
    private final String room;
    private final Double value;

    public SensorData(LocalDateTime measurementTime, String room, Double value) {
        this.measurementTime = measurementTime;
        this.room = room;
        this.value = value;
    }

    public LocalDateTime getMeasurementTime() {
        return measurementTime;
    }

    public String getRoom() {
        return room;
    }

    public Double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "SensorData{" +
                "measurementTime=" + measurementTime +
                ", room='" + room + '\'' +
                ", value=" + value +
                '}';
    }
}
