package ru.nsu.nmashkin.task121;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Topological sorter.
 */
public class TopologicalSort {
    /**
     * Implementation of a topological sort.
     *
     * @param g graph to be sorted
     * @return order
     * @throws GraphSortException if the graph has cycles
     */
    public static List<Object> sort(Graph g) {
        Map<Object, Integer> penetrations = new HashMap<>();
        List<Object> vertices = g.getVertices();

        for (Object v : vertices) {
            penetrations.putIfAbsent(v, 0);

            List<Object> neighbors = g.getNeighbors(v);
            for (Object n : neighbors) {
                penetrations.put(n, penetrations.getOrDefault(n, 0) + 1);
            }
        }

        Queue<Object> queue = new LinkedList<>();
        for (Object v : penetrations.keySet()) {
            if (penetrations.get(v) == 0) {
                queue.add(v);
            }
        }

        List<Object> result = new ArrayList<>();
        while (!queue.isEmpty()) {
            Object curr = queue.poll();
            result.add(curr);

            List<Object> neighbors = g.getNeighbors(curr);
            for (Object n : neighbors) {
                penetrations.put(n, penetrations.get(n) - 1);
                if (penetrations.get(n) == 0) {
                    queue.add(n);
                }
            }
        }

        for (Integer p : penetrations.values()) {
            if (p != 0) {
                throw new GraphSortException("Graph has cycles");
            }
        }
        return result;
    }
}