import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
    selector: 'app-my-library',
    standalone: true,
    imports: [CommonModule, RouterLink],
    templateUrl: './my-library.component.html',
    styleUrl: './my-library.component.scss'
})
export class MyLibraryComponent {
    // 模拟用户书库数据
    myBooks = [
        { id: '1', title: 'Les Misérables', author: 'Victor Hugo', progress: 75, emoji: '📕' },
        { id: '2', title: 'Le Comte de Monte-Cristo', author: 'Alexandre Dumas', progress: 45, emoji: '📗' },
        { id: '5', title: 'Vingt mille lieues sous les mers', author: 'Jules Verne', progress: 100, emoji: '📘' }
    ];

    recentlyViewed = [
        { id: '4', title: 'Le Petit Prince', author: 'Antoine de Saint-Exupéry', emoji: '📙' },
        { id: '8', title: "L'Étranger", author: 'Albert Camus', emoji: '📔' }
    ];
}
