package ru.nsu.nmashkin.task111;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

class SortTest {

    @Test
    void sort_normal() {
        int[] array = new int[]{1, 3, 2, 7, 6, 4, 5};

        Sort.sort(array);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6, 7}, array);
    }

    @Test
    void sort_zero_elements() {
        int[] array = new int[]{};
        Sort.sort(array);
        assertArrayEquals(new int[]{}, array);
    }

    @Test
    void sort_sorted() {
        int[] array = new int[]{1, 2, 3, 4, 5};
        Sort.sort(array);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, array);
    }

    @Test
    void sort_negative() {
        int[] array = new int[]{-3, -6, -4, -1, -9, -3};
        Sort.sort(array);
        assertArrayEquals(new int[]{-9, -6, -4, -3, -3, -1}, array);
    }

    @Test
    void sort_a_lot() {
        int n = 30000000;
        int ind = n / 2;
        int[] array = new int[n];
        int[] expected = new int[n];
        for (int i = 0; i < n; i++) {
            expected[i] = i + 1;
            array[ind] = i + 1;
            ind += (i + 1) * (2 * (i % 2) - 1);
        }
        Sort.sort(array);
        assertArrayEquals(expected, array);
    }

    @Test
    void sort_main() {
        Sort.main(null);
    }
}