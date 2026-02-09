import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { BookDetailComponent } from './components/book-detail/book-detail.component';
import { DocumentResult } from './core/models/document.model';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterOutlet, RouterLink, RouterLinkActive, BookDetailComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  keyword = '';
  selectedBook: DocumentResult | null = null;

  constructor(private router: Router) { }

  onSearch(): void {
    const q = this.keyword?.trim();
    if (!q) return;
    this.router.navigate(['/resultats'], { queryParams: { q, type: 'keyword' } });
  }

  selectBook(book: DocumentResult): void {
    this.selectedBook = book;
  }

  onAddToFavorites(book: DocumentResult): void {
    console.log('Added to favorites:', book.title);
    // 实际实现中会调用服务保存到收藏
  }

  onDownload(book: DocumentResult): void {
    console.log('Download:', book.title);
    // 实际实现中会触发下载
  }
}
