package com.searchengine.cli;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import java.util.*;

public class InvertedIndex {
    private Map<String, Set<String>> index;

    public InvertedIndex() {
        index = new HashMap<>();
    }

    public void addDocument(String url, List<String> tokens) {
        for (String token : tokens) {
            index.computeIfAbsent(token, k -> new HashSet<>()).add(url);
        }
    }

    public Set<String> search(String term) {
        return index.getOrDefault(term.toLowerCase(), new HashSet<>());
    }

    // Method to rank results based on term frequency
    public Map<String, Integer> rankResults(Set<String> urls, List<String> tokens) {
        Map<String, Integer> rankMap = new HashMap<>();
        
        for (String url : urls) {
            int frequency = 0;
            for (String token : tokens) {
                // Count occurrences of the token in the URL (simplified)
                frequency += Collections.frequency(Arrays.asList(tokens.toArray()), token);
            }
            rankMap.put(url, frequency);
        }

        // Sort the results by frequency
        return sortByValue(rankMap);
    }

    // Helper method to sort the map by value
    private Map<String, Integer> sortByValue(Map<String, Integer> unsortedMap) {
        List<Map.Entry<String, Integer>> list = new LinkedList<>(unsortedMap.entrySet());
        list.sort(Map.Entry.comparingByValue(Collections.reverseOrder()));

        Map<String, Integer> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }
}
