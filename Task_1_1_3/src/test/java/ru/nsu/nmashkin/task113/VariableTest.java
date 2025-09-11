package ru.nsu.nmashkin.task113;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class VariableTest {

    @Test
    void eval_no_value() {
        Expression e = new Variable("x");
        assertEquals(Double.POSITIVE_INFINITY, e.eval(""));
    }

    @Test
    void eval_invalid_value() {
        Expression e = new Variable("x");
        assertEquals(Double.POSITIVE_INFINITY, e.eval("x = y"));
    }

    @Test
    void eval_null() {
        Expression e = new Variable("");
        assertEquals(Double.POSITIVE_INFINITY, e.eval(""));
    }

    @Test
    void derivative_same() {
        Expression e = new Variable("x");
        assertEquals(new Number(1), e.derivative("x"));
    }

    @Test
    void derivative_different() {
        Expression e = new Variable("x");
        assertEquals(new Number(0), e.derivative("y"));
    }

    @Test
    void derivative_null_name() {
        Expression e = new Variable("");
        assertEquals(new Number(Double.POSITIVE_INFINITY), e.derivative(""));
    }

    @Test
    void invalid_name_digit() {
        Variable v = new Variable("1");
        assertNull(v.getName());
    }

    @Test
    void invalid_name_jibberish() {
        Variable v = new Variable("%$;:'\"");
        assertNull(v.getName());
    }
}