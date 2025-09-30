package ru.nsu.nmashkin.task121;

/**
 * Graph topological sort exceptions class.
 */
public class GraphSortException extends RuntimeException {
    /**
     * Create an Exception.
     *
     * @param message exception message
     */
    public GraphSortException(String message) {
        super(message);
    }
}
