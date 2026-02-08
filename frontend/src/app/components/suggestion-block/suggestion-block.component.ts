import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DocumentResult } from '../../core/models/document.model';

@Component({
  selector: 'app-suggestion-block',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './suggestion-block.component.html',
  styleUrl: './suggestion-block.component.scss'
})
export class SuggestionBlockComponent {
  @Input() suggestions: DocumentResult[] = [];
  @Input() strategy = '';
}
