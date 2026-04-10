package org.example;

public class Producer extends Thread {

    private final Storage storage;

    public Producer(Storage storage) {
        this.storage = storage;
    }

    @Override
    public void run() {
        storage.produce();
    }
}