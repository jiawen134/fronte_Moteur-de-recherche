import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { AdvancedSearchComponent } from './pages/advanced-search/advanced-search.component';
import { ResultsComponent } from './pages/results/results.component';
import { MyLibraryComponent } from './pages/my-library/my-library.component';
import { FavoritesComponent } from './pages/favorites/favorites.component';
import { DownloadsComponent } from './pages/downloads/downloads.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'recherche-avancee', component: AdvancedSearchComponent },
  { path: 'resultats', component: ResultsComponent },
  { path: 'ma-bibliotheque', component: MyLibraryComponent },
  { path: 'favoris', component: FavoritesComponent },
  { path: 'telechargements', component: DownloadsComponent },
  { path: '**', redirectTo: '' }
];
