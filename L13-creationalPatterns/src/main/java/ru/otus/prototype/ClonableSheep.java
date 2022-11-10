package ru.otus.prototype;

import java.util.Objects;

/**
 * @author sergey
 * created on 19.09.18.
 */
class ClonableSheep  implements Cloneable {
  private String name;

  ClonableSheep(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  void setName(String name) {
    this.name = name;
  }

  @Override
  public ClonableSheep clone() throws CloneNotSupportedException {
    ClonableSheep sheep = (ClonableSheep)super.clone();
    // ...
    return sheep;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ClonableSheep sheep = (ClonableSheep) o;
    return Objects.equals(name, sheep.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }
}
