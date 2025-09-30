package ru.nsu.nmashkin.task121;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class IncidenceMatrixGraphTest {

    @Test
    void addVertex() {
        Graph g = new IncidenceMatrixGraph();
        g.addVertex(0);
        g.addVertex(1);
        g.addVertex(1);
        assertEquals("Vertices: [0, 1]\nEdges:\n", g.toString());
    }

    @Test
    void addEdge() {
        Graph g = new IncidenceMatrixGraph();
        g.addVertex(0);
        g.addVertex(1);
        g.addEdge(0, 1);
        g.addEdge(1, 1);
        assertThrows(GraphException.class, () -> g.addEdge(2, 3));
        g.addEdge(0, 1);
        assertEquals("Vertices: [0, 1]\nEdges:\n(0, 1)\n(1, 1)\n", g.toString());
    }
}