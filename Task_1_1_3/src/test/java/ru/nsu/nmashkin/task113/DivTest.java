package ru.nsu.nmashkin.task113;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class DivTest {

    @Test
    void eval_nums() {
        Expression e = new Div(new Number(5), new Number(3));
        assertEquals(5.0 / 3, e.eval(""));
    }

    @Test
    void eval_vars() {
        Expression e = new Div(new Variable("x"), new Variable("y"));
        assertEquals(5.0 / 3, e.eval("x=5;y=3"));
    }

    @Test
    void eval_null() {
        Expression e = new Div(null, null);
        assertEquals(Double.POSITIVE_INFINITY, e.eval(""));
    }

    @Test
    void derivative_bound() {
        Expression e = new Div(new Variable("x"), new Variable("y"));
        assertEquals(new Div(new Sub(new Mul(new Number(1), new Variable("y")),
                                     new Mul(new Variable("x"), new Number(0))),
                             new Mul(new Variable("y"), new Variable("y"))),
                     e.derivative("x"));
    }

    @Test
    void derivative_free() {
        Expression e = new Div(new Variable("x"), new Variable("y"));
        assertEquals(new Div(new Sub(new Mul(new Number(0), new Variable("y")),
                                     new Mul(new Variable("x"), new Number(0))),
                             new Mul(new Variable("y"), new Variable("y"))),
                     e.derivative("z"));
    }

    @Test
    void derivative_null() {
        Expression e = new Div(null, null);
        assertNull(e.derivative(""));
    }

    @Test
    void simplify_two_nums() {
        Expression e = new Div(new Number(5), new Number(3));
        assertEquals(new Number(5.0 / 3), e.simplify());
    }

    @Test
    void simplify_zero() {
        Expression e = new Div(new Number(0), new Variable("x"));
        assertEquals(new Number(0), e.simplify());
    }

    @Test
    void simplify_one() {
        Expression e = new Div(new Variable("x"), new Number(1));
        assertEquals(new Variable("x"), e.simplify());
    }

    @Test
    void simplify_no_simplify() {
        Expression e = new Div(new Variable("x"), new Number(9));
        assertEquals(e, e.simplify());
    }

    @Test
    void simplify_null() {
        Expression e = new Div(null, null);
        assertNull(e.simplify());
    }

    @Test
    void zero_division() {
        Expression e = new Div(new Number(1), new Number(0));
        assertEquals(Double.POSITIVE_INFINITY, e.eval(""));
    }

    @Test
    void zero_by_zero_division() {
        Expression e = new Div(new Number(0), new Number(0));
        assertEquals(Double.POSITIVE_INFINITY, e.eval(""));
    }
}