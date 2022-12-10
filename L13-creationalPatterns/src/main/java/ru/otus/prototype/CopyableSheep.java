package ru.otus.prototype;

import java.util.Objects;

class CopyableSheep implements Copyable<CopyableSheep> {
  private String name;

  CopyableSheep(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  void setName(String name) {
    this.name = name;
  }

  @Override
  public CopyableSheep copy() {
    return new CopyableSheep(name);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CopyableSheep sheep = (CopyableSheep) o;
    return Objects.equals(name, sheep.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }
}
