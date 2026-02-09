import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
    selector: 'app-favorites',
    standalone: true,
    imports: [CommonModule, RouterLink],
    templateUrl: './favorites.component.html',
    styleUrl: './favorites.component.scss'
})
export class FavoritesComponent {
    // 模拟收藏数据
    favorites = [
        { id: '4', title: 'Le Petit Prince', author: 'Antoine de Saint-Exupéry', addedDate: '2026-02-01', emoji: '📙' },
        { id: '9', title: 'Les Trois Mousquetaires', author: 'Alexandre Dumas', addedDate: '2026-01-28', emoji: '📕' },
        { id: '13', title: 'Cyrano de Bergerac', author: 'Edmond Rostand', addedDate: '2026-01-20', emoji: '📗' }
    ];

    removeFromFavorites(bookId: string): void {
        this.favorites = this.favorites.filter(book => book.id !== bookId);
    }
}
