package ru.nsu.nmashkin.task113;

/**
 * Custom exception class for Parser.
 */
public class ParserException extends RuntimeException {
    /**
     * Exception constructor.
     *
     * @param message exception message
     */
    public ParserException(String message) {
        super(message);
    }
}
