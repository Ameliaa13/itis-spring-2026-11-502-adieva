package org.example;

public class Consumer extends Thread {

    private final Storage storage;

    public Consumer(Storage storage) {
        this.storage = storage;
    }

    @Override
    public void run() {
        storage.consume();
    }
}