package ru.otus.generators;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
public class ClientIdGeneratorImpl implements ClientIdGenerator {

    private final static AtomicLong CLIENT_ID = new AtomicLong(0);

    @Override
    public long generateId() {
        return CLIENT_ID.incrementAndGet();
    }
}
