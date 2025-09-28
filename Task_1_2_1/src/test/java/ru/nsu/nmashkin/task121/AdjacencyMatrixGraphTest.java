package ru.nsu.nmashkin.task121;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class AdjacencyMatrixGraphTest {

    @Test
    void addVertex() {
        Graph g = new AdjacencyMatrixGraph();
        g.addVertex(0);
        g.addVertex(1);
        g.addVertex(1);
        assertEquals("0 0 \n0 0 \n", g.toString());
    }

    @Test
    void addEdge() {
        Graph g = new AdjacencyMatrixGraph();
        g.addVertex(0);
        g.addVertex(1);
        g.addEdge(0, 1);
        g.addEdge(1, 1);
        assertThrows(GraphException.class, () -> g.addEdge(2, 3));
        g.addEdge(0, 1);
        assertEquals("0 1 \n0 1 \n", g.toString());
    }
}