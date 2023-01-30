package ru.otus.lib;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.api.model.SensorData;

import java.util.List;
import java.util.stream.Collectors;

public class SensorDataBufferedWriterFake implements SensorDataBufferedWriter {
    private static final Logger log = LoggerFactory.getLogger(SensorDataBufferedWriterFake.class);

    @Override
    public void writeBufferedData(List<SensorData> bufferedData) {
        var dataToWrite = bufferedData.stream().map(SensorData::toString).collect(Collectors.joining("\n"));
        log.info("Как будто куда-то записываем пачку данных: \n{}", dataToWrite);
    }
}
