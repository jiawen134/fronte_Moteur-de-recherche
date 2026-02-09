import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { DocumentResult } from '../../core/models/document.model';

@Component({
    selector: 'app-book-detail',
    standalone: true,
    imports: [CommonModule],
    templateUrl: './book-detail.component.html',
    styleUrl: './book-detail.component.scss'
})
export class BookDetailComponent {
    @Input() book: DocumentResult | null = null;
    @Output() close = new EventEmitter<void>();
    @Output() addToFavorites = new EventEmitter<DocumentResult>();
    @Output() download = new EventEmitter<DocumentResult>();

    constructor(private router: Router) { }

    onClose(): void {
        this.close.emit();
    }

    onAddToFavorites(): void {
        if (this.book) {
            this.addToFavorites.emit(this.book);
        }
    }

    onDownload(): void {
        if (this.book) {
            this.download.emit(this.book);
        }
    }

    onRead(): void {
        if (this.book) {
            this.router.navigate(['/lire', this.book.id]);
        }
    }
}
