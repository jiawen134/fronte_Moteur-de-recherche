import { DocumentResult } from './document.model';

/**
 * Réponse de la recherche par mot-clé ou recherche avancée.
 */
export interface SearchResponse {
  query: string;
  documents: DocumentResult[];
  totalCount: number;
  /** Critère de tri appliqué (ex: occurrenceCount, centralityScore). */
  sortBy?: string;
}

/**
 * Paramètres de recherche par mot-clé.
 */
export interface SearchParams {
  keyword: string;
  /** Critère de classement : occurrenceCount | centralityScore (pagerank, betweenness, closeness). */
  sortBy?: string;
  page?: number;
  size?: number;
}

/**
 * Paramètres de recherche avancée (RegEx).
 */
export interface AdvancedSearchParams {
  regex: string;
  /** true = recherche dans la table d'index, false = dans le contenu textuel (plus lent). */
  searchInIndex?: boolean;
  sortBy?: string;
  page?: number;
  size?: number;
}
