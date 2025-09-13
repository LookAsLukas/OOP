package ru.nsu.nmashkin.task113;

public class ParserException extends RuntimeException {
    /**
     * Custom exception class for Parser.
     *
     * @param message exception message
     */
    public ParserException(String message) {
        super(message);
    }
}
