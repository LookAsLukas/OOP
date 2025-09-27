package ru.nsu.nmashkin.task121;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Implementation of a Graph via adjacency matrix.
 */
public class AdjacencyMatrixGraph implements Graph {
    private boolean[][] adjMatrix;
    private final List<Integer> vertices;

    /**
     * Initialize the Graph.
     */
    public AdjacencyMatrixGraph() {
        vertices = new ArrayList<>();
        adjMatrix = new boolean[0][0];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addVertex(int v) {
        if (vertices.contains(v)) {
            return;
        }

        vertices.add(v);
        int size = vertices.size();
        boolean[][] newMatrix = new boolean[size][size];
        for (int i = 0; i < Math.min(adjMatrix.length, size); i++) {
            System.arraycopy(adjMatrix[i], 0, newMatrix[i], 0, Math.min(adjMatrix.length, size));
        }
        adjMatrix = newMatrix;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeVertex(int v) {
        int index = vertices.indexOf(v);
        if (index == -1) {
            return;
        }
        vertices.remove(index);

        int size = adjMatrix.length;
        int skipI = 0;
        int skipJ = 0;
        boolean[][] newMatrix = new boolean[size - 1][size - 1];
        for (int i = 0; i < size; i++) {
            if (i == index) {
                skipI = 1;
                continue;
            }
            for (int j = 0; j < size; j++) {
                if (j == index) {
                    skipJ = 1;
                    continue;
                }
                newMatrix[i - skipI][j - skipJ] = adjMatrix[i][j];
            }
            skipJ = 0;
        }
        adjMatrix = newMatrix;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addEdge(int v1, int v2) {
        int i = vertices.indexOf(v1);
        int j = vertices.indexOf(v2);
        if (i == -1 || j == -1) {
            return;
        }

        adjMatrix[i][j] = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeEdge(int v1, int v2) {
        int i = vertices.indexOf(v1);
        int j = vertices.indexOf(v2);
        if (i == -1 || j == -1) {
            return;
        }

        adjMatrix[i][j] = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Integer> getNeighbors(int v) {
        int index = vertices.indexOf(v);
        if (index == -1) {
            return null;
        }

        List<Integer> neighbors = new ArrayList<>();
        for (int j = 0; j < vertices.size(); j++) {
            if (adjMatrix[index][j]) {
                neighbors.add(vertices.get(j));
            }
        }
        return neighbors;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadFromFile(String filename) throws GraphLoadException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            vertices.clear();
            adjMatrix = new boolean[0][0];
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

        AdjacencyMatrixGraph other = (AdjacencyMatrixGraph) o;
        return vertices.equals(other.vertices) && Arrays.deepEquals(adjMatrix, other.adjMatrix);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < adjMatrix.length; i++) {
            for (int j = 0; j < adjMatrix.length; j++) {
                sb.append(adjMatrix[i][j] ? "1 " : "0 ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Integer> topologicalSort() throws GraphSortException {
        int n = vertices.size();
        int[] penetrations = new int[n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (adjMatrix[i][j]) {
                    penetrations[j]++;
                }
            }
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (penetrations[i] == 0) {
                queue.add(i);
            }
        }

        List<Integer> sorted = new ArrayList<>();
        while (!queue.isEmpty()) {
            int u = queue.poll();
            sorted.add(vertices.get(u));
            for (int v = 0; v < n; v++) {
                if (adjMatrix[u][v]) {
                    penetrations[v]--;
                    if (penetrations[v] == 0) {
                        queue.add(v);
                    }
                }
            }
        }

        if (sorted.size() != n) {
            throw new GraphSortException("Graph has cycles");
        }
        return sorted;
    }
}