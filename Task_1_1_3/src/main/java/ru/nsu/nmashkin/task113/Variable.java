package ru.nsu.nmashkin.task113;

import java.util.Objects;
import java.util.Scanner;

/**
 * Variable.
 */
public class Variable extends Expression {
    private final String name;

    /**
     * Make a Variable with a specified name. If fails, name is set to null.
     *
     * @param varName provided name
     */
    public Variable(String varName) {
        super(varName);
        name = varName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double eval(String vars) {
        Scanner scanner = new Scanner(vars);
        scanner.useDelimiter("[; =]+");
        while (scanner.hasNext() && !scanner.next().equals(name)) {
            scanner.next();
        }

        if (!scanner.hasNext()) {
            throw new ExpressionException("No value provided for a variable");
        }

        double result;
        try {
            result = Double.parseDouble(scanner.next());
        } catch (NumberFormatException e) {
            throw new ExpressionException("Invalid variable value provided");
        } finally {
            scanner.close();
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void print() {
        System.out.print(name);
    }

    @Override
    public Expression derivative(String var) {
        return new Number(var.equals(name) ? 1 : 0);
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

        Variable o = (Variable) obj;
        return name.equals(o.name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
