import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { SearchService } from '../../core/services/search.service';
import { DocumentResult } from '../../core/models/document.model';

@Component({
    selector: 'app-reader',
    standalone: true,
    imports: [CommonModule, RouterLink],
    templateUrl: './reader.component.html',
    styleUrl: './reader.component.scss'
})
export class ReaderComponent implements OnInit {
    book: DocumentResult | null = null;
    loading = true;
    error = '';
    fontSize = 16;

    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private searchService: SearchService
    ) { }

    ngOnInit(): void {
        const id = this.route.snapshot.paramMap.get('id');
        if (id) {
            this.loadBook(id);
        } else {
            this.error = 'ID du document non spécifié.';
            this.loading = false;
        }
    }

    loadBook(id: string): void {
        this.loading = true;
        this.searchService.getDocument(id).subscribe({
            next: (doc) => {
                this.book = doc;
                this.loading = false;
            },
            error: (err) => {
                this.error = 'Impossible de charger le document.';
                this.loading = false;
                console.error(err);
            }
        });
    }

    increaseFontSize(): void {
        if (this.fontSize < 28) this.fontSize += 2;
    }

    decreaseFontSize(): void {
        if (this.fontSize > 12) this.fontSize -= 2;
    }

    goBack(): void {
        this.router.navigate(['/']);
    }
}
