package ru.nsu.nmashkin.task121;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Implementation of a Graph via incidence matrix.
 */
public class IncidenceMatrixGraph implements Graph {
    private class Edge {
        Object v1;
        Object v2;

        private Edge(Object v1, Object v2) {
            this.v1 = v1;
            this.v2 = v2;
        }

        private Object start() {
            return v1;
        }

        private Object end() {
            return v2;
        }

        /**
         * Check if edges are equal.
         *
         * @param o the reference object with which to compare.
         * @return true if equal
         */
        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Edge edge = (Edge) o;
            return v1.equals(edge.v1) && v2.equals(edge.v2);
        }

        /**
         * Get the hash code of the Edge.
         *
         * @return the hash code
         */
        @Override
        public int hashCode() {
            return Objects.hash(v1, v2);
        }

        /**
         * Get the string representation of the Edge.
         *
         * @return the string representation
         */
        @Override
        public String toString() {
            return "(" + v1 + ", " + v2 + ")";
        }
    }

    private final List<Object> vertices;
    private List<Edge> edges;
    private int[][] incidenceMatrix;

    /**
     * Initialize the Graph.
     */
    public IncidenceMatrixGraph() {
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
        incidenceMatrix = new int[0][0];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addVertex(Object v) {
        if (!vertices.contains(v)) {
            vertices.add(v);
            resizeIncidenceMatrix();
        }
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
        List<Edge> newEdges = new ArrayList<>();
        for (Edge e : edges) {
            if (e.start().equals(v) && e.end().equals(v)) {
                newEdges.add(e);
            }
        }
        edges = newEdges;
        resizeIncidenceMatrix();
    }

    private void resizeIncidenceMatrix() {
        int vertexCount = vertices.size();
        int edgeCount = edges.size();
        incidenceMatrix = new int[vertexCount][edgeCount];
        for (int i = 0; i < edgeCount; i++) {
            Edge e = edges.get(i);
            int v1Index = vertices.indexOf(e.start());
            int v2Index = vertices.indexOf(e.end());
            incidenceMatrix[v1Index][i] = 1;
            incidenceMatrix[v2Index][i] = -1;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addEdge(Object v1, Object v2) {
        if (!vertices.contains(v1) || !vertices.contains(v2)) {
            return;
        }
        if (edges.contains(new Edge(v1, v2))) {
            return;
        }

        edges.add(new Edge(v1, v2));
        resizeIncidenceMatrix();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeEdge(Object v1, Object v2) {
        for (int i = 0; i < edges.size(); i++) {
            if (edges.get(i).start().equals(v1) && edges.get(i).end().equals(v2)) {
                edges.remove(edges.get(i));
                break;
            }
        }
        resizeIncidenceMatrix();
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
        int vertexIndex = vertices.indexOf(v);
        if (vertexIndex == -1) {
            return null;
        }

        List<Object> neighbors = new ArrayList<>();
        for (int i = 0; i < edges.size(); i++) {
            if (incidenceMatrix[vertexIndex][i] == 1) {
                Edge e = edges.get(i);
                neighbors.add(e.end());
            }
        }
        return neighbors;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            vertices.clear();
            edges.clear();
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

        IncidenceMatrixGraph other = (IncidenceMatrixGraph) o;
        return vertices.equals(other.vertices) && edges.equals(other.edges);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Vertices: ").append(vertices).append("\n");
        sb.append("Edges:\n");
        for (Edge e : edges) {
            sb.append(e).append("\n");
        }
        return sb.toString();
    }
}
