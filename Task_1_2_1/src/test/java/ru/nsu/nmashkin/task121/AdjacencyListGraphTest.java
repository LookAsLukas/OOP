package ru.nsu.nmashkin.task121;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class AdjacencyListGraphTest {

    @Test
    void addVertex() {
        Graph g = new AdjacencyListGraph();
        g.addVertex(0);
        g.addVertex(1);
        g.addVertex(1);
        assertEquals("0: []\n1: []\n", g.toString());
    }

    @Test
    void addEdge() {
        Graph g = new AdjacencyListGraph();
        g.addVertex(0);
        g.addVertex(1);
        g.addEdge(0, 1);
        g.addEdge(1, 1);
        g.addEdge(2, 3);
        g.addEdge(0, 1);
        assertEquals("0: [1]\n1: [1]\n", g.toString());
    }
}