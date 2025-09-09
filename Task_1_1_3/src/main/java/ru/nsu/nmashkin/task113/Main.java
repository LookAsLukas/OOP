package ru.nsu.nmashkin.task113;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Expression e = new Add(new Number(3), new Mul(new Number(2),
                new Variable("x")));
        e.print();
        System.out.println();
        Expression de = e.derivative("x");
        de.print();
        System.out.println();
        e.print();
        System.out.println();
        double result = e.eval("x = 10; y = 13");
        System.out.println(result);
    }
}
