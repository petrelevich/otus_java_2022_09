package ru.outs.command;

import java.util.ArrayDeque;
import java.util.Queue;

class Executor {
    private final SomeObject object;
    private final Queue<Command> commands = new ArrayDeque<>();

    public Executor(SomeObject object) {
        this.object = object;
    }

    void addCommand(Command command) {
        commands.add(command);
    }

    void executeCommands() {
        Command command;
        while ((command = commands.poll()) != null) {
            var result = command.execute(object);
            System.out.println("command execution result:" + result);
        }
    }
}
