import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { SearchService } from '../../core/services/search.service';
import { BookSelectionService } from '../../core/services/book-selection.service';
import { DocumentResult } from '../../core/models/document.model';
import { SearchResponse } from '../../core/models/search.model';
import { SuggestionResponse } from '../../core/models/suggestion.model';
import { SuggestionBlockComponent } from '../../components/suggestion-block/suggestion-block.component';

@Component({
  selector: 'app-results',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink, SuggestionBlockComponent],
  templateUrl: './results.component.html',
  styleUrl: './results.component.scss'
})
export class ResultsComponent implements OnInit {
  query = '';
  type: 'keyword' | 'regex' = 'keyword';
  searchInIndex = true;
  sortBy = 'occurrenceCount';
  documents: DocumentResult[] = [];
  totalCount = 0;
  loading = true;
  error = '';
  suggestions: DocumentResult[] = [];
  suggestionsStrategy = '';

  sortOptions = [
    { value: 'occurrenceCount', label: 'Nombre d\'occurrences' },
    { value: 'centralityScore', label: 'Centralité (ex. PageRank)' }
  ];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private searchService: SearchService,
    private bookSelectionService: BookSelectionService
  ) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.query = params['q'] || '';
      this.type = (params['type'] === 'regex') ? 'regex' : 'keyword';
      this.searchInIndex = params['inIndex'] !== 'false';
      this.sortBy = params['sortBy'] || 'occurrenceCount';
      if (this.query) {
        this.runSearch();
        this.loadSuggestions();
      } else {
        this.loading = false;
        this.error = 'Aucune requête de recherche.';
      }
    });
  }

  runSearch(): void {
    this.loading = true;
    this.error = '';

    if (this.type === 'keyword') {
      this.searchService
        .search({ keyword: this.query, sortBy: this.sortBy })
        .subscribe({
          next: (res: SearchResponse) => {
            this.documents = res.documents;
            this.totalCount = res.totalCount;
            this.loading = false;
          },
          error: err => {
            this.error = err?.error?.message || 'Erreur lors de la recherche. Vérifiez que le backend est démarré sur http://localhost:8080';
            this.loading = false;
          }
        });
    } else {
      this.searchService
        .advancedSearch({
          regex: this.query,
          searchInIndex: this.searchInIndex,
          sortBy: this.sortBy
        })
        .subscribe({
          next: (res: SearchResponse) => {
            this.documents = res.documents;
            this.totalCount = res.totalCount;
            this.loading = false;
          },
          error: err => {
            this.error = err?.error?.message || 'Erreur lors de la recherche avancée. Vérifiez que le backend est démarré.';
            this.loading = false;
          }
        });
    }
  }

  loadSuggestions(): void {
    this.searchService.getSuggestions(this.query).subscribe({
      next: (res: SuggestionResponse) => {
        this.suggestions = res.suggestions || [];
        this.suggestionsStrategy = res.strategy || '';
      },
      error: () => { }
    });
  }

  onSortChange(): void {
    this.router.navigate([], {
      relativeTo: this.route,
      queryParams: { sortBy: this.sortBy },
      queryParamsHandling: 'merge'
    });
    this.runSearch();
  }

  onDocumentClick(doc: DocumentResult): void {
    this.bookSelectionService.selectBook(doc);
  }
}
