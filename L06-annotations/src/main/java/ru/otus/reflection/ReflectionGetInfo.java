package ru.otus.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;


public class ReflectionGetInfo {
    public static void main(String[] args) throws NoSuchMethodException {

        Class<DemoClass> clazz = DemoClass.class;
        System.out.println("Class Name:" + clazz.getSimpleName());

        Constructor<?>[] constructors = clazz.getConstructors();
        System.out.println("--- constructors:");
        System.out.println(Arrays.toString(constructors));

        Method[] methodsPublic = clazz.getMethods();
        System.out.println("--- public methods:");
        Arrays.stream(methodsPublic).forEach(method -> System.out.println(method.getName()));

        Method[] methodsAll = clazz.getDeclaredMethods();
        System.out.println("--- all methods:");
        Arrays.stream(methodsAll).forEach(method -> System.out.println(method.getName()));

        System.out.println("--- public fields:");
        Field[] fieldsPublic = clazz.getFields();
        System.out.println(Arrays.toString(fieldsPublic));

        System.out.println("--- all fields:");
        Field[] fieldsAll = clazz.getDeclaredFields();
        Arrays.stream(fieldsAll).forEach(System.out::println);

        System.out.println("--- annotations:");
        Method annotatedMethod = clazz.getMethod("toString");
        Annotation[] annotations = annotatedMethod.getDeclaredAnnotations();
        System.out.println(Arrays.toString(annotations));
    }
}
