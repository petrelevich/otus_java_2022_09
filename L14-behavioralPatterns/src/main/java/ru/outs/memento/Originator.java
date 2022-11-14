package ru.outs.memento;

import java.util.ArrayDeque;
import java.util.Deque;

class Originator {
    //Фактически, это stack
    private final Deque<Memento> stack = new ArrayDeque<>();

    private final DateTimeProvider dateTimeProvider;

    public Originator(DateTimeProvider dateTimeProvider) {
        this.dateTimeProvider = dateTimeProvider;
    }

    void saveState(State state) {
        stack.push(new Memento(state, dateTimeProvider.getDate()));
    }

    State restoreState() {
        var memento = stack.pop();
        System.out.println("createdAt:" + memento.createdAt());
        return memento.state();
    }
}
