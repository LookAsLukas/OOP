package ru.nsu.nmashkin.Task_1_1_1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SortTest {

    @Test
    void sort() {
        int[] array = new int[]{1, 3, 2, 7, 6, 4, 5};
        var result = Sort.sort(array);
        for (var a : result) {
            System.out.println(a);
        }
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6, 7}, result);
    }
}