package org.example;

public class RealService implements Service {
    @Override
    public String start(String input) {
        try { Thread.sleep(100); } catch (InterruptedException e) {}
        return input;
    }

    @Override
    public int calculate(int a, int b) {
        try { Thread.sleep(50); } catch (InterruptedException e) {}
        return a + b;
    }
}