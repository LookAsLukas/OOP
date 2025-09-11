package ru.nsu.nmashkin.task113;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class NumberTest {

    @Test
    void derivative() {
        Expression e = new Number(1);
        assertEquals(new Number(0), e.derivative(""));
    }

    @Test
    void nan() {
        Expression e = new Number(Double.NaN);
        assertEquals(Double.POSITIVE_INFINITY, e.eval(""));
    }
}