package com.library.dto;

import java.util.List;

/**
 * DTO pour les suggestions de documents similaires.
 * Correspond à l'interface SuggestionResponse côté frontend.
 */
public class SuggestionResponse {
    private List<DocumentDTO> suggestions;
    private String strategy;
    
    public SuggestionResponse() {}
    
    public SuggestionResponse(List<DocumentDTO> suggestions, String strategy) {
        this.suggestions = suggestions;
        this.strategy = strategy;
    }
    
    public List<DocumentDTO> getSuggestions() { return suggestions; }
    public void setSuggestions(List<DocumentDTO> suggestions) { this.suggestions = suggestions; }
    
    public String getStrategy() { return strategy; }
    public void setStrategy(String strategy) { this.strategy = strategy; }
}
