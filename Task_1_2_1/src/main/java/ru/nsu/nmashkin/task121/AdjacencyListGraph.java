package ru.nsu.nmashkin.task121;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of a Graph via adjacency lists.
 */
public class AdjacencyListGraph implements Graph {
    private final Map<Object, List<Object>> adjList;

    /**
     * Initialize the Graph.
     */
    public AdjacencyListGraph() {
        adjList = new HashMap<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addVertex(Object v) {
        adjList.putIfAbsent(v, new ArrayList<>());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeVertex(Object v) {
        adjList.remove(v);
        for (List<Object> neighbors : adjList.values()) {
            neighbors.remove(v);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addEdge(Object v1, Object v2) {
        if (!adjList.containsKey(v1) || !adjList.containsKey(v2)) {
            return;
        }
        if (adjList.get(v1).contains(v2)) {
            return;
        }

        adjList.get(v1).add(v2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeEdge(Object v1, Object v2) {
        List<Object> neighbors = adjList.get(v1);
        if (neighbors != null) {
            neighbors.remove(v2);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Object> getVertices() {
        return new ArrayList<>(adjList.keySet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Object> getNeighbors(Object v) {
        if (!adjList.containsKey(v)) {
            return null;
        }
        return new ArrayList<>(adjList.get(v));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            adjList.clear();
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.trim().split("\\s+");
                if (parts.length == 2) {
                    Object v1 = parts[0];
                    Object v2 = parts[1];
                    addVertex(v1);
                    addVertex(v2);
                    addEdge(v1, v2);
                }
            }
        } catch (IOException e) {
            throw new GraphLoadException(e.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (o.getClass() != this.getClass()) {
            return false;
        }

        AdjacencyListGraph other = (AdjacencyListGraph) o;
        return adjList.equals(other.adjList);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Object, List<Object>> entry : adjList.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }
}
