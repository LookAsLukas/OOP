package ru.nsu.nmashkin.task121;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

/**
 * Implementation of a Graph via incidence matrix.
 */
public class IncidenceMatrixGraph implements Graph {
    private class Edge {
        int v1, v2;

        private Edge(int v1, int v2) {
            this.v1 = v1;
            this.v2 = v2;
        }

        private int start() {
            return v1;
        }

        private int end() {
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
            return v1 == edge.v1 && v2 == edge.v2;
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

    private final List<Integer> vertices;
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
    public void addVertex(int v) {
        if (!vertices.contains(v)) {
            vertices.add(v);
            resizeIncidenceMatrix();
        }
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
        List<Edge> newEdges = new ArrayList<>();
        for (Edge e : edges) {
            if (e.start() != v && e.end() != v) {
                newEdges.add(e);
            }
        }
        edges = newEdges;
        resizeIncidenceMatrix();
    }

    private void resizeIncidenceMatrix() {
        int vCount = vertices.size();
        int eCount = edges.size();
        incidenceMatrix = new int[vCount][eCount];
        for (int i = 0; i < eCount; i++) {
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
    public void addEdge(int v1, int v2) {
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
    public void removeEdge(int v1, int v2) {
        for (int i = 0; i < edges.size(); i++) {
            if (edges.get(i).start() == v1 && edges.get(i).end() == v2) {
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
    public List<Integer> getNeighbors(int v) {
        int vIndex = vertices.indexOf(v);
        if (vIndex == -1) {
            return null;
        }

        List<Integer> neighbors = new ArrayList<>();
        for (int i = 0; i < edges.size(); i++) {
            if (incidenceMatrix[vIndex][i] == 1) {
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

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Integer> topologicalSort() throws GraphSortException {
        int n = vertices.size();
        int[] penetrations = new int[n];

        for (int i = 0; i < edges.size(); i++) {
            for (int vIdx = 0; vIdx < n; vIdx++) {
                if (incidenceMatrix[vIdx][i] == -1) {
                    penetrations[vIdx]++;
                }
            }
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (penetrations[i] == 0) queue.add(i);
        }

        List<Integer> sorted = new ArrayList<>();
        while (!queue.isEmpty()) {
            int u = queue.poll();
            sorted.add(vertices.get(u));
            for (int i = 0; i < edges.size(); i++) {
                if (incidenceMatrix[u][i] == 1) {
                    int vIdx = -1;
                    for (int j = 0; j < n; j++) {
                        if (incidenceMatrix[j][i] == -1) {
                            vIdx = j;
                            break;
                        }
                    }
                    if (vIdx != -1) {
                        penetrations[vIdx]--;
                        if (penetrations[vIdx] == 0) {
                            queue.add(vIdx);
                        }
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
