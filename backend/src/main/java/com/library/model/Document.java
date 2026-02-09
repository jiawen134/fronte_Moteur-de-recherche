package com.library.model;

import java.util.List;
import java.util.Map;

/**
 * Document (livre) dans la bibliothèque.
 * Correspond à l'interface DocumentResult côté frontend.
 */
public class Document {
    private String id;
    private String title;
    private String author;
    private String content;
    private int wordCount;
    private String category;
    private List<String> keywords;
    
    // Métriques de recherche
    private int occurrenceCount;
    private double centralityScore;
    private String snippet;
    
    // Index inversé pour ce document
    private Map<String, Integer> wordIndex;
    
    public Document() {}
    
    public Document(String id, String title, String author, String content) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.content = content;
        this.wordCount = content != null ? content.split("\\s+").length : 0;
    }
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    
    public int getWordCount() { return wordCount; }
    public void setWordCount(int wordCount) { this.wordCount = wordCount; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public List<String> getKeywords() { return keywords; }
    public void setKeywords(List<String> keywords) { this.keywords = keywords; }
    
    public int getOccurrenceCount() { return occurrenceCount; }
    public void setOccurrenceCount(int occurrenceCount) { this.occurrenceCount = occurrenceCount; }
    
    public double getCentralityScore() { return centralityScore; }
    public void setCentralityScore(double centralityScore) { this.centralityScore = centralityScore; }
    
    public String getSnippet() { return snippet; }
    public void setSnippet(String snippet) { this.snippet = snippet; }
    
    public Map<String, Integer> getWordIndex() { return wordIndex; }
    public void setWordIndex(Map<String, Integer> wordIndex) { this.wordIndex = wordIndex; }
}
