package org.example;
import java.lang.reflect.Proxy;


public class ProxyFactory {

    public static <T> T createProxy(T realObject, Class<T> interfaceClass) {
        return (T) Proxy.newProxyInstance(
                interfaceClass.getClassLoader(),
                new Class<?>[] { interfaceClass },
                new Timer(realObject)
        );
    }
}
