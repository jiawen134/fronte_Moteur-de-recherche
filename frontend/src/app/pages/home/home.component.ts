import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {
  error = '';

  recommended = [
    { title: 'The Psychology of Money', author: 'Morgan Housel', emoji: '📕' },
    { title: 'How Innovation Works', author: 'Matt Ridley', emoji: '📗' },
    { title: 'Company of One', author: 'Paul Jarvis', emoji: '📘' },
    { title: 'Stupeur et tremblements', author: 'Amélie Nothomb', emoji: '📙' }
  ];

  categories = [
    { id: 'all', label: 'Tous' },
    { id: 'sci-fi', label: 'Sci-Fi' },
    { id: 'fantasy', label: 'Fantastique' },
    { id: 'drama', label: 'Drame' },
    { id: 'business', label: 'Business' },
    { id: 'education', label: 'Éducation' },
    { id: 'geography', label: 'Géographie' }
  ];

  categoryBooks = [
    { title: 'The Bees', author: 'Laline Paull', emoji: '📕' },
    { title: 'Real Help', author: 'Ayodele Konedov', emoji: '📗' },
    { title: 'The Fact of a Body', author: 'Alexandria Marzano-Lesnevich', emoji: '📘' },
    { title: 'The Booth', author: 'James Robinson', emoji: '📙' },
    { title: 'Through the...', author: 'Cate McDonald', emoji: '📔' }
  ];

  selectedCategory = 'all';
}
