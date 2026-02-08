/**
 * Document (livre) retourné par le moteur de recherche.
 */
export interface DocumentResult {
  id: string;
  title: string;
  author?: string;
  /** Nombre d'occurrences du mot-clé dans le document (pour le classement). */
  occurrenceCount?: number;
  /** Indice de centralité (ex. PageRank, betweenness, closeness) pour le classement. */
  centralityScore?: number;
  /** Aperçu du contenu autour des occurrences. */
  snippet?: string;
  /** Nombre de mots dans le document. */
  wordCount?: number;
}
