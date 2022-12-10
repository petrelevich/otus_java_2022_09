package ru.otus.abstractfactory.luminescent;

import ru.otus.abstractfactory.Lampholder;

/**
 * @author sergey
 * created on 18.09.18.
 */
public class LampholderLuminescent implements Lampholder {
  @Override
  public void hold() {
    System.out.println("Luminescent hold");
  }
}
