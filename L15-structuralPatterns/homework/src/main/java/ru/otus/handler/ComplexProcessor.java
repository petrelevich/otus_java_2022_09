package ru.otus.handler;

import ru.otus.model.Message;
import ru.otus.listener.Listener;
import ru.otus.processor.Processor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ComplexProcessor implements Handler {

    private final List<Listener> listeners = new ArrayList<>();
    private final List<Processor> processors;
    private final Consumer<Exception> errorHandler;

    public ComplexProcessor(List<Processor> processors, Consumer<Exception> errorHandler) {
        this.processors = processors;
        this.errorHandler = errorHandler;
    }

    @Override
    public Message handle(Message msg) {
        Message newMsg = msg;
        for (Processor pros : processors) {
            try {
                newMsg = pros.process(newMsg);
            } catch (Exception ex) {
                errorHandler.accept(ex);
            }
        }
        notify(newMsg);
        return newMsg;
    }

    @Override
    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(Listener listener) {
        listeners.remove(listener);
    }

    private void notify(Message msg) {
        listeners.forEach(listener -> {
            try {
                listener.onUpdated(msg);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
}
