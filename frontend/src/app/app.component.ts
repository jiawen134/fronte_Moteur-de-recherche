import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { Subscription } from 'rxjs';
import { BookDetailComponent } from './components/book-detail/book-detail.component';
import { BookSelectionService } from './core/services/book-selection.service';
import { DocumentResult } from './core/models/document.model';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterOutlet, RouterLink, RouterLinkActive, BookDetailComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent implements OnInit, OnDestroy {
  keyword = '';
  selectedBook: DocumentResult | null = null;
  private subscription?: Subscription;

  constructor(
    private router: Router,
    private bookSelectionService: BookSelectionService
  ) { }

  ngOnInit(): void {
    this.subscription = this.bookSelectionService.selectedBook$.subscribe(book => {
      this.selectedBook = book;
    });
  }

  ngOnDestroy(): void {
    this.subscription?.unsubscribe();
  }

  onSearch(): void {
    const q = this.keyword?.trim();
    if (!q) return;
    this.router.navigate(['/resultats'], { queryParams: { q, type: 'keyword' } });
  }

  onClosePanel(): void {
    this.bookSelectionService.clearSelection();
  }

  onAddToFavorites(book: DocumentResult): void {
    console.log('Added to favorites:', book.title);
    alert(`"${book.title}" ajouté aux favoris!`);
  }

  onDownload(book: DocumentResult): void {
    console.log('Download:', book.title);
    alert(`Téléchargement de "${book.title}" commencé!`);
  }
}
