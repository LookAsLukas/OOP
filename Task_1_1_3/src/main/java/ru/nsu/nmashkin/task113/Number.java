package ru.nsu.nmashkin.task113;

public class Number extends Expression {
    private final double value;

    public Number(double val) {
        value = val;
    }

    @Override
    public double eval(String vars) {
        return value;
    }

    @Override
    public void print() {
        System.out.printf("%f", value);
    }

    @Override
    public Expression derivative(String var) {
        return new Number(0);
    }
}
