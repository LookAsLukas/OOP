package ru.nsu.nmashkin.task113;

public class Token {
    private final String value;
    private final boolean operator;

    public Token(String val) {
        value = val;
        operator = false;
    }

    public Token(Operator op) {
        value = String.valueOf(op.getValue());
        operator = true;
    }

    public String getValue() {
        return value;
    }

    public boolean isOperator() {
        return operator;
    }
}
