package ru.otus.abstractfactory.led;

/**
 * @author sergey
 * created on 18.09.18.
 */
public class BulbLed implements ru.otus.abstractfactory.Bulb {
  @Override
  public void light() {
    System.out.println("Led light");
  }
}
