package ru.otus.objectpool;

public interface ObjectFactory<T> {
    T create();
}
