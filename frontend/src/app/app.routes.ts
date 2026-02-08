import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { AdvancedSearchComponent } from './pages/advanced-search/advanced-search.component';
import { ResultsComponent } from './pages/results/results.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'recherche-avancee', component: AdvancedSearchComponent },
  { path: 'resultats', component: ResultsComponent },
  { path: '**', redirectTo: '' }
];
