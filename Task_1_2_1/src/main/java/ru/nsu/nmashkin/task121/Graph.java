package ru.nsu.nmashkin.task121;

import java.util.List;

/**
 * Interface for oriented graphs with loops allowed.
 */
public interface Graph {
    /**
     * Add a vertex to the Graph.
     *
     * @param v vertex to be added
     */
    void addVertex(Object v);

    /**
     * Remove a vertex from the Graph.
     *
     * @param v vertex to be removed
     */
    void removeVertex(Object v);

    /**
     * Add an edge to the Graph.
     *
     * @param v1 starting vertex
     * @param v2 ending vertex
     */
    void addEdge(Object v1, Object v2);

    /**
     * Remove an edge from the Graph.
     *
     * @param v1 starting vertex
     * @param v2 ending vertex
     */
    void removeEdge(Object v1, Object v2);

    /**
     * Get the list of all vertices.
     *
     * @return the said list
     */
    List<Object> getVertices();

    /**
     * Get a list of all neighbouring vertices.
     * Vertex is a neighbor to another vertex if there is an edge from the former to the latter.
     *
     * @param v vertex, whose neighbors will be returned
     * @return list of neighbors, null if there is no such vertex v
     */
    List<Object> getNeighbors(Object v);

    /**
     * Load graph from a file.
     * Graph should be written as a list of edges "start end" each on a new line.
     * For example:
     * 0 1
     * 0 2
     * 1 2.
     *
     * @param filename name of the source file
     * @throws GraphLoadException if file is in incorrect format of does not exist
     */
    void loadFromFile(String filename) throws GraphLoadException;

    /**
     * Check if Graphs are equal.
     * Different implementations can never be equal.
     *
     * @param o the reference object with which to compare
     * @return true if equal
     */
    boolean equals(Object o);

    /**
     * Not implemented.
     *
     * @return nothing
     */
    int hashCode();

    /**
     * Get a string representation of the Graph.
     * Different implementations may have different representations.
     *
     * @return string representation
     */
    String toString();
}