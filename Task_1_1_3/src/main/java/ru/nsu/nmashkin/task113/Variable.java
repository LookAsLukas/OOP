package ru.nsu.nmashkin.task113;

import java.util.Scanner;

public class Variable extends Expression {
    private final String name;

    public Variable(String varName) {
        name = varName;
    }

    @Override
    public double eval(String vars) {
        Scanner scanner = new Scanner(vars);
        scanner.useDelimiter("[; =]+");
        while (!scanner.next().equals(name)) {
            scanner.next();
        }

        double result = Double.parseDouble(scanner.next());
        scanner.close();
        return result;
    }

    @Override
    public void print() {
        System.out.printf("%s", name);
    }

    @Override
    public Expression derivative(String var) {
        return new Number(var.equals(name) ? 1 : 0);
    }
}
