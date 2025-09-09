package ru.nsu.nmashkin.task113;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Parser {
    public static Queue<Token> parseString(String toParse) {
        Stack<Operator> operators = new Stack<>();
        Queue<Token> output = new LinkedList<>();

        for (int i = 0; i < toParse.length(); i++) {
            char ch = toParse.charAt(i);
            if (ch == ' ') {
                continue;
            }

            if (Operator.isArithmeticOperator(ch)) {
                while (!operators.isEmpty() &&
                        operators.peek().getPrecedence() >= Operator.determinePrecedence(ch) &&
                        operators.peek() != Operator.LPR) {
                    output.add(new Token(operators.pop()));
                }
                operators.push(Operator.makeOperator(ch));
            } else if (ch == '(') {
                operators.push(Operator.makeOperator(ch));
            } else if (ch == ')') {
                while (!operators.isEmpty() &&
                        operators.peek() != Operator.LPR) {
                    output.add(new Token(operators.pop()));
                }
                if (operators.isEmpty()) {
                    System.out.println("panic");
                    return null;
                }
                operators.pop();
            } else if (Character.isDigit(ch)) {
                Scanner scanner = new Scanner(toParse.substring(i));
                String val = String.valueOf(scanner.nextDouble());
                scanner.close();
                output.add(new Token(val));
                i += val.length() - 1;
            } else if (Character.isAlphabetic(ch)) {
                Scanner scanner = new Scanner(toParse.substring(i));
                String val = scanner.next("[a-zA-Z][a-zA-Z0-9]*");
                scanner.close();
                output.add(new Token(val));
                i += val.length() - 1;
            } else {
                System.out.println("panic");
            }

        }

        return null;

    }
}
