package ru.nsu.nmashkin.task211;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class CommonTests {
    private static Stream<Function<List<Integer>, Boolean>> provideArgs() {
        return Stream.of(
            (List<Integer> nums) -> Sequential.hasNonPrime(nums.stream()
                    .mapToInt(Integer::intValue)
                    .toArray()),
            (List<Integer> nums) -> MultiThreaded.hasNonPrime(nums.stream()
                    .mapToInt(Integer::intValue)
                    .toArray(), 4),
            (List<Integer> nums) -> Sequential.hasNonPrime(nums.stream()
                    .mapToInt(Integer::intValue)
                    .toArray())
        );
    }

    @ParameterizedTest
    @MethodSource("provideArgs")
    void hasNonPrime_true(Function<List<Integer>, Boolean> func) {
        List<Integer> nums = List.of(2, 3, 5, 7, 11, 13, 17, 20);
        assertTrue(func.apply(nums));
    }

    @ParameterizedTest
    @MethodSource("provideArgs")
    void hasNonPrime_false(Function<List<Integer>, Boolean> func) {
        List<Integer> nums = List.of(2, 3, 5, 7, 11, 13, 17, 19);
        assertFalse(func.apply(nums));
    }

    @Test
    void isPrime() {
        assertFalse(PrimeChecker.isPrime(1));
        assertTrue(PrimeChecker.isPrime(2));
        assertFalse(PrimeChecker.isPrime(100));
    }
}