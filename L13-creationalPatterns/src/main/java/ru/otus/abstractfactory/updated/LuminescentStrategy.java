package ru.otus.abstractfactory.updated;

import ru.otus.abstractfactory.AbstractFactory;
import ru.otus.abstractfactory.luminescent.LuminescentFactory;

/**
 * @author sergey
 * created on 18.09.18.
 */
public class LuminescentStrategy implements Strategy {
    @Override
    public AbstractFactory configuration() {
        return new LuminescentFactory();
    }
}
