package ru.outs.visitor;

public interface Element {
    void accept(Visitor v);
}
