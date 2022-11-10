package ru.otus.abstractfactory.updated;

import ru.otus.abstractfactory.AbstractFactory;
import ru.otus.abstractfactory.led.LedFactory;

/**
 * @author sergey
 * created on 17.09.18.
 */
public class LedStrategy implements Strategy {
    @Override
    public AbstractFactory configuration() {
        return new LedFactory();
    }
}
