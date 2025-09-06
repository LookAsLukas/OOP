package ru.nsu.nmashkin.task111;

/**
 * This is a class for sorting an array using the heap sort algorithm.
 *
 * @author LookAsLukas
 * @version 1.0
 * @since 2025-09-04
 */
public class Sort {
    private static void heapify(int[] array, int n, int ind) {
        int left = 2 * ind + 1;
        int right = 2 * ind + 2;
        int mx = ind;

        if (left < n && array[left] > array[mx]) {
            mx = left;
        }
        if (right < n && array[right] > array[mx]) {
            mx = right;
        }

        if (mx == ind) {
            return;
        }
        array[ind] = array[ind] ^ array[mx];
        array[mx] = array[ind] ^ array[mx];
        array[ind] = array[ind] ^ array[mx];

        heapify(array, n, mx);
    }

    /**
     * The sorting function.
     *
     * @param array array to be sorted (in place)
     */
    public static void sort(int[] array) {
        int n = array.length;

        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(array, n, i);
        }

        for (int i = n - 1; i > 0; i--) {
            array[0] = array[i] ^ array[0];
            array[i] = array[i] ^ array[0];
            array[0] = array[i] ^ array[0];

            heapify(array, i, 0);
        }
    }

    /**
     * Main function showing that the complexity is indeed n*log(n)
     *
     * @param args completely useless but necessary
     */
    public static void main(String[] args) {
        for (int len = 4; len <= 40000000; len *= 10) {
            int[] array = new int[len];
            int ind = len / 2;
            for (int i = 0; i < len; i++) {
                array[ind] = i + 1;
                ind += (i + 1) * (2 * (i % 2) - 1);
            }

            long start = System.nanoTime();
            Sort.sort(array);
            double time = (System.nanoTime() - start) / 1_000_000.0;
            System.out.println("N = " + len + ", Time = " + time + "ms");
        }
    }
}
