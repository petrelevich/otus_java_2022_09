package ru.otus.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.api.SensorDataProcessingFlow;
import ru.otus.api.SensorDataProcessor;
import ru.otus.api.SensorsDataChannel;
import ru.otus.api.model.SensorData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;


public class SensorDataProcessingFlowImpl implements SensorDataProcessingFlow {
    private static final Logger log = LoggerFactory.getLogger(SensorDataProcessingFlowImpl.class);
    public static final int POLLING_TIMEOUT = 5;
    public static final int DATA_PROCESS_THREAD_POOL_SIZE = 1;

    private final Map<String, List<SensorDataProcessor>> bindings = new ConcurrentHashMap<>();
    private final AtomicBoolean pollingInProgress = new AtomicBoolean(false);
    private final ExecutorService pollingThreadPool = Executors.newFixedThreadPool(1);
    private final ExecutorService dataProcessThreadPool = Executors.newFixedThreadPool(DATA_PROCESS_THREAD_POOL_SIZE);

    private final SensorsDataChannel sensorsDataChannel;

    public SensorDataProcessingFlowImpl(SensorsDataChannel sensorsDataChannel) {
        this.sensorsDataChannel = sensorsDataChannel;
    }

    @Override
    public void startProcessing() {
        pollingInProgress.set(true);
        pollingThreadPool.submit(this::pollQueue);
    }

    @Override
    public void stopProcessing() {
        pollingInProgress.set(false);
    }

    @Override
    public void bindProcessor(String roomPattern, SensorDataProcessor processor) {
        var roomPatternBindings = bindings.computeIfAbsent(roomPattern,
                k -> new ArrayList<>());
        roomPatternBindings.add(processor);
    }

    private void pollQueue() {
        while (pollingInProgress.get() || !sensorsDataChannel.isEmpty()) {
            try {
                var sensorData = sensorsDataChannel.take(POLLING_TIMEOUT, TimeUnit.SECONDS);

                if (sensorData != null) {
                    dataProcessThreadPool.submit(() -> processData(sensorData));
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("Ошибка в процессе обработки очереди данных", e);
            }
        }
        doShutdown();
    }

    private void processData(SensorData data) {
        try {
            var actualProcessors = new ArrayList<>(bindings.getOrDefault("*", new ArrayList<>()));
            actualProcessors.addAll(bindings.getOrDefault(data.getRoom(), new ArrayList<>()));
            for (var processor : actualProcessors) {
                processor.process(data);
            }
        } catch (Exception e) {
            log.error("Ошибка в процессе обработки показаний датчика", e);
        }
    }

    private void doShutdown() {
        bindings.values().stream().flatMap(Collection::stream)
                .forEach(this::fireProcessorShutdownEvent);
        dataProcessThreadPool.shutdown();
        pollingThreadPool.shutdown();
    }

    private void fireProcessorShutdownEvent(SensorDataProcessor processor) {
        try {
            processor.onProcessingEnd();
        } catch (Exception e) {
            log.error("Ошибка в процессе завершающих действий процессора", e);
        }
    }
}
