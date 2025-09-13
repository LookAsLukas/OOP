package ru.nsu.nmashkin.task113;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class NumberTest {

    @Test
    void derivative() {
        Expression e = new Number(1);
        assertEquals(new Number(0), e.derivative(""));
    }

    @Test
    void nan() {
        assertThrows(RuntimeException.class, () -> {
            new Number(Double.NaN);
        });
    }
}