package ru.nsu.nmashkin.task113;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class VariableTest {

    @Test
    void eval_no_value() {
        Expression e = new Variable("x");
        RuntimeException ex = assertThrows(RuntimeException.class, () -> { e.eval(""); });
        assertEquals("No value provided for a variable", ex.getMessage());
    }

    @Test
    void eval_invalid_value() {
        Expression e = new Variable("x");
        RuntimeException ex = assertThrows(RuntimeException.class, () -> { e.eval("x = _"); });
        assertEquals("Invalid variable value provided", ex.getMessage());
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
    void construct_empty() {
        RuntimeException ex = assertThrows(RuntimeException.class, () -> { new Variable(""); });
        assertEquals("Cannot create a Variable with an empty name", ex.getMessage());
    }

    @Test
    void construct_digit() {
        RuntimeException ex = assertThrows(RuntimeException.class, () -> { new Variable("1"); });
        assertEquals("Variable name cannot start with a digit", ex.getMessage());
    }

    @Test
    void construct_non_alphanumeric() {
        RuntimeException ex = assertThrows(RuntimeException.class, () -> { new Variable("_"); });
        assertEquals("Variable name must be alphanumeric", ex.getMessage());
    }
}