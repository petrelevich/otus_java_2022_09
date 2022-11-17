package ru.otus.flyweight;


public class ObjectFactory {
    private final HeavyCommonPart heavyCommonPart;

    public ObjectFactory() {
        heavyCommonPart = new HeavyCommonPart();
    }

    public ObjectOnLine create(int x) {
        return new ObjectOnLine(heavyCommonPart, x);
    }
}

/*
По сути получается следующее:
var heavyCommonPart = new HeavyCommonPart();
var obj1 = new ObjectOnLine(heavyCommonPart, x1);
var obj2 = new ObjectOnLine(heavyCommonPart, x2);
var obj3 = new ObjectOnLine(heavyCommonPart, x3);
var obj4 = new ObjectOnLine(heavyCommonPart, x4);
var obj5 = new ObjectOnLine(heavyCommonPart, x5);
 */
