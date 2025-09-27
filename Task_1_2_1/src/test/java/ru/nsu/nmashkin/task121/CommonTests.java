package ru.nsu.nmashkin.task121;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class CommonTests {
    static Stream<Graph> graphTypes() {
        return Stream.of(new AdjacencyMatrixGraph(),
                         new IncidenceMatrixGraph(),
                         new AdjacencyListGraph());
    }

    /
    static Graph newClone(Graph g) {
        if (g instanceof AdjacencyMatrixGraph) {
            return new AdjacencyMatrixGraph();
        }
        if (g instanceof IncidenceMatrixGraph) {
            return new IncidenceMatrixGraph();
        }
        return new AdjacencyListGraph();
    }

    @ParameterizedTest
    @MethodSource("graphTypes")
    void removeVertex(Graph g) {
        g.addVertex(0);
        g.addVertex(1);
        g.removeVertex(1);
        g.removeVertex(2);
        Graph e = newClone(g);
        e.addVertex(0);
        assertEquals(e, g);
    }

    @ParameterizedTest
    @MethodSource("graphTypes")
    void removeEdge(Graph g) {
        g.addVertex(0);
        g.addVertex(1);
        g.addEdge(0, 1);
        g.addEdge(1, 1);
        g.removeEdge(0, 1);
        g.removeEdge(0, 0);
        g.removeEdge(4, 0);
        Graph e = newClone(g);
        e.addVertex(0);
        e.addVertex(1);
        e.addEdge(1, 1);
        assertEquals(e, g);
    }

    @ParameterizedTest
    @MethodSource("graphTypes")
    void getNeighbors(Graph g) {
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

    @ParameterizedTest
    @MethodSource("graphTypes")
    void loadFromFile(Graph g) {
        g.loadFromFile("correct_graph.txt");
        Graph e = newClone(g);
        e.addVertex("0");
        e.addVertex("1");
        e.addVertex("2");
        e.addEdge("0", "1");
        e.addEdge("0", "2");
        e.addEdge("1", "1");
        assertEquals(e, g);
    }

    @ParameterizedTest
    @MethodSource("graphTypes")
    void loadFromFile_jibberish(Graph g) {
        g.loadFromFile("jibberish_graph.txt");
        assertEquals(newClone(g), g);
    }

    @ParameterizedTest
    @MethodSource("graphTypes")
    void loadFromFile_non_existent(Graph g) {
        assertThrows(GraphLoadException.class, () -> g.loadFromFile("lolkek.txt"));
    }

    @ParameterizedTest
    @MethodSource("graphTypes")
    void sort(Graph g) {
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
        assertEquals(e, TopologicalSort.sort(g));
    }

    @ParameterizedTest
    @MethodSource("graphTypes")
    void sort_cycle(Graph g) {
        g.addVertex(0);
        g.addVertex(1);
        g.addVertex(2);
        g.addEdge(0, 2);
        g.addEdge(1, 0);
        g.addEdge(2, 1);
        assertThrows(GraphSortException.class, () -> TopologicalSort.sort(g));
    }
}
