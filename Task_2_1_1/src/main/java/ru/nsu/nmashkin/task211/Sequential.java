package ru.nsu.nmashkin.task211;

/**
 * Sequential implementation for finding a non prime.
 */
public class Sequential {
    /**
     * Main method.
     *
     * @param numbers array of numbers
     * @return whether there is a non prime among numbers
     */
    public static boolean hasNonPrime(int[] numbers) {
        for (int num : numbers) {
            if (!PrimeChecker.isPrime(num)) {
                return true;
            }
        }
        return false;
    }
}
