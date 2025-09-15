package ru.nsu.nmashkin.task113;

/**
 * Custom exception class for Expression.
 */
public class ExpressionException extends RuntimeException {
    /**
     * Exception constructor.
     *
     * @param message exception message
     */
    public ExpressionException(String message) {
        super(message);
    }
}
