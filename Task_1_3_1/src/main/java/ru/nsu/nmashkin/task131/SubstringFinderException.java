package ru.nsu.nmashkin.task131;

/**
 * Exception class for SubstringFinder.
 */
public class SubstringFinderException extends RuntimeException {
    /**
     * Initialize the exception.
     *
     * @param message exception message
     * @param cause exception cause
     */
    public SubstringFinderException(String message, Throwable cause) {
        super(message, cause);
    }
}
