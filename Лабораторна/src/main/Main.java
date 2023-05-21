package main;
import java.util.concurrent.CountDownLatch;

public class Main {
    static int numThreads = 10000; // Кількість потоків
    static int sequenceStep = 2; // Крок послідовності
    static int permissionDelay = 1000; // Проміжок часу для генерації дозволу

    public static void main(String[] args) {
        Thread[] threads = new Thread[numThreads];
        CountDownLatch latch = new CountDownLatch(numThreads);

        for (int i = 0; i < numThreads; i++) {
            int threadNumber = i;

            threads[i] = new Thread(() -> {
                int sum = 0;
                int count = 0;
                for (int j = 0; j <= threadNumber; j++) {
                    sum += j * sequenceStep;
                    count++;
                }
                System.out.println("Thread " + threadNumber + ": Sum = " + sum + ", Count = " + count);

                latch.countDown(); // Зменшуємо лічильник
            });

            threads[i].start();
        }

        try {
            latch.await(permissionDelay, java.util.concurrent.TimeUnit.MILLISECONDS); // Очікуємо дозволу з заданим проміжком часу
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("All threads completed.");
    }
}
