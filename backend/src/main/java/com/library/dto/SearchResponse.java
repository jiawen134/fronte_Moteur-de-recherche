package com.library.dto;

import java.util.List;

/**
 * DTO pour la réponse de recherche.
 * Correspond à l'interface SearchResponse côté frontend.
 */
public class SearchResponse {
    private String query;
    private List<DocumentDTO> documents;
    private int totalCount;
    private String sortBy;
    
    public SearchResponse() {}
    
    public SearchResponse(String query, List<DocumentDTO> documents, int totalCount, String sortBy) {
        this.query = query;
        this.documents = documents;
        this.totalCount = totalCount;
        this.sortBy = sortBy;
    }
    
    public String getQuery() { return query; }
    public void setQuery(String query) { this.query = query; }
    
    public List<DocumentDTO> getDocuments() { return documents; }
    public void setDocuments(List<DocumentDTO> documents) { this.documents = documents; }
    
    public int getTotalCount() { return totalCount; }
    public void setTotalCount(int totalCount) { this.totalCount = totalCount; }
    
    public String getSortBy() { return sortBy; }
    public void setSortBy(String sortBy) { this.sortBy = sortBy; }
}
