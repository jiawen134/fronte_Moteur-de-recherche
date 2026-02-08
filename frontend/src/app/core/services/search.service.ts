import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import {
  SearchResponse,
  SearchParams,
  AdvancedSearchParams
} from '../models/search.model';
import { SuggestionResponse } from '../models/suggestion.model';

/**
 * Service d'appel API vers le backend Java (recherche, recherche avancée, suggestions).
 * Adapter les URLs selon votre backend (ex: /api/search, /api/advanced-search, /api/suggestions).
 */
@Injectable({ providedIn: 'root' })
export class SearchService {
  private readonly apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  /**
   * Recherche par mot-clé (fonctionnalité explicite).
   */
  search(params: SearchParams): Observable<SearchResponse> {
    let httpParams = new HttpParams()
      .set('keyword', params.keyword);
    if (params.sortBy) httpParams = httpParams.set('sortBy', params.sortBy);
    if (params.page != null) httpParams = httpParams.set('page', params.page.toString());
    if (params.size != null) httpParams = httpParams.set('size', params.size.toString());

    return this.http.get<SearchResponse>(`${this.apiUrl}/search`, { params: httpParams });
  }

  /**
   * Recherche avancée par RegEx (fonctionnalité explicite).
   */
  advancedSearch(params: AdvancedSearchParams): Observable<SearchResponse> {
    let httpParams = new HttpParams()
      .set('regex', params.regex);
    if (params.searchInIndex != null) {
      httpParams = httpParams.set('searchInIndex', params.searchInIndex.toString());
    }
    if (params.sortBy) httpParams = httpParams.set('sortBy', params.sortBy);
    if (params.page != null) httpParams = httpParams.set('page', params.page.toString());
    if (params.size != null) httpParams = httpParams.set('size', params.size.toString());

    return this.http.get<SearchResponse>(`${this.apiUrl}/advanced-search`, { params: httpParams });
  }

  /**
   * Récupère les suggestions après une recherche (fonctionnalité implicite).
   * Le backend peut utiliser le dernier mot-clé/regex de la session ou un paramètre.
   */
  getSuggestions(lastQuery?: string): Observable<SuggestionResponse> {
    let params = new HttpParams();
    if (lastQuery) params = params.set('query', lastQuery);
    return this.http.get<SuggestionResponse>(`${this.apiUrl}/suggestions`, { params });
  }
}
