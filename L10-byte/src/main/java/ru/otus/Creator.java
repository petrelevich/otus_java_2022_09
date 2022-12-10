package ru.otus;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class Creator {

    private static Object object;

    public static TestLoggingInterface create(Object ob) {
        object = ob;
        return (TestLoggingInterface) Proxy.newProxyInstance(Creator.class.getClassLoader(),
                new Class<?>[]{TestLoggingInterface.class}, new MyInvocationHandler());

    }

    static class MyInvocationHandler implements InvocationHandler {


        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Method method1 = object.getClass().getMethod(method.getName(), method.getParameterTypes());
            if (Arrays.stream(method1.getAnnotations()).anyMatch(e -> e.annotationType().equals(Log.class))) {
                System.out.print("executed method: calculation, param: ");
                Arrays.stream(args).forEach(e -> System.out.print(e + ", "));
                System.out.println();
            }

            return method.invoke(object, args);
        }
    }
}
