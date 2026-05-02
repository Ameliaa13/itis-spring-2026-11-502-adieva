package org.example;


public class Main {
    public static void main(String[] args) {
        Service realService = new RealService();
        Service proxiedService = ProxyFactory.createProxy(realService, Service.class);

        System.out.println(proxiedService.start("Привет"));
        System.out.println(proxiedService.calculate(5, 3));


    }
}