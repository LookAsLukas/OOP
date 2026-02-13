package ru.nsu.nmashkin.task211;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Multithreaded implementation for finding a non prime.
 */
public class MultiThreaded {
    /**
     * Main method.
     *
     * @param numbers array of numbers
     * @return whether there is a non prime among numbers
     */
    public static boolean hasNonPrime(int[] numbers, int threadCount) {
        AtomicBoolean result = new AtomicBoolean(false);
        Thread[] threads = new Thread[threadCount];
        int chunkSize = (int) Math.ceil((double) numbers.length / threadCount);

        for (int i = 0; i < threadCount; i++) {
            final int start = i * chunkSize;
            final int end = Math.min(start + chunkSize, numbers.length);

            threads[i] = new Thread(() -> {
                for (int j = start; j < end; j++) {
                    if (result.get()) break;
                    if (!PrimeChecker.isPrime(numbers[j])) {
                        result.set(true);
                        break;
                    }
                }
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException _) {
                System.err.println("MultiThreaded.hasNonPrime(): Could not join thread");
            }
        }

        return result.get();
    }
}
