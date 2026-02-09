package com.library.service.impl;

import com.library.dto.DocumentDTO;
import com.library.dto.SearchResponse;
import com.library.dto.SuggestionResponse;
import com.library.model.Document;
import com.library.repository.DocumentRepository;
import com.library.service.SearchService;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Implémentation du service de recherche.
 * Contient les algorithmes de Jaccard et PageRank.
 */
@Service
public class SearchServiceImpl implements SearchService {
    
    private final DocumentRepository documentRepository;
    
    // Graphe de Jaccard: document ID -> (neighbor ID -> similarity score)
    private Map<String, Map<String, Double>> jaccardGraph = new HashMap<>();
    
    // Scores PageRank pour chaque document
    private Map<String, Double> pageRankScores = new HashMap<>();
    
    // Dernier mot-clé recherché (pour les suggestions)
    private String lastSearchQuery = "";
    
    public SearchServiceImpl(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }
    
    @PostConstruct
    public void init() {
        buildJaccardGraph();
        computePageRank();
    }
    
    /**
     * Construction du graphe de Jaccard basé sur la similarité des mots-clés entre documents.
     */
    private void buildJaccardGraph() {
        List<Document> allDocs = documentRepository.findAll();
        
        for (int i = 0; i < allDocs.size(); i++) {
            Document doc1 = allDocs.get(i);
            Set<String> keywords1 = new HashSet<>(doc1.getKeywords() != null ? doc1.getKeywords() : Collections.emptyList());
            
            Map<String, Double> neighbors = new HashMap<>();
            
            for (int j = 0; j < allDocs.size(); j++) {
                if (i == j) continue;
                
                Document doc2 = allDocs.get(j);
                Set<String> keywords2 = new HashSet<>(doc2.getKeywords() != null ? doc2.getKeywords() : Collections.emptyList());
                
                double similarity = calculateJaccardSimilarity(keywords1, keywords2);
                
                // Seuil de similarité pour créer une arête
                if (similarity > 0.1) {
                    neighbors.put(doc2.getId(), similarity);
                }
            }
            
            jaccardGraph.put(doc1.getId(), neighbors);
        }
    }
    
    /**
     * Calcul de la similarité de Jaccard entre deux ensembles.
     * Jaccard(A, B) = |A ∩ B| / |A ∪ B|
     */
    private double calculateJaccardSimilarity(Set<String> set1, Set<String> set2) {
        if (set1.isEmpty() && set2.isEmpty()) return 0.0;
        
        Set<String> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);
        
        Set<String> union = new HashSet<>(set1);
        union.addAll(set2);
        
        return (double) intersection.size() / union.size();
    }
    
    /**
     * Calcul du PageRank pour tous les documents.
     * Algorithme itératif avec facteur d'amortissement.
     */
    private void computePageRank() {
        double dampingFactor = 0.85;
        int maxIterations = 100;
        double convergenceThreshold = 0.0001;
        
        List<Document> allDocs = documentRepository.findAll();
        int n = allDocs.size();
        
        // Initialisation
        for (Document doc : allDocs) {
            pageRankScores.put(doc.getId(), 1.0 / n);
        }
        
        // Itérations
        for (int iter = 0; iter < maxIterations; iter++) {
            Map<String, Double> newScores = new HashMap<>();
            double maxDiff = 0.0;
            
            for (Document doc : allDocs) {
                String docId = doc.getId();
                double sum = 0.0;
                
                // Somme des contributions des voisins entrants
                for (Document otherDoc : allDocs) {
                    String otherId = otherDoc.getId();
                    if (otherId.equals(docId)) continue;
                    
                    Map<String, Double> neighbors = jaccardGraph.get(otherId);
                    if (neighbors != null && neighbors.containsKey(docId)) {
                        int outDegree = neighbors.size();
                        if (outDegree > 0) {
                            sum += pageRankScores.get(otherId) / outDegree;
                        }
                    }
                }
                
                double newScore = (1 - dampingFactor) / n + dampingFactor * sum;
                newScores.put(docId, newScore);
                
                maxDiff = Math.max(maxDiff, Math.abs(newScore - pageRankScores.get(docId)));
            }
            
            pageRankScores = newScores;
            
            if (maxDiff < convergenceThreshold) {
                break;
            }
        }
        
        // Normalisation
        double total = pageRankScores.values().stream().mapToDouble(Double::doubleValue).sum();
        if (total > 0) {
            for (String id : pageRankScores.keySet()) {
                pageRankScores.put(id, pageRankScores.get(id) / total);
            }
        }
    }
    
    @Override
    public SearchResponse search(String keyword, String sortBy, int page, int size) {
        this.lastSearchQuery = keyword;
        
        List<Document> results = documentRepository.searchByKeyword(keyword);
        
        // Calculer les occurrences et snippets
        for (Document doc : results) {
            doc.setOccurrenceCount(documentRepository.countOccurrences(doc, keyword));
            doc.setSnippet(documentRepository.extractSnippet(doc, keyword, 50));
            doc.setCentralityScore(pageRankScores.getOrDefault(doc.getId(), 0.0));
        }
        
        // Tri
        if ("centralityScore".equals(sortBy)) {
            results.sort((a, b) -> Double.compare(b.getCentralityScore(), a.getCentralityScore()));
        } else {
            results.sort((a, b) -> Integer.compare(b.getOccurrenceCount(), a.getOccurrenceCount()));
        }
        
        // Pagination
        int start = page * size;
        int end = Math.min(start + size, results.size());
        List<Document> pageResults = (start < results.size()) ? results.subList(start, end) : Collections.emptyList();
        
        List<DocumentDTO> dtos = pageResults.stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
        
        return new SearchResponse(keyword, dtos, results.size(), sortBy);
    }
    
    @Override
    public SearchResponse advancedSearch(String regex, boolean searchInIndex, String sortBy, int page, int size) {
        this.lastSearchQuery = regex;
        
        List<Document> results = documentRepository.searchByRegex(regex, searchInIndex);
        
        // Calculer les métriques
        for (Document doc : results) {
            doc.setOccurrenceCount(1); // Pour regex, on compte juste la présence
            doc.setSnippet(doc.getContent() != null ? 
                doc.getContent().substring(0, Math.min(100, doc.getContent().length())) + "..." : "");
            doc.setCentralityScore(pageRankScores.getOrDefault(doc.getId(), 0.0));
        }
        
        // Tri
        if ("centralityScore".equals(sortBy)) {
            results.sort((a, b) -> Double.compare(b.getCentralityScore(), a.getCentralityScore()));
        }
        
        // Pagination
        int start = page * size;
        int end = Math.min(start + size, results.size());
        List<Document> pageResults = (start < results.size()) ? results.subList(start, end) : Collections.emptyList();
        
        List<DocumentDTO> dtos = pageResults.stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
        
        return new SearchResponse(regex, dtos, results.size(), sortBy);
    }
    
    @Override
    public SuggestionResponse getSuggestions(String lastQuery) {
        String query = (lastQuery != null && !lastQuery.isEmpty()) ? lastQuery : this.lastSearchQuery;
        
        if (query == null || query.isEmpty()) {
            // Retourner les documents avec le meilleur PageRank
            List<DocumentDTO> topDocs = documentRepository.findAll().stream()
                .sorted((a, b) -> Double.compare(
                    pageRankScores.getOrDefault(b.getId(), 0.0),
                    pageRankScores.getOrDefault(a.getId(), 0.0)))
                .limit(5)
                .map(this::toDTO)
                .collect(Collectors.toList());
            
            return new SuggestionResponse(topDocs, "pagerank_top");
        }
        
        // Trouver les documents correspondant à la requête
        List<Document> matchingDocs = documentRepository.searchByKeyword(query);
        
        if (matchingDocs.isEmpty()) {
            return new SuggestionResponse(Collections.emptyList(), "no_matches");
        }
        
        // Prendre les 2-3 meilleurs résultats
        matchingDocs.sort((a, b) -> Integer.compare(
            documentRepository.countOccurrences(b, query),
            documentRepository.countOccurrences(a, query)));
        
        List<String> topResultIds = matchingDocs.stream()
            .limit(3)
            .map(Document::getId)
            .collect(Collectors.toList());
        
        // Trouver les voisins Jaccard de ces documents
        Set<String> suggestionIds = new HashSet<>();
        for (String docId : topResultIds) {
            Map<String, Double> neighbors = jaccardGraph.get(docId);
            if (neighbors != null) {
                // Trier par similarité et prendre les meilleurs voisins
                neighbors.entrySet().stream()
                    .filter(e -> !topResultIds.contains(e.getKey()))
                    .sorted((a, b) -> Double.compare(b.getValue(), a.getValue()))
                    .limit(3)
                    .forEach(e -> suggestionIds.add(e.getKey()));
            }
        }
        
        List<DocumentDTO> suggestions = suggestionIds.stream()
            .map(documentRepository::findById)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(this::toDTO)
            .limit(5)
            .collect(Collectors.toList());
        
        return new SuggestionResponse(suggestions, "jaccard_neighbors");
    }
    
    @Override
    public DocumentDTO getDocumentById(String id) {
        return documentRepository.findById(id)
            .map(doc -> {
                DocumentDTO dto = toDTO(doc);
                dto.setCentralityScore(pageRankScores.getOrDefault(id, 0.0));
                return dto;
            })
            .orElse(null);
    }
    
    private DocumentDTO toDTO(Document doc) {
        DocumentDTO dto = new DocumentDTO();
        dto.setId(doc.getId());
        dto.setTitle(doc.getTitle());
        dto.setAuthor(doc.getAuthor());
        dto.setOccurrenceCount(doc.getOccurrenceCount());
        dto.setCentralityScore(doc.getCentralityScore());
        dto.setSnippet(doc.getSnippet());
        dto.setWordCount(doc.getWordCount());
        dto.setCategory(doc.getCategory());
        return dto;
    }
}
