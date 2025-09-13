package ru.nsu.nmashkin.task113;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class AddTest {

    @Test
    void eval_nums() {
        Expression e = new Add(new Number(1), new Number(2));
        assertEquals(3, e.eval(""));
    }

    @Test
    void eval_vars() {
        Expression e = new Add(new Variable("x"), new Variable("y"));
        assertEquals(3, e.eval("y = 10; x = -7"));
    }

    @Test
    void derivative_bound() {
        Expression e = new Add(new Variable("x"), new Variable("y"));
        assertEquals(new Add(new Number(1), new Number(0)),
                     e.derivative("x"));
    }

    @Test
    void derivative_free() {
        Expression e = new Add(new Variable("x"), new Variable("y"));
        assertEquals(new Add(new Number(0), new Number(0)),
                     e.derivative("z"));
    }

    @Test
    void simplify_two_nums() {
        Expression e = new Add(new Number(1), new Number(2));
        assertEquals(new Number(3), e.simplify());
    }

    @Test
    void simplify_zero_1() {
        Expression e = new Add(new Number(0), new Variable("x"));
        assertEquals(new Variable("x"), e.simplify());
    }

    @Test
    void simplify_zero_2() {
        Expression e = new Add(new Variable("x"), new Number(0));
        assertEquals(new Variable("x"), e.simplify());
    }

    @Test
    void simplify_no_simplify() {
        Expression e = new Add(new Variable("x"), new Variable("y"));
        assertEquals(e, e.simplify());
    }

    @Test
    void construct_null() {
        assertThrows(ExpressionException.class, () -> {
            new Add(null, null);
        });
    }
}