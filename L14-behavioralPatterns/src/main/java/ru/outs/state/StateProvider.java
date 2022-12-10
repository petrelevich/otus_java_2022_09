package ru.outs.state;

public class StateProvider {

    private static final State ON_STATE = new OnState();
    private static final State OFF_STATE = new OffState();

    private StateProvider() {
    }

    public static State getOnState() {
        return ON_STATE;
    }

    public static State getOffState() {
        return OFF_STATE;
    }
}
