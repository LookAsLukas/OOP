package ru.nsu.nmashkin.task113;

import java.util.Queue;
import java.util.Stack;

/**
 * Any expression.
 */
public abstract class Expression {
    protected Expression[] children = null;

    /**
     * Make an Expression from string. If fails, return null.
     *
     * @param str expression string
     * @return corresponding Expression
     */
    public static Expression fromString(String str) {
        Queue<Token> reversePolish = Parser.parseString(str);
        if (reversePolish == null) {
            System.err.println("WARNING: Invalid expression defaults to null");
            return null;
        }

        Stack<Expression> tokens = new Stack<>();
        Expression result;

        while (!reversePolish.isEmpty()) {
            Token tok = reversePolish.poll();

            if (tok.isOperator()) {
                if (tokens.isEmpty()) {
                    System.err.println("WARNING: Invalid expression defaults to null");
                    return null;
                }
                Expression op1 = tokens.pop();

                if (tokens.isEmpty()) {
                    System.err.println("WARNING: Invalid expression defaults to null");
                    return null;
                }
                Expression op2 = tokens.pop();

                Expression exp = switch (tok.getValue()) {
                    case "+" -> new Add(op2, op1);
                    case "-" -> new Sub(op2, op1);
                    case "*" -> new Mul(op2, op1);
                    case "/" -> new Div(op2, op1);
                    default -> null;
                };
                if (exp == null) {
                    System.err.println("Unreachable");
                    return null;
                }

                tokens.push(exp);
                continue;
            }

            String tokValue = tok.getValue();
            boolean hasLetters = false;
            for (char ch : tokValue.toCharArray()) {
                if (Character.isAlphabetic(ch)) {
                    hasLetters = true;
                }
            }

            tokens.push(hasLetters
                        ? new Variable(tokValue)
                        : new Number(Double.parseDouble(tokValue)));
        }

        result = tokens.pop();
        if (!tokens.isEmpty()) {
            System.err.println("WARNING: Invalid expression defaults to null");
            return null;
        }
        return result;
    }

    /**
     * Get operands.
     *
     * @return operands
     */
    public Expression[] getChildren() {
        return children;
    }

    /**
     * Evaluate the expression. If fails, return infinity.
     *
     * @param vars variable values
     * @return evaluation result
     */
    public abstract double eval(String vars);

    /**
     * Print the expression.
     */
    public abstract void print();

    /**
     * Take the derivative with respect to var. If fails, return null.
     *
     * @param var derivation variable
     * @return derivation result
     */
    public abstract Expression derivative(String var);

    /**
     * Simplify the expression if possible. If fails, return null.
     *
     * @return simplified result
     */
    public abstract Expression simplify();
}
