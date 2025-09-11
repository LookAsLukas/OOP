package ru.nsu.nmashkin.task113;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

class MulTest {

    @Test
    void eval_nums() {
        Expression e = new Mul(new Number(5), new Number(3));
        assertEquals(15, e.eval(""));
    }

    @Test
    void eval_vars() {
        Expression e = new Mul(new Variable("x"), new Variable("y"));
        assertEquals(15, e.eval("x=5;y=3"));
    }

    @Test
    void eval_null() {
        Expression e = new Mul(null, null);
        assertEquals(Double.POSITIVE_INFINITY, e.eval(""));
    }

    @Test
    void derivative_bound_var() {
        Expression e = new Mul(new Variable("x"), new Variable("y"));
        assertEquals(new Add(new Mul(new Number(1), new Variable("y")),
                             new Mul(new Variable("x"), new Number(0))),
                     e.derivative("x"));
    }

    @Test
    void derivative_free_var() {
        Expression e = new Mul(new Variable("x"), new Variable("y"));
        assertEquals(new Add(new Mul(new Number(0), new Variable("y")),
                             new Mul(new Variable("x"), new Number(0))),
                     e.derivative("z"));
    }

    @Test
    void derivative_null() {
        Expression e = new Mul(null, null);
        assertNull(e.derivative(""));
    }

    @Test
    void simplify_nums() {
        Expression e = new Mul(new Number(5), new Number(3));
        assertEquals(new Number(15), e.simplify());
    }

    @Test
    void simplify_zero_1() {
        Expression e = new Mul(new Number(0), new Variable("x"));
        assertEquals(new Number(0), e.simplify());
    }

    @Test
    void simplify_zero_2() {
        Expression e = new Mul(new Variable("x"), new Number(0));
        assertEquals(new Number(0), e.simplify());
    }

    @Test
    void simplify_one_1() {
        Expression e = new Mul(new Number(1), new Variable("x"));
        assertEquals(new Variable("x"), e.simplify());
    }

    @Test
    void simplify_one_2() {
        Expression e = new Mul(new Variable("x"), new Number(1));
        assertEquals(new Variable("x"), e.simplify());
    }

    @Test
    void simplify_no_simplify() {
        Expression e = new Mul(new Number(2), new Variable("x"));
        assertEquals(e, e.simplify());
    }

    @Test
    void simplify_null() {
        Expression e = new Mul(null, null);
        assertNull(e.derivative(""));
    }
}