package ru.otus.proxy.lazy;


public class Demo {
    public static void main(String[] args) {
        lazy();
    }


    private static void lazy() {
        HeavyObject heavyObject = new HeavyObjectImpl();
        System.out.println(heavyObject);

        HeavyObject heavyObjectProxy = new LazyProxy(heavyObject);

        System.out.println(heavyObjectProxy.getValue());
        System.out.println(heavyObject);
        System.out.println(heavyObjectProxy.getValue());
    }
}
