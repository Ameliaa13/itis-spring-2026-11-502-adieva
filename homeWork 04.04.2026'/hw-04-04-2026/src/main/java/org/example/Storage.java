package org.example;

public class Storage {

    private int items = 0;

    public synchronized void produce() {
        while (true) {
            try {
                while (items >= 100) {
                    wait();
                }

                items++;
                System.out.println("Producer: " + items + " пепси произведено");

                Thread.sleep((long) (Math.random() * 400 + 100));

                if (items == 90) {
                    System.out.println("Producer: произведено 90 пепси, уведомляю консьюмера");
                    notifyAll();
                }

                if (items == 100) {
                    System.out.println("Producer: произведен максимум пепси");
                    notifyAll();
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public synchronized void consume() {
        while (true) {
            try {
                while (items <= 0) {
                    wait();
                }

                items--;
                System.out.println("Consumer: " + items + " пепси осталось");

                Thread.sleep((long) (Math.random() * 400 + 100));

                if (items == 10) {
                    System.out.println("Consumer: осталось 10 пепси, уведомляю продюсера");
                    notifyAll();
                }

                if (items == 0) {
                    System.out.println("Consumer: пепси закончились");
                    notifyAll();
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
