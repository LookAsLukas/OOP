package ru.nsu.nmashkin.Task_1_1_1;

public class Sort {
    private static void heapify(int[] array, int n, int ind) {
        int left = 2 * ind + 1, right = 2 * ind + 2, mx = ind;

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

    public static int[] sort(int[] array) {
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

        return array;
    }
}
