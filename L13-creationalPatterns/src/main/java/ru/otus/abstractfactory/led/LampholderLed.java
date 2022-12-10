package ru.otus.abstractfactory.led;

import ru.otus.abstractfactory.Lampholder;

/**
 * @author sergey
 * created on 18.09.18.
 */
public class LampholderLed implements Lampholder {
  @Override
  public void hold() {
    System.out.println("Led hold");
  }
}
