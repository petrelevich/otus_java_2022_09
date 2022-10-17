package ru.otus.reflection;

import java.lang.reflect.Array;

public class ReflectionArray {
    public static void main(String[] args) {
        int[] arrayInt = new int[0];

        Class<? extends int[]> clazz = arrayInt.getClass();
        System.out.println("isArray: " + clazz.isArray());
        System.out.println("TypeName: " + clazz.getTypeName());

        Class<?> componentType = clazz.getComponentType();
        System.out.println("componentType: " + componentType);

        System.out.println("-- creating Array");

        float[] arrayFloat = (float[]) Array.newInstance(float.class, 5);
        System.out.println("length:" + arrayFloat.length);
        System.out.println("created TypeName:" + arrayFloat.getClass().getTypeName());
    }
}
