package ru.nsu.nmashkin.task121;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Implementation of a Graph via adjacency lists.
 */
public class AdjacencyListGraph implements Graph {
    private final Map<Integer, List<Integer>> adjList;

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
    public void addVertex(int v) {
        adjList.putIfAbsent(v, new ArrayList<>());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeVertex(int v) {
        adjList.remove(v);
        for (List<Integer> neighbors : adjList.values()) {
            neighbors.remove(Integer.valueOf(v));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addEdge(int v1, int v2) {
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
    public void removeEdge(int v1, int v2) {
        List<Integer> neighbors = adjList.get(v1);
        if (neighbors != null) {
            neighbors.remove(Integer.valueOf(v2));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Integer> getNeighbors(int v) {
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
                if (parts.length >= 2) {
                    int v1 = Integer.parseInt(parts[0]);
                    int v2 = Integer.parseInt(parts[1]);
                    addVertex(v1);
                    addVertex(v2);
                    addEdge(v1, v2);
                }
            }
        } catch (IOException | NumberFormatException e) {
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
        for (Map.Entry<Integer, List<Integer>> entry : adjList.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Integer> topologicalSort() throws GraphSortException {
        Map<Integer, Integer> penetrations = new HashMap<>();
        for (Integer v : adjList.keySet()) {
            penetrations.put(v, 0);
        }
        for (List<Integer> neighbors : adjList.values()) {
            for (Integer neighbor : neighbors) {
                penetrations.put(neighbor, penetrations.getOrDefault(neighbor, 0) + 1);
            }
        }

        Queue<Integer> queue = new LinkedList<>();
        for (Map.Entry<Integer, Integer> entry : penetrations.entrySet()) {
            if (entry.getValue() == 0) {
                queue.add(entry.getKey());
            }
        }

        List<Integer> sorted = new ArrayList<>();
        while (!queue.isEmpty()) {
            int v = queue.poll();
            sorted.add(v);
            for (Integer neighbor : adjList.getOrDefault(v, Collections.emptyList())) {
                penetrations.put(neighbor, penetrations.get(neighbor) - 1);
                if (penetrations.get(neighbor) == 0) {
                    queue.add(neighbor);
                }
            }
        }

        if (sorted.size() != adjList.size()) {
            throw new GraphSortException("Graph has cycles");
        }
        return sorted;
    }
}
