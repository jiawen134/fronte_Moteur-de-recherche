import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { DocumentResult } from '../../core/models/document.model';
import { BookSelectionService } from '../../core/services/book-selection.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {
  error = '';

  recommended: DocumentResult[] = [
    { id: '1', title: 'Les Misérables', author: 'Victor Hugo', wordCount: 530000, snippet: 'Jean Valjean était un homme condamné au bagne pour avoir volé du pain. Après dix-neuf ans de prison, il est libéré mais se retrouve rejeté par la société...' },
    { id: '2', title: 'Le Comte de Monte-Cristo', author: 'Alexandre Dumas', wordCount: 464000, snippet: 'Edmond Dantès, un jeune marin prometteur, est injustement emprisonné au château d\'If pendant quatorze ans...' },
    { id: '4', title: 'Le Petit Prince', author: 'Antoine de Saint-Exupéry', wordCount: 17000, snippet: 'Un aviateur tombé en panne dans le désert du Sahara rencontre un petit garçon extraordinaire venant d\'une autre planète...' },
    { id: '5', title: 'Vingt mille lieues sous les mers', author: 'Jules Verne', wordCount: 117000, snippet: 'Le professeur Aronnax part en expédition pour chasser un mystérieux monstre marin qui s\'avère être le Nautilus...' }
  ];

  categories = [
    { id: 'all', label: 'Tous' },
    { id: 'sci-fi', label: 'Sci-Fi' },
    { id: 'fiction', label: 'Fiction' },
    { id: 'drama', label: 'Drame' },
    { id: 'business', label: 'Business' },
    { id: 'education', label: 'Éducation' }
  ];

  categoryBooks: DocumentResult[] = [
    { id: '6', title: 'Notre-Dame de Paris', author: 'Victor Hugo', wordCount: 190000, snippet: 'Quasimodo, le sonneur de cloches bossu de Notre-Dame, est un être difforme mais au cœur pur...' },
    { id: '8', title: "L'Étranger", author: 'Albert Camus', wordCount: 36000, snippet: 'Meursault, un employé de bureau à Alger, apprend la mort de sa mère avec une indifférence troublante...' },
    { id: '9', title: 'Les Trois Mousquetaires', author: 'Alexandre Dumas', wordCount: 220000, snippet: 'D\'Artagnan, un jeune Gascon ambitieux, monte à Paris pour devenir mousquetaire du roi...' },
    { id: '13', title: 'Cyrano de Bergerac', author: 'Edmond Rostand', wordCount: 35000, snippet: 'Cyrano est un brillant duelliste et poète, mais son nez immense le complexe. Il aime en secret sa cousine Roxane...' },
    { id: '16', title: 'Introduction to Algorithms', author: 'Thomas H. Cormen', wordCount: 280000, snippet: 'This comprehensive textbook covers a broad range of algorithms in depth with accessible pseudocode...' }
  ];

  selectedCategory = 'all';

  constructor(private bookSelectionService: BookSelectionService) { }

  onBookClick(book: DocumentResult): void {
    this.bookSelectionService.selectBook(book);
  }
}
