package com.searchengine.cli;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class SearchEngineCLI {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TextProcessor textProcessor = new TextProcessor();
        InvertedIndex invertedIndex = new InvertedIndex();
        WebCrawler webCrawler = new WebCrawler(invertedIndex);

        System.out.println("Welcome to the CLI Search Engine!");

        while (true) {
            System.out.print("> ");
            String command = scanner.nextLine().trim();

            if (command.equalsIgnoreCase("exit")) {
                System.out.println("Exiting... Goodbye!");
                break;
            } else if (command.startsWith("search")) {
                String query = command.substring(7).trim();
                if (!query.isEmpty()) {
                    List<String> tokens = textProcessor.tokenize(query);
                    Set<String> results = new HashSet<>();
                    for (String token : tokens) {
                        results.addAll(invertedIndex.search(token));
                    }

                    // Get ranked results
                    Map<String, Integer> rankedResults = invertedIndex.rankResults(results, tokens);
                    System.out.println("Ranked Results:");
                    for (Map.Entry<String, Integer> entry : rankedResults.entrySet()) {
                        System.out.println(entry.getKey() + " (Frequency: " + entry.getValue() + ")");
                    }
                } else {
                    System.out.println("Please provide a search query.");
                }
            } else if (command.startsWith("crawl")) {
                String url = command.substring(6).trim();
                if (!url.isEmpty()) {
                    System.out.println("Crawling: " + url);
                    String content = webCrawler.fetchContent(url);
                    if (content != null) {
                        System.out.println("Content fetched from " + url + ":");
                        System.out.println(content);
                    }
                } else {
                    System.out.println("Please provide a URL to crawl.");
                }
            } else {
                System.out.println("Unknown command. Available commands are: search, crawl, exit.");
            }
        }

        scanner.close();
    }
}
