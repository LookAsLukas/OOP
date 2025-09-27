package ru.nsu.nmashkin.task121;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
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
    void removeVertex() {
        Graph g = new IncidenceMatrixGraph();
        g.addVertex(0);
        g.addVertex(1);
        g.removeVertex(1);
        g.removeVertex(2);
        Graph e = new IncidenceMatrixGraph();
        e.addVertex(0);
        assertEquals(e, g);
    }

    @Test
    void addEdge() {
        Graph g = new IncidenceMatrixGraph();
        g.addVertex(0);
        g.addVertex(1);
        g.addEdge(0, 1);
        g.addEdge(1, 1);
        g.addEdge(2, 3);
        g.addEdge(0, 1);
        assertEquals("Vertices: [0, 1]\nEdges:\n(0, 1)\n(1, 1)\n", g.toString());
    }

    @Test
    void removeEdge() {
        Graph g = new IncidenceMatrixGraph();
        g.addVertex(0);
        g.addVertex(1);
        g.addEdge(0, 1);
        g.addEdge(1, 1);
        g.removeEdge(0, 1);
        g.removeEdge(0, 0);
        g.removeEdge(4, 0);
        Graph e = new IncidenceMatrixGraph();
        e.addVertex(0);
        e.addVertex(1);
        e.addEdge(1, 1);
        assertEquals(e, g);
    }

    @Test
    void getNeighbors() {
        Graph g = new IncidenceMatrixGraph();
        g.addVertex(0);
        g.addVertex(1);
        g.addVertex(2);
        g.addEdge(0, 2);
        g.addEdge(1, 0);
        g.addEdge(1, 2);
        ArrayList<Integer> e = new ArrayList<>();
        e.add(2);
        assertEquals(e, g.getNeighbors(0));
        e = new ArrayList<>();
        e.add(0);
        e.add(2);
        assertEquals(e, g.getNeighbors(1));
        e = new ArrayList<>();
        assertEquals(e, g.getNeighbors(2));
        assertNull(g.getNeighbors(69));
    }

    @Test
    void loadFromFile() {
        Graph g = new IncidenceMatrixGraph();
        g.loadFromFile("correct_graph.txt");
        Graph e = new IncidenceMatrixGraph();
        e.addVertex(0);
        e.addVertex(1);
        e.addVertex(2);
        e.addEdge(0, 1);
        e.addEdge(0, 2);
        e.addEdge(1, 1);
        assertEquals(e, g);
    }

    @Test
    void loadFromFile_jibberish() {
        Graph g = new IncidenceMatrixGraph();
        assertThrows(GraphLoadException.class, () -> g.loadFromFile("jibberish_graph.txt"));
    }

    @Test
    void loadFromFile_non_existent() {
        Graph g = new IncidenceMatrixGraph();
        assertThrows(GraphLoadException.class, () -> g.loadFromFile("lolkek.txt"));
    }

    @Test
    void topologicalSort() {
        Graph g = new IncidenceMatrixGraph();
        g.addVertex(0);
        g.addVertex(1);
        g.addVertex(2);
        g.addEdge(0, 2);
        g.addEdge(1, 0);
        g.addEdge(1, 2);
        ArrayList<Integer> e = new ArrayList<>();
        e.add(1);
        e.add(0);
        e.add(2);
        assertEquals(e, g.topologicalSort());
    }

    @Test
    void topologicalSort_cycle() {
        Graph g = new IncidenceMatrixGraph();
        g.addVertex(0);
        g.addVertex(1);
        g.addVertex(2);
        g.addEdge(0, 2);
        g.addEdge(1, 0);
        g.addEdge(2, 1);
        assertThrows(GraphSortException.class, g::topologicalSort);
    }
}