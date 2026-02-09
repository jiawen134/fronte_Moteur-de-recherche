import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
    selector: 'app-downloads',
    standalone: true,
    imports: [CommonModule, RouterLink],
    templateUrl: './downloads.component.html',
    styleUrl: './downloads.component.scss'
})
export class DownloadsComponent {
    // 模拟下载数据
    downloads = [
        { id: '1', title: 'Les Misérables', author: 'Victor Hugo', size: '2.5 MB', status: 'completed', emoji: '📕' },
        { id: '5', title: 'Vingt mille lieues sous les mers', author: 'Jules Verne', size: '1.8 MB', status: 'completed', emoji: '📘' },
        { id: '16', title: 'Introduction to Algorithms', author: 'Thomas H. Cormen', size: '15.2 MB', status: 'downloading', progress: 65, emoji: '📗' }
    ];

    getStatusLabel(status: string): string {
        return status === 'completed' ? 'Terminé' : 'En cours';
    }

    removeDownload(bookId: string): void {
        this.downloads = this.downloads.filter(d => d.id !== bookId);
    }
}
