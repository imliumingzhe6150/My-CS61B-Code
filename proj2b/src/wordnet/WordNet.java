package wordnet;

import edu.princeton.cs.algs4.In;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class WordNet {
    private Map<Integer, List<String>> idToWords;
    private Map<String, List<Integer>> wordToIds;
    private Graph graph;

    public WordNet(String synsetFile, String hyponymFile) {
        idToWords = new HashMap<>();
        wordToIds = new HashMap<>();
        graph = new Graph();
        In in =  new In(synsetFile);
        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] parts = line.split(",");
            int id = Integer.parseInt(parts[0]);
            String[] words = parts[1].split(" ");
            graph.addNode(id);
            for (String word : words) {
                idToWords.computeIfAbsent(id, k -> new ArrayList<>()).add(word);
                wordToIds.computeIfAbsent(word, k -> new ArrayList<>()).add(id);
            }
        }

        In hyponymIn = new In(hyponymFile);
        while (hyponymIn.hasNextLine()) {
            String line = hyponymIn.readLine();
            String[] parts = line.split(",");
            int source  = Integer.parseInt(parts[0]);
            for (int i = 1; i < parts.length; i++) {
                int target = Integer.parseInt(parts[i]);
                graph.addEdge(source, target);
            }
        }
    }

    public Set<String> hyponyms(String word) {
        Set<String> result = new TreeSet<>();
        List<Integer> ids = wordToIds.get(word);
        Set<Integer> allIds = new HashSet<>();
        if (ids == null) {
            return result;
        }
        for (int id : ids) {
            allIds.addAll(reachable(id));
        }
        for (int id : allIds) {
            result.addAll(idToWords.get(id));
        }
        return result;
    }

    /** 返回 WORDS 里所有词的下位词的交集（含各词自身）。 */
    public Set<String> hyponyms(List<String> words) {
        if (words.isEmpty()) {
            return new TreeSet<>();
        }
        Set<String> result = new TreeSet<>(hyponyms(words.get(0)));
        for (int i = 1; i < words.size(); i++) {
            result.retainAll(hyponyms(words.get(i)));
        }
        return result;
    }

    private Set<Integer> reachable(int id) {
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new ArrayDeque<>();

        queue.add(id);
        visited.add(id);
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            for (int neibour : graph.neighbours(cur)) {
                if (!visited.contains(neibour)) {
                    visited.add(neibour);
                    queue.add(neibour);
                }
            }
        }
        return visited;
    }
}
