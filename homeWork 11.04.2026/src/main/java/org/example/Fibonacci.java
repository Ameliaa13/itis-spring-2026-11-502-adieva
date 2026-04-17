package org.example;


import java.util.*;
import java.util.concurrent.*;

public class Fibonacci {

    static class Pair{
        int index;
        long number;

        public Pair(int index, long number) {
            this.index = index;
            this.number = number;
        }
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите индекс: ");
        int n = sc.nextInt();
        long startSingle = System.nanoTime();
        System.out.println(fibonacci(n));
        long endSingle = System.nanoTime();
        double resultSingle = (endSingle-startSingle)/1_000_000.0;
        System.out.println(resultSingle);


        System.out.println("Введите число потоков: ");
        int count = sc.nextInt();
        List<Integer> list = List.of(35, 36, 37, 38, 39, 40);
        long startMany = System.nanoTime();
        Map<Integer, Long> result = fibonacciParallel(list, count);
        long endMany = System.nanoTime();
        double resultMany = (endMany- startMany)/ 1_000_000.0;
        System.out.println(resultMany);
        for(Map.Entry<Integer, Long> entry :result.entrySet() ){
            System.out.printf("fibonacci(%d) = %d\n", entry.getKey(), entry.getValue());
        }

        double differenceOfSpeed = (resultSingle * list.size())/resultMany;
        System.out.println("программа работает быстрее в: " + differenceOfSpeed);

    }


    public static int fibonacci(int n){
        if (n<0) throw new RuntimeException();
        if(n <=1) return n;
        return fibonacci(n-1) + fibonacci(n-2);
    }


    public static Map<Integer, Long> fibonacciParallel(List<Integer> indexes, int countThreads){
        ExecutorService executorService = Executors.newFixedThreadPool(countThreads);
        List<Future<Pair>> futures = new ArrayList<>();

        for(int i : indexes){
            Callable<Pair> task = () -> new Pair(i, fibonacci(i));
            futures.add(executorService.submit(task));
        }
        Map<Integer, Long> result = new HashMap<>();
        for(Future<Pair> future: futures){
            try {
                Pair pair = future.get();
                result.put(pair.index, pair.number);
            } catch (ExecutionException | InterruptedException e){
                System.out.println("Ошибка: " + e.getCause());
            }
        }

        executorService.shutdown();
        return result;
    }

}

