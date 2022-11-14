package ru.outs.command;

@FunctionalInterface
public interface Command {
    String execute(SomeObject object);
}
