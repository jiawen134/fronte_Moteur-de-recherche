package com.library.service;

import com.library.dto.DocumentDTO;
import com.library.dto.SearchResponse;
import com.library.dto.SuggestionResponse;

/**
 * Interface du service de recherche.
 */
public interface SearchService {
    
    /**
     * Recherche par mot-clé dans l'index.
     */
    SearchResponse search(String keyword, String sortBy, int page, int size);
    
    /**
     * Recherche avancée par expression régulière.
     */
    SearchResponse advancedSearch(String regex, boolean searchInIndex, String sortBy, int page, int size);
    
    /**
     * Obtenir les suggestions basées sur la dernière requête.
     */
    SuggestionResponse getSuggestions(String lastQuery);
    
    /**
     * Obtenir un document par son ID.
     */
    DocumentDTO getDocumentById(String id);
}
