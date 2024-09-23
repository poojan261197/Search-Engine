package com.searchengine.cli;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.util.List;

public class WebCrawler {
    private InvertedIndex invertedIndex;

    public WebCrawler(InvertedIndex invertedIndex) {
        this.invertedIndex = invertedIndex;
    }

    public String fetchContent(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            String content = doc.body().text();

            // Tokenize the content
            TextProcessor textProcessor = new TextProcessor();
            List<String> tokens = textProcessor.tokenize(content);

            // Add the document to the inverted index
            invertedIndex.addDocument(url, tokens);

            return content;
        } catch (IOException e) {
            System.out.println("Error fetching content from " + url + ": " + e.getMessage());
            return null;
        }
    }
}
