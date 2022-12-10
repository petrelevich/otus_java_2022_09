package ru.outs.observer;

import java.util.ArrayList;
import java.util.List;

class EventProducer {

    private final List<Listener> listeners = new ArrayList<>();

    void addListener(Listener listener) {
        listeners.add(listener);
    }

    void removeListener(Listener listener) {
        listeners.remove(listener);
    }

    void event(String data) {
        listeners.forEach(listener -> {
            try {
                listener.onUpdate(data);
            } catch (Exception ex) {
                //логирование исключения
            }
        });
    }
}
