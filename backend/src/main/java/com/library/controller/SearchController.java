package com.library.controller;

import com.library.dto.DocumentDTO;
import com.library.dto.SearchResponse;
import com.library.dto.SuggestionResponse;
import com.library.service.SearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Contrôleur REST pour les endpoints de recherche.
 * 
 * Endpoints:
 * - GET /api/search?keyword=...&sortBy=...&page=...&size=...
 * - GET /api/advanced-search?regex=...&searchInIndex=...&sortBy=...
 * - GET /api/suggestions?query=...
 * - GET /api/documents/{id}
 */
@RestController
@RequestMapping("/api")
public class SearchController {
    
    private final SearchService searchService;
    
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }
    
    /**
     * Recherche par mot-clé (fonctionnalité explicite).
     */
    @GetMapping("/search")
    public ResponseEntity<SearchResponse> search(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "occurrenceCount") String sortBy,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        SearchResponse response = searchService.search(keyword, sortBy, page, size);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Recherche avancée par RegEx (fonctionnalité explicite).
     */
    @GetMapping("/advanced-search")
    public ResponseEntity<SearchResponse> advancedSearch(
            @RequestParam String regex,
            @RequestParam(defaultValue = "true") boolean searchInIndex,
            @RequestParam(defaultValue = "occurrenceCount") String sortBy,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        SearchResponse response = searchService.advancedSearch(regex, searchInIndex, sortBy, page, size);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Suggestions basées sur la dernière recherche (fonctionnalité implicite).
     */
    @GetMapping("/suggestions")
    public ResponseEntity<SuggestionResponse> getSuggestions(
            @RequestParam(required = false) String query) {
        
        SuggestionResponse response = searchService.getSuggestions(query);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Obtenir un document par son ID.
     */
    @GetMapping("/documents/{id}")
    public ResponseEntity<DocumentDTO> getDocument(@PathVariable String id) {
        DocumentDTO document = searchService.getDocumentById(id);
        if (document == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(document);
    }
}
