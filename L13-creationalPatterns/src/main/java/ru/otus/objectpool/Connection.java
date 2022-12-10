package ru.otus.objectpool;

/**
 * @author sergey
 * created on 19.09.18.
 */
public interface Connection {
  void connect();

  void execSelect();
}
