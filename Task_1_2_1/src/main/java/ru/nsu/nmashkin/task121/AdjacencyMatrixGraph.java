package ru.nsu.nmashkin.task121;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Implementation of a Graph via adjacency matrix.
 */
public class AdjacencyMatrixGraph implements Graph {
    private boolean[][] adjMatrix;
    private final List<Object> vertices;

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
    public void addVertex(Object v) {
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
    public void removeVertex(Object v) {
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
    public void addEdge(Object v1, Object v2) {
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
    public void removeEdge(Object v1, Object v2) {
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
    public List<Object> getVertices() {
        return vertices;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public List<Object> getNeighbors(Object v) {
        int index = vertices.indexOf(v);
        if (index == -1) {
            return null;
        }

        List<Object> neighbors = new ArrayList<>();
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
        if (o == null || o.getClass() != this.getClass()) {
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
}