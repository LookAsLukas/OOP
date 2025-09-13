package ru.nsu.nmashkin.task113;

/**
 * Token for parsing.
 */
public class Token {
    private final String value;
    private final boolean operator;

    /**
     * Make a Token from a string.
     *
     * @param val source
     */
    public Token(String val) {
        value = val;
        operator = false;
    }

    /**
     * Make a Token from an Operator.
     *
     * @param op source
     */
    public Token(Operator op) {
        value = String.valueOf(op.getValue());
        operator = true;
    }

    /**
     * Get the Token's value.
     *
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * Check if the Token is an operator.
     *
     * @return true if is an operator
     */
    public boolean isOperator() {
        return operator;
    }
}
