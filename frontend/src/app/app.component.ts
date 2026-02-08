import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterOutlet, RouterLink, RouterLinkActive],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  keyword = '';
  selectedBook: unknown = null;

  constructor(private router: Router) {}

  onSearch(): void {
    const q = this.keyword?.trim();
    if (!q) return;
    this.router.navigate(['/resultats'], { queryParams: { q, type: 'keyword' } });
  }
}
