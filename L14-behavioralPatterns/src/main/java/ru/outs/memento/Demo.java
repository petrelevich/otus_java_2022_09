package ru.outs.memento;

import java.time.LocalDateTime;

public class Demo {
    public static void main(String[] args) {
        var originator = new Originator(LocalDateTime::now);

        var abc = new State(new String[]{"A", "B", "C"});
        System.out.println(abc);

        originator.saveState(abc);
        abc.getArray()[0] = "A1";
        System.out.println(abc);

        originator.saveState(abc);
        abc.getArray()[0] = "A2";
        System.out.println(abc);

        originator.saveState(abc);
        abc.getArray()[0] = "A3";
        System.out.println(abc);

        System.out.println("  undo changes");

        abc = originator.restoreState();
        System.out.println(abc);

        abc = originator.restoreState();
        System.out.println(abc);

        abc = originator.restoreState();
        System.out.println(abc);
    }
}
