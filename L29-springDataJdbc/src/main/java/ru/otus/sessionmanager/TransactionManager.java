package ru.otus.sessionmanager;

public interface TransactionManager {

    <T> T doInTransaction(TransactionAction<T> action);
}
