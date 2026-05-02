package org.example;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

class Timer implements InvocationHandler {
    private final Object realObject;

    public Timer(Object realObject) {
        this.realObject = realObject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long startTime = System.nanoTime();

        Object result = method.invoke(realObject, args);

        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000;

        System.out.println("Метод " + method.getName() + " выполнен за " + duration + " мс");

        return result;
    }
}
