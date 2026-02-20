package ru.nsu.nmashkin.task211;

/**
 * Check if the number is prime.
 */
public class PrimeChecker {
    /**
     * Check if the number is prime.
     *
     * @param n number
     * @return is n prime
     */
    public static boolean isPrime(int n) {
        if (n <= 1) {
            return false;
        }
        if (n == 2) {
            return true;
        }

        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }
}
