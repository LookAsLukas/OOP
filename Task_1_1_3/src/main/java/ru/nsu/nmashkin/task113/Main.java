package ru.nsu.nmashkin.task113;

import java.util.Scanner;

/**
 * Showcase.
 */
public class Main {
    /**
     * Showcase.
     *
     * @param args poplach
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter an expression: ");
        Expression e = Expression.fromString(in.nextLine());
        if (e == null) {
            System.err.println("\nERROR: Invalid expression");
            in.close();
            return;
        }

        System.out.print("\nFull form: ");
        e.print();
        System.out.println();

        System.out.print("\nSimplified form: ");
        e.simplify().print();
        System.out.println();

        System.out.print("\nEnter a derivation variable: ");
        String derivationVar = in.nextLine();
        in.close();

        Expression de = e.derivative(derivationVar);
        System.out.println("\nDerivative full form: ");
        de.print();
        System.out.println();

        System.out.println("\nDerivative simplified form: ");
        de.simplify().print();
        System.out.println();
    }
}
