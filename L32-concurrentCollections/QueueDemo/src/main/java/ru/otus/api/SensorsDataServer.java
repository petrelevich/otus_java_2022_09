package ru.otus.api;

import ru.otus.api.model.SensorData;

public interface SensorsDataServer {
    void onReceive(SensorData sensorData);
}
