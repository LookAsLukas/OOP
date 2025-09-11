package ru.nsu.nmashkin.task113;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

class ExpressionTest {

    @Test
    void fromString_incorrect_brackets() {
        Expression e = Expression.fromString("((x)");
        assertNull(e);
    }

    @Test
    void fromString_incorrect_operators_1() {
        Expression e = Expression.fromString("x+");
        assertNull(e);
    }

    @Test
    void fromString_incorrect_operators_2() {
        Expression e = Expression.fromString("x++y");
        assertNull(e);
    }

    @Test
    void fromString_incorrect_operators_3() {
        Expression e = Expression.fromString("+x");
        assertNull(e);
    }

    @Test
    void fromString_incorrect_operators_4() {
        Expression e = Expression.fromString("+");
        assertNull(e);
    }

    @Test
    void fromString_incorrect_operands() {
        Expression e = Expression.fromString("(x+y)1");
        assertNull(e);
    }

    @Test
    void fromString_incorrect_variable() {
        Expression e = Expression.fromString("1y + 3");
        assertNull(e);
    }

    @Test
    void fromString_jibberish() {
        Expression e = Expression.fromString("+=28jsh;'\"");
        assertNull(e);
    }

    @Test
    void fromString_normal() {
        Expression e = Expression.fromString("x / y + 1 - 0 * z");
        assertEquals(new Sub(new Add(new Div(new Variable("x"), new Variable("y")),
                                     new Number(1)),
                             new Mul(new Number(0), new Variable("z"))), e);
    }

    @Test
    void print() {
        Expression e = new Variable("justForCoverage");
        assertNull(e.getChildren());
    }
}