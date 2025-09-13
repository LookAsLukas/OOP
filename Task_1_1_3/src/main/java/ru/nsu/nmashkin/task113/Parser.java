package ru.nsu.nmashkin.task113;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * Parser.
 */
public class Parser {
    /**
     * Parse a string with an arithmetic expression. If fails, return null.
     *
     * @param toParse string to parse
     * @return parsed expression in Reverse Polish Notation
     */
    public static Queue<Token> parseString(String toParse) {
        Stack<Operator> operators = new Stack<>();
        Queue<Token> output = new LinkedList<>();

        for (int i = 0; i < toParse.length(); i++) {
            char ch = toParse.charAt(i);
            if (ch == ' ') {
                continue;
            }

            if (Operator.isArithmeticOperator(ch)) {
                while (!operators.isEmpty()
                        && operators.peek().getPrecedence() >= Operator.determinePrecedence(ch)
                        && operators.peek() != Operator.LPR) {
                    output.add(new Token(operators.pop()));
                }
                operators.push(Operator.makeOperator(ch));
            } else if (ch == '(') {
                operators.push(Operator.makeOperator(ch));
            } else if (ch == ')') {
                while (!operators.isEmpty()
                        && operators.peek() != Operator.LPR) {
                    output.add(new Token(operators.pop()));
                }
                if (operators.isEmpty()) {
                    throw new RuntimeException("Invalid brackets pairing in the expression");
                }
                operators.pop();
            } else if (Character.isDigit(ch)) {
                int start = i;
                boolean seenDot = false;
                while (Character.isDigit(toParse.charAt(i))
                       || (!seenDot && toParse.charAt(i) == '.')) {
                    if (toParse.charAt(i) == '.') {
                        seenDot = true;
                    }

                    i++;

                    if (i == toParse.length()) {
                        break;
                    }
                }

                String val = toParse.substring(start, i);
                output.add(new Token(val));
                i--;
            } else if (Character.isAlphabetic(ch)) {
                int start = i;
                while (Character.isDigit(toParse.charAt(i))
                       || Character.isAlphabetic(toParse.charAt(i))) {
                    i++;

                    if (i == toParse.length()) {
                        break;
                    }
                }

                String val = toParse.substring(start, i);
                output.add(new Token(val));
                i--;
            } else {
                throw new RuntimeException("Unrecognised character detected");
            }
        }

        while (!operators.isEmpty()) {
            Operator op = operators.pop();
            if (op == Operator.LPR) {
                throw new RuntimeException("Invalid brackets pairing in the expression");
            }
            output.add(new Token(op));
        }

        return output;
    }
}
