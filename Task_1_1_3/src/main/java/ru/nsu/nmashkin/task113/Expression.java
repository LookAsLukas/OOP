package ru.nsu.nmashkin.task113;

import java.util.*;

public abstract class Expression {
    protected Expression[] children = null;

    public Expression fromString(String str) {
    }

    public abstract double eval(String vars);
    public abstract void print();
    public abstract Expression derivative(String var);
}
