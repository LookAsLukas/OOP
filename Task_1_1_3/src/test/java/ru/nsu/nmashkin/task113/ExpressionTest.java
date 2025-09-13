package ru.nsu.nmashkin.task113;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ExpressionTest {

    @Test
    void fromString_incorrect_brackets_1() {
        RuntimeException ex = assertThrows(RuntimeException.class,
                                           () -> { Expression.fromString("((x)"); });
        assertEquals("Invalid brackets pairing in the expression", ex.getMessage());
    }

    @Test
    void fromString_incorrect_brackets_2() {
        RuntimeException ex = assertThrows(RuntimeException.class,
                                           () -> { Expression.fromString("(x))"); });
        assertEquals("Invalid brackets pairing in the expression", ex.getMessage());
    }

    @Test
    void fromString_incorrect_operators_1() {
        RuntimeException ex = assertThrows(RuntimeException.class,
                                           () -> { Expression.fromString("x+"); });
        assertEquals("Invalid operator or operand placement", ex.getMessage());
    }

    @Test
    void fromString_incorrect_operators_2() {
        RuntimeException ex = assertThrows(RuntimeException.class,
                                           () -> { Expression.fromString("x++y"); });
        assertEquals("Invalid operator or operand placement", ex.getMessage());
    }

    @Test
    void fromString_incorrect_operators_3() {
        RuntimeException ex = assertThrows(RuntimeException.class,
                                           () -> { Expression.fromString("+x"); });
        assertEquals("Invalid operator or operand placement", ex.getMessage());
    }

    @Test
    void fromString_incorrect_operators_4() {
        RuntimeException ex = assertThrows(RuntimeException.class,
                                           () -> { Expression.fromString("+"); });
        assertEquals("Invalid operator or operand placement", ex.getMessage());
    }

    @Test
    void fromString_incorrect_operands() {
        RuntimeException ex = assertThrows(RuntimeException.class,
                                           () -> { Expression.fromString("(x+y)1"); });
        assertEquals("Invalid operator or operand placement", ex.getMessage());
    }

    @Test
    void fromString_incorrect_variable() {
        RuntimeException ex = assertThrows(RuntimeException.class,
                                           () -> { Expression.fromString("1y + 3"); });
        assertEquals("Invalid operator or operand placement", ex.getMessage());
    }

    @Test
    void fromString_jibberish() {
        RuntimeException ex = assertThrows(RuntimeException.class,
                                           () -> { Expression.fromString("+=28jsh;'"); });
        assertEquals("Unrecognised character detected", ex.getMessage());
    }

    @Test
    void fromString_normal() {
        Expression e = Expression.fromString("x / y + 1 - 0 * z");
        assertEquals(new Sub(new Add(new Div(new Variable("x"), new Variable("y")),
                                     new Number(1)),
                             new Mul(new Number(0), new Variable("z"))), e);
    }
}