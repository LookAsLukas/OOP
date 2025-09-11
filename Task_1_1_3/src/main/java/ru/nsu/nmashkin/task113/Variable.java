package ru.nsu.nmashkin.task113;

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
        if (varName.isEmpty()) {
            System.err.println("WARNING: Invalid variable name defaults to null");
            name = null;
            return;
        }
        if (Character.isDigit(varName.charAt(0))) {
            System.err.println("WARNING: Invalid variable name defaults to null");
            name = null;
            return;
        }
        for (char ch : varName.toCharArray()) {
            if (!Character.isAlphabetic(ch) && !Character.isDigit(ch)) {
                System.err.println("WARNING: Invalid variable name defaults to null");
                name = null;
                return;
            }
        }
        name = varName;
    }

    /**
     * Get the variable name.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double eval(String vars) {
        if (name == null) {
            System.err.println("WARNING: Evaluating expression containing null variable name" +
                               "results in the value being infinite");
            return Double.POSITIVE_INFINITY;
        }

        Scanner scanner = new Scanner(vars);
        scanner.useDelimiter("[; =]+");
        while (scanner.hasNext() && !scanner.next().equals(name)) {
            scanner.next();
        }

        if (!scanner.hasNext()) {
            System.err.println("WARNING: Evaluating expression without providing variable value " +
                               "results in the value being infinite");
            return Double.POSITIVE_INFINITY;
        }

        double result;
        try {
            result = Double.parseDouble(scanner.next());
        } catch (NumberFormatException e) {
            System.err.println("WARNING: Evaluating expression with invalid variable value " +
                               "results in the value being infinite");
            result = Double.POSITIVE_INFINITY;
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
        if (name == null) {
            System.err.println("WARNING: Printing a null variable name");
            System.out.println("{null}");
            return;
        }
        System.out.print(name);
    }

    @Override
    public Expression derivative(String var) {
        if (name == null) {
            System.err.println("WARNING: Differentiation of a null named variable " +
                               "results in a value being infinite");
            return new Number(Double.POSITIVE_INFINITY);
        }
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
}
