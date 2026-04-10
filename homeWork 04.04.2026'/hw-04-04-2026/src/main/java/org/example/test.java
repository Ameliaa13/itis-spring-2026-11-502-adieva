package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.*;

class StorageTest {

    private Storage storage;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        storage = new Storage();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void testProduceUntilFull() throws InterruptedException {
        Thread producer = new Thread(() -> storage.produce());
        producer.start();
        Thread.sleep(20000);
        producer.interrupt();
        producer.join();
        String output = outContent.toString();
        assertTrue(output.contains("произведено 100 пепси") || output.contains("произведен максимум пепси"));
    }

    @Test
    void testConsumeUntilEmpty() throws Exception {
        Field itemsField = Storage.class.getDeclaredField("items");
        itemsField.setAccessible(true);
        itemsField.set(storage, 50);

        Thread consumer = new Thread(() -> storage.consume());
        consumer.start();
        Thread.sleep(10000);
        consumer.interrupt();
        consumer.join();

        String output = outContent.toString();
        assertTrue(output.contains("пепси закончились"));
    }

    @Test
    void testProducerNotifiesConsumerAt90() throws Exception {
        Field itemsField = Storage.class.getDeclaredField("items");
        itemsField.setAccessible(true);
        itemsField.set(storage, 89);

        Thread producer = new Thread(() -> storage.produce());
        producer.start();
        Thread.sleep(3000);
        producer.interrupt();
        producer.join();

        String output = outContent.toString();
        assertTrue(output.contains("произведено 90 пепси, уведомляю консьюмера"));
    }

    @Test
    void testProducerNotifiesAt100() throws Exception {
        Field itemsField = Storage.class.getDeclaredField("items");
        itemsField.setAccessible(true);
        itemsField.set(storage, 99);

        Thread producer = new Thread(() -> storage.produce());
        producer.start();
        Thread.sleep(3000);
        producer.interrupt();
        producer.join();

        String output = outContent.toString();
        assertTrue(output.contains("произведен максимум пепси"));
    }

    @Test
    void testConsumerNotifiesAt10() throws Exception {
        Field itemsField = Storage.class.getDeclaredField("items");
        itemsField.setAccessible(true);
        itemsField.set(storage, 11);

        Thread consumer = new Thread(() -> storage.consume());
        consumer.start();
        Thread.sleep(3000);
        consumer.interrupt();
        consumer.join();

        String output = outContent.toString();
        assertTrue(output.contains("осталось 10 пепси, уведомляю продюсера"));
    }
}
