import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-advanced-search',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './advanced-search.component.html',
  styleUrl: './advanced-search.component.scss'
})
export class AdvancedSearchComponent {
  regex = '';
  searchInIndex = true;
  loading = false;
  error = '';

  constructor(private router: Router) {}

  onSearch(): void {
    const q = this.regex?.trim();
    if (!q) {
      this.error = 'Veuillez saisir une expression régulière.';
      return;
    }
    this.error = '';
    this.router.navigate(['/resultats'], {
      queryParams: {
        q,
        type: 'regex',
        inIndex: this.searchInIndex
      }
    });
  }
}
