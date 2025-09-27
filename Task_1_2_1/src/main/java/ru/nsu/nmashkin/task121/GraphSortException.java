package ru.nsu.nmashkin.task121;

/**
 * Graph topological sort exceptions class.
 */
public class GraphSortException extends RuntimeException {
    /**
     * {@inheritDoc}
     */
    public GraphSortException(String message) {
        super(message);
    }
}
