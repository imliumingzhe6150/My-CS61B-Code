package wordnet;

import java.util.*;

public class Graph {

    private Map<Integer, Set<Integer>> graph;

    public Graph() {
        graph = new HashMap<>();
    }

    public void addNode(int id) {
        graph.computeIfAbsent(id, k -> new HashSet<>());
    }

    public void addEdge(int from, int to) {
        graph.get(from).add(to);
    }

    public List<Integer> neighbours (int id) {
        List<Integer> result = new ArrayList<>();
        if (graph.containsKey(id)) {
            for (Integer neighbour : graph.get(id)) {
                result.add(neighbour);
            }
        }
        return result;
    }

    public Set<Integer> getNodes() {
        return new HashSet<>(graph.keySet());
    }
}
