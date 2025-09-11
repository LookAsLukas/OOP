package ru.nsu.nmashkin.task113;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class SubTest {

    @Test
    void eval_nums() {
        Expression e = new Sub(new Number(1), new Number(2));
        assertEquals(-1, e.eval(""));
    }

    @Test
    void eval_vars() {
        Expression e = new Sub(new Variable("x"), new Variable("y"));
        assertEquals(-17, e.eval("y = 10; x = -7"));
    }

    @Test
    void eval_null() {
        Expression e = new Sub(null, null);
        assertEquals(Double.POSITIVE_INFINITY, e.eval(""));
    }

    @Test
    void derivative_bound() {
        Expression e = new Sub(new Variable("x"), new Variable("y"));
        assertEquals(new Sub(new Number(1), new Number(0)),
                     e.derivative("x"));
    }

    @Test
    void derivative_free() {
        Expression e = new Sub(new Variable("x"), new Variable("y"));
        assertEquals(new Sub(new Number(0), new Number(0)),
                     e.derivative("z"));
    }

    @Test
    void derivative_null() {
        Expression e = new Sub(null, null);
        assertNull(e.derivative(""));
    }

    @Test
    void simplify_two_nums() {
        Expression e = new Sub(new Number(1), new Number(2));
        assertEquals(new Number(-1), e.simplify());
    }

    @Test
    void simplify_zero() {
        Expression e = new Sub(new Variable("x"), new Number(0));
        assertEquals(new Variable("x"), e.simplify());
    }

    @Test
    void simplify_no_simplify() {
        Expression e = new Sub(new Variable("x"), new Variable("y"));
        assertEquals(e, e.simplify());
    }

    @Test
    void simplify_null() {
        Expression e = new Sub(null, null);
        assertNull(e.simplify());
    }
}