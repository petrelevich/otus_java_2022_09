package ru.outs.command;

public class Echo implements Command {
    @Override
    public String execute(SomeObject object) {
        return object.getValue();
    }
}
