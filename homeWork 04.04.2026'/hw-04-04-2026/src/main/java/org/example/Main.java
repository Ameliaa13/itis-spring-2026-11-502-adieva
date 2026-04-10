package org.example;

public class Main {
    public static void main(String[] args) {

        Storage storage = new Storage();

        Thread producer = new Producer(storage);
        Thread consumer = new Consumer(storage);

        producer.start();
        consumer.start();
    }
}