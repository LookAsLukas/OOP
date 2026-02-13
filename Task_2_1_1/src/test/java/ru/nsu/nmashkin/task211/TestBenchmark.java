package ru.nsu.nmashkin.task211;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class TestBenchmark {
    private int[] generatePrimes() {
        List<Integer> result = new ArrayList<>();
        for (int i = 6700000; i < 6800000; i++) {
            if (PrimeChecker.isPrime(i)) {
                result.add(i);
            }
        }
        return result.stream().mapToInt(Integer::intValue).toArray();
    }


    @Test
    void benchmark() {
        int[] nums = generatePrimes();

        long start = System.nanoTime();
        assertFalse(Sequential.hasNonPrime(nums));
        final long timeSequential = System.nanoTime() - start;

        final int numThreads = 10;
        long[] timeMultiThreaded = new long[numThreads];
        for (int threadCnt = 1; threadCnt <= numThreads; threadCnt++) {
            start = System.nanoTime();
            assertFalse(MultiThreaded.hasNonPrime(nums, threadCnt));
            timeMultiThreaded[threadCnt - 1] = System.nanoTime() - start;
        }

        start = System.nanoTime();
        assertFalse(ParallelStream.hasNonPrime(nums));
        final long timeParallelStream = System.nanoTime() - start;

        double divisor = 1000000;
        System.out.println("Sequential: " + (double) timeSequential / divisor);
        System.out.println("MultiThreaded:");
        for (int threadCnt = 1; threadCnt <= numThreads; threadCnt++) {
            System.out.println("  " + threadCnt + " --> "
                    + (double) timeMultiThreaded[threadCnt - 1] / divisor);
        }
        System.out.println("ParallelStream: " + (double) timeParallelStream / divisor);
    }
}