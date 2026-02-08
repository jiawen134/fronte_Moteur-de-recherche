# Moteur de recherche – Bibliothèque (projet MRec)

Application web **frontend** du moteur de recherche dans une bibliothèque de livres (texte).  
Ce dépôt contient **uniquement la partie frontend** (Angular). Le backend est prévu en **Java** (entity, DTO, DAO, controller, service, serviceimpl).

## Frontend (Angular)

- **Dossier** : `frontend/`
- **Stack** : Angular 18, SCSS, standalone components

### Fonctionnalités couvertes

| Fonctionnalité | Description |
|----------------|-------------|
| **Recherche (explicite)** | Recherche par mot-clé → liste des documents dont la table d’index contient la chaîne saisie. |
| **Recherche avancée (explicite)** | Recherche par expression régulière (RegEx), avec option « index » ou « contenu textuel ». |
| **Classement (implicite)** | Tri des résultats par **nombre d’occurrences** ou par **score de centralité** (ex. PageRank, betweenness, closeness). |
| **Suggestions (implicite)** | Bloc de suggestions (voisins Jaccard, plus cliqués, etc.) après une recherche. |

### Lancer le frontend

```bash
cd frontend
npm install
npm start
```

Ouvrir http://localhost:4200

### Build

```bash
cd frontend
npm run build
```

### Configuration API

L’URL du backend Java est définie dans :

- **Développement** : `frontend/src/environments/environment.ts`  
  Par défaut : `http://localhost:8080/api`
- **Production** : `frontend/src/environments/environment.prod.ts`  
  Par défaut : `/api` (même origine que le front)

Adapter ces URLs selon votre déploiement.

### Structure du frontend

```
frontend/src/app/
├── core/
│   ├── models/          # document.model, search.model, suggestion.model
│   └── services/        # SearchService (appels API)
├── pages/
│   ├── home/            # Page d’accueil + recherche par mot-clé
│   ├── advanced-search/ # Recherche avancée (RegEx)
│   └── results/         # Résultats + tri + suggestions
├── components/
│   └── suggestion-block/
└── app.component.*      # Layout (navbar + router-outlet)
```

### Endpoints attendus côté backend (Java)

Le `SearchService` appelle :

| Méthode | URL | Paramètres (query) |
|--------|-----|--------------------|
| GET | `/api/search` | `keyword`, optionnel : `sortBy`, `page`, `size` |
| GET | `/api/advanced-search` | `regex`, optionnel : `searchInIndex`, `sortBy`, `page`, `size` |
| GET | `/api/suggestions` | optionnel : `query` (dernière recherche) |

Réponses attendues : voir les interfaces dans `core/models/` (SearchResponse, SuggestionResponse, DocumentResult).

---

**Backend** : à développer séparément en Java (entity, DTO, DAO, controller, service, serviceimpl) et à exposer sur le port configuré (ex. 8080) avec CORS autorisé pour `http://localhost:4200` en dev.
