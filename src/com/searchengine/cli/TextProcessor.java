package com.searchengine.cli;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class TextProcessor {

    // Method to tokenize input text
    public List<String> tokenize(String text) {
        // Normalize the text: convert to lowercase and remove punctuation
        text = text.toLowerCase().replaceAll("[^a-zA-Z0-9\\s]", "");

        // Split the text into words based on whitespace
        String[] tokens = text.split("\\s+");

        // Create a list to store tokens
        List<String> tokenList = new ArrayList<>();
        for (String token : tokens) {
            if (!token.isEmpty()) {
                tokenList.add(token);
            }
        }
        return tokenList; // Return the list of tokens
    }
}
