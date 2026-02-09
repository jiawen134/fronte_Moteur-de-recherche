package com.library.dto;

/**
 * DTO pour un document dans les résultats de recherche.
 * Version légère du Document pour l'envoi au frontend.
 */
public class DocumentDTO {
    private String id;
    private String title;
    private String author;
    private Integer occurrenceCount;
    private Double centralityScore;
    private String snippet;
    private Integer wordCount;
    private String category;
    private String content;  // 完整内容，用于阅读

    
    public DocumentDTO() {}
    
    public DocumentDTO(String id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    
    public Integer getOccurrenceCount() { return occurrenceCount; }
    public void setOccurrenceCount(Integer occurrenceCount) { this.occurrenceCount = occurrenceCount; }
    
    public Double getCentralityScore() { return centralityScore; }
    public void setCentralityScore(Double centralityScore) { this.centralityScore = centralityScore; }
    
    public String getSnippet() { return snippet; }
    public void setSnippet(String snippet) { this.snippet = snippet; }
    
    public Integer getWordCount() { return wordCount; }
    public void setWordCount(Integer wordCount) { this.wordCount = wordCount; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
