package ru.nsu.nmashkin.task121;

/**
 * Graph exceptions class.
 */
public abstract class GraphException extends RuntimeException {
    /**
     * Create an Exception.
     *
     * @param message exception message
     */
    public GraphException(String message) {
        super(message);
    }
}
