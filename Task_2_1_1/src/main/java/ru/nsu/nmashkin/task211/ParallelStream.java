package ru.nsu.nmashkin.task211;

import java.util.Arrays;

/**
 * ParallelStream implementation for finding a non prime.
 */
public class ParallelStream {
    /**
     * Main method.
     *
     * @param numbers array of numbers
     * @return whether there is a non prime among numbers
     */
    public static boolean hasNonPrime(int[] numbers) {
        return Arrays.stream(numbers).parallel()
                .anyMatch(num -> !PrimeChecker.isPrime(num));
    }
}
