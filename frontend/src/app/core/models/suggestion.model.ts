import { DocumentResult } from './document.model';

/**
 * Suggestion de documents similaires (voisins Jaccard, plus cliqués, etc.).
 */
export interface SuggestionResponse {
  /** Documents suggérés (liés à la dernière recherche). */
  suggestions: DocumentResult[];
  /** Stratégie utilisée (ex: jaccard_neighbors, most_clicked). */
  strategy?: string;
}
