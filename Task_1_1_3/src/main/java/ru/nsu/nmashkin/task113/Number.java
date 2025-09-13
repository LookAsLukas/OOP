package ru.nsu.nmashkin.task113;

import java.util.Objects;

/**
 * Number.
 */
public class Number extends Expression {
    private final double value;

    /**
     * Make a Number from a double.
     *
     * @param val source
     */
    public Number(double val) {
        super(val);
        value = val;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double eval(String vars) {
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void print() {
        System.out.print(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Expression derivative(String var) {
        return new Number(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Expression simplify() {
        return this;
    }

    /**
     * Compare to an object.
     *
     * @param obj   the reference object with which to compare.
     * @return true if equal
     */
    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != getClass()) {
            return false;
        }

        Number o = (Number) obj;
        return value == o.value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
