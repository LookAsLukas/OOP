package ru.nsu.nmashkin.task113;

/**
 * Operator for parsing.
 */
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

    /**
     * Determine the operator precedence.
     *
     * @param symbol contains the operator
     * @return precedence
     */
    public static int determinePrecedence(char symbol) {
        return switch (symbol) {
            case '*', '/' -> 2;
            case '-', '+' -> 1;
            case '(', ')' -> -1;
            default -> -2;
        };
    }

    /**
     * Make an Operator from a symbol.
     *
     * @param symbol source
     * @return Operator
     */
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

    /**
     * Check if the symbol is an arithmetic operator.
     *
     * @param symbol to check
     * @return true if is an arithmetic operator
     */
    public static boolean isArithmeticOperator(char symbol) {
        return switch (symbol) {
            case '*', '/', '+', '-' -> true;
            default -> false;
        };
    }

    /**
     * Get Operator's precedence.
     *
     * @return precedence
     */
    public int getPrecedence() {
        return precedence;
    }

    /**
     * Get operator's symbol value.
     *
     * @return symbol value
     */
    public char getValue() {
        return value;
    }
}
