import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { DocumentResult } from '../models/document.model';

/**
 * 服务用于在组件之间共享选中的书籍状态
 */
@Injectable({ providedIn: 'root' })
export class BookSelectionService {
    private selectedBookSubject = new BehaviorSubject<DocumentResult | null>(null);

    selectedBook$: Observable<DocumentResult | null> = this.selectedBookSubject.asObservable();

    selectBook(book: DocumentResult): void {
        this.selectedBookSubject.next(book);
    }

    clearSelection(): void {
        this.selectedBookSubject.next(null);
    }

    getSelectedBook(): DocumentResult | null {
        return this.selectedBookSubject.value;
    }
}
