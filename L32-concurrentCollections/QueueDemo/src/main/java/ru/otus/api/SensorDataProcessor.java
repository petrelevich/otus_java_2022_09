package ru.otus.api;


import ru.otus.api.model.SensorData;

public interface SensorDataProcessor {
    void process(SensorData data);

    default void onProcessingEnd() {
    }
}
