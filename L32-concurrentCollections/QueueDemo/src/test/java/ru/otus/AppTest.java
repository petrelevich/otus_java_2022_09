package ru.otus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ru.otus.api.model.SensorData;
import ru.otus.lib.SensorDataBufferedWriterFake;
import ru.otus.services.FakeSensorDataGenerator;
import ru.otus.services.SensorDataProcessingFlowImpl;
import ru.otus.services.SensorsDataQueueChannel;
import ru.otus.services.SensorsDataServerImpl;
import ru.otus.services.processors.SensorDataProcessorCommon;
import ru.otus.services.processors.SensorDataProcessorRoom;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AppTest {
    private static final String ALL_ROOMS_BINDING = "*";
    private static final String ROOM_NAME_BINDING = "Комната: 4";
    private static final int SENSORS_COUNT = 4;
    private static final int SENSORS_DATA_QUEUE_CAPACITY = 1000;

    private FakeSensorDataGenerator fakeSensorDataGenerator;
    private SensorsDataQueueChannel sensorsDataChannel;
    private SensorDataProcessingFlowImpl sensorDataProcessingFlow;
    private SensorDataProcessorCommon sensorDataProcessorCommon;
    private SensorDataProcessorRoom sensorDataProcessorRoom;

    @BeforeEach
    void setUp() {

        sensorsDataChannel = spy(new SensorsDataQueueChannel(SENSORS_DATA_QUEUE_CAPACITY));
        var sensorsDataServer = new SensorsDataServerImpl(sensorsDataChannel);
        sensorDataProcessingFlow = new SensorDataProcessingFlowImpl(sensorsDataChannel);

        fakeSensorDataGenerator = new FakeSensorDataGenerator(sensorsDataServer, SENSORS_COUNT);
        sensorDataProcessorCommon = spy(new SensorDataProcessorCommon());
        sensorDataProcessorRoom = spy(new SensorDataProcessorRoom(ROOM_NAME_BINDING));

        sensorDataProcessingFlow.bindProcessor(ALL_ROOMS_BINDING, sensorDataProcessorCommon);
        sensorDataProcessingFlow.bindProcessor(ROOM_NAME_BINDING, sensorDataProcessorRoom);
    }

    @Test
    void shouldInvokeCorrectProcessorsAccordingItsBindings() throws InterruptedException {
        var allDataByRooms = new ConcurrentHashMap<String, List<SensorData>>();

        Mockito.doAnswer(a -> {
            SensorData sd = (SensorData) a.callRealMethod();
            allDataByRooms.computeIfAbsent(sd.getRoom(), k -> new ArrayList<>()).add(sd);
            return sd;
        }).when(sensorsDataChannel).take(anyLong(), any());


        fakeSensorDataGenerator.start();
        sensorDataProcessingFlow.startProcessing();

        TimeUnit.SECONDS.sleep(5);

        fakeSensorDataGenerator.stop();
        sensorDataProcessingFlow.stopProcessing();

        var allData = allDataByRooms.entrySet().stream()
                .flatMap(e -> e.getValue().stream())
                .collect(Collectors.toList());
        var allDataByBindedRoom = allDataByRooms.get(ROOM_NAME_BINDING);

        assertThat(allData).isNotEmpty();

        ArgumentCaptor<SensorData> argumentCaptorForProcessorCommon = ArgumentCaptor.forClass(SensorData.class);
        verify(sensorDataProcessorCommon, times(allData.size())).process(argumentCaptorForProcessorCommon.capture());
        assertThat(argumentCaptorForProcessorCommon.getAllValues()).containsExactlyInAnyOrderElementsOf(allData);

        ArgumentCaptor<SensorData> argumentCaptorForProcessorRoom = ArgumentCaptor.forClass(SensorData.class);
        verify(sensorDataProcessorRoom, times(allDataByBindedRoom.size())).process(argumentCaptorForProcessorRoom.capture());
        assertThat(argumentCaptorForProcessorRoom.getAllValues()).containsExactlyInAnyOrderElementsOf(allDataByBindedRoom);
    }
}