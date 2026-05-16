package org.example.Task2;

import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.util.Scanner;

public class PiCalculator{

    public static double singleThreadPi(int N) {
        double sum = 0;
        for (int i = 0; i < N; i++) {
            if (i % 2 == 0) {
                sum = sum + 1.0 / (2 * i + 1);
            } else {
                sum = sum - 1.0 / (2 * i + 1);
            }
        }
        return 4 * sum;
    }

    public static double multiThreadCallablePi(int N, int threadCount) throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(threadCount);
        Future<Double>[] futures = new Future[threadCount];

        int chunk = N / threadCount;
        int ostatok = N % threadCount;

        for (int t = 0; t < threadCount; t++) {
            int start = t * chunk;
            int end = (t == threadCount - 1) ? start + chunk + ostatok - 1 : start + chunk - 1;
            final int s = start;
            final int e = end;

            futures[t] = pool.submit(() -> {
                double sum = 0;
                for (int i = s; i <= e; i++) {
                    double term = 1.0 / (2 * i + 1);
                    if (i % 2 == 0) sum += term;
                    else sum -= term;
                }
                return sum;
            });
        }

        double total = 0;
        for (int t = 0; t < threadCount; t++) {
            total += futures[t].get();
        }
        pool.shutdown();
        return 4 * total;
    }

    public static double multiThreadLockPi(int N, int threadCount) throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(threadCount);
        ReentrantLock lock = new ReentrantLock();
        CountDownLatch latch = new CountDownLatch(threadCount);
        double[] sharedSum = {0.0};

        int chunk = N / threadCount;
        int ostatok = N % threadCount;

        for (int t = 0; t < threadCount; t++) {
            int start = t * chunk;
            int end = (t == threadCount - 1) ? start + chunk + ostatok - 1 : start + chunk - 1;
            final int s = start;
            final int e = end;

            pool.execute(() -> {
                double localSum = 0;
                for (int i = s; i <= e; i++) {
                    double term = 1.0 / (2 * i + 1);
                    if (i % 2 == 0) localSum += term;
                    else localSum -= term;
                }
                lock.lock();
                try {
                    sharedSum[0] += localSum;
                } finally {
                    lock.unlock();
                }
                latch.countDown();
            });
        }

        latch.await();
        pool.shutdown();
        return 4 * sharedSum[0];
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("N: ");
        int N = sc.nextInt();
        int threads = Runtime.getRuntime().availableProcessors();
        sc.close();

        try {
            long start = System.nanoTime();
            double pi1 = singleThreadPi(N);
            long t1 = System.nanoTime() - start;

            start = System.nanoTime();
            double pi2 = multiThreadCallablePi(N, threads);
            long t2 = System.nanoTime() - start;

            start = System.nanoTime();
            double pi3 = multiThreadLockPi(N, threads);
            long t3 = System.nanoTime() - start;

            System.out.printf("однопоточка: pi=%.10f %d нс\n", pi1, t1);
            System.out.printf("call: pi=%.10f %d нс\n", pi2, t2);
            System.out.printf("lock: pi=%.10f %d нс\n", pi3, t3);

        } catch (Exception e) {
            System.err.println("error: " + e.getMessage());
        }
    }
}