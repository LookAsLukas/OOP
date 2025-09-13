package ru.nsu.nmashkin.task113;

import java.util.Queue;
import java.util.Stack;

/**
 * Any expression.
 */
public abstract class Expression {
    protected Expression[] children = null;

    protected Expression(String varName) {
        if (varName.isEmpty()) {
            throw new RuntimeException("Cannot create a Variable with an empty name");
        }
        if (Character.isDigit(varName.charAt(0))) {
            throw new RuntimeException("Variable name cannot start with a digit");
        }
        for (char ch : varName.toCharArray()) {
            if (!Character.isAlphabetic(ch) && !Character.isDigit(ch)) {
                throw new RuntimeException("Variable name must be alphanumeric");
            }
        }
    }

    protected Expression(double val) {
        if (Double.isNaN(val)) {
            throw new RuntimeException("Number value cannot be NaN");
        }
    }

    /**
     * Construct an operator.
     *
     * @param op1 operand 1
     * @param op2 operand 2
     */
    public Expression(Expression op1, Expression op2) {
        if (op1 == null || op2 == null) {
            throw new RuntimeException("Cannot create an Expression with null operands");
        }
        children = new Expression[2];
        children[0] = op1;
        children[1] = op2;
    }

    /**
     * Make an Expression from string.
     *
     * @param str expression string
     * @return corresponding Expression
     */
    public static Expression fromString(String str) {
        Queue<Token> reversePolish = Parser.parseString(str);

        Stack<Expression> tokens = new Stack<>();
        Expression result;

        while (!reversePolish.isEmpty()) {
            Token tok = reversePolish.poll();

            if (tok.isOperator()) {
                if (tokens.isEmpty()) {
                    throw new RuntimeException("Invalid operator or operand placement");
                }
                Expression op1 = tokens.pop();

                if (tokens.isEmpty()) {
                    throw new RuntimeException("Invalid operator or operand placement");
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
                    throw new RuntimeException("Unreachable");
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
            throw new RuntimeException("Invalid operator or operand placement");
        }
        return result;
    }

    protected Expression[] getChildren() {
        return children;
    }

    /**
     * Print the expression.
     */
    public void print() {
        System.out.print("(");
        children[0].print();
        System.out.print(" + ");
        children[1].print();
        System.out.print(")");
    }

    /**
     * Evaluate the expression. If fails, return infinity.
     *
     * @param vars variable values
     * @return evaluation result
     */
    public abstract double eval(String vars);

    /**
     * Take the derivative with respect to var.
     *
     * @param var derivation variable
     * @return derivation result
     */
    public abstract Expression derivative(String var);

    /**
     * Simplify the expression if possible.
     *
     * @return simplified result
     */
    public abstract Expression simplify();
}
