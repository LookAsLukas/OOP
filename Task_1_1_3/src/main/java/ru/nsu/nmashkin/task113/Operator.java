package ru.nsu.nmashkin.task113;

public enum Operator {
    MUL('*', 2),
    DIV('/', 2),
    ADD('+', 1),
    SUB('-', 1),
    LPR('(', -1),
    RPR(')', -1),
    ERR(' ', -2);

    private final int precedence;
    private final char value;

    private Operator(char val, int prec) {
        precedence = prec;
        value = val;
    }

    public static int determinePrecedence(char symbol) {
        return switch (symbol) {
            case '*', '/' -> 2;
            case '-', '+' -> 1;
            case '(', ')' -> -1;
            default -> -2;
        };
    }

    public static Operator makeOperator(char symbol) {
        return switch (symbol) {
            case '*' -> MUL;
            case '/' -> DIV;
            case '+' -> ADD;
            case '-' -> SUB;
            case '(' -> LPR;
            case ')' -> RPR;
            default -> ERR;
        };
    }

    public static boolean isArithmeticOperator(char symbol) {
        return switch (symbol) {
            case '*', '/', '+', '-' -> true;
            default -> false;
        };
    }

    public int getPrecedence() {
        return precedence;
    }

    public char getValue() {
        return value;
    }
}
