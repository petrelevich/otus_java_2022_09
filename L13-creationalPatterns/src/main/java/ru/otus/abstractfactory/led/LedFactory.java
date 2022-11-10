package ru.otus.abstractfactory.led;

import ru.otus.abstractfactory.AbstractFactory;
import ru.otus.abstractfactory.Bulb;
import ru.otus.abstractfactory.Lampholder;

/**
 * @author sergey
 * created on 17.09.18.
 */
public class LedFactory implements AbstractFactory {
  @Override
  public Bulb createBulb() {
    return new BulbLed();
  }

  @Override
  public Lampholder createLampholder() {
    return new LampholderLed();
  }
}
