# Moteur de recherche – Bibliothèque (projet MRec)

Application web complète de moteur de recherche dans une bibliothèque de livres (texte).  
Ce projet contient le **frontend Angular** et le **backend Java Spring Boot**.

## 🎯 Fonctionnalités

### Fonctionnalités principales

| Fonctionnalité | Description | Statut |
|----------------|-------------|--------|
| **Recherche par mot-clé** | Recherche dans l'index inversé | ✅ Implémenté |
| **Recherche avancée (RegEx)** | Recherche par expression régulière | ✅ Implémenté |
| **Classement intelligent** | Tri par occurrences ou centralité (PageRank) | ✅ Implémenté |
| **Suggestions** | Recommandations basées sur Jaccard/PageRank | ✅ Implémenté |
| **Lecture en ligne** | Lecteur de livres intégré | ✅ Implémenté |

### Fonctionnalités supplémentaires

- 📚 **Ma bibliothèque** - Gestion des livres en cours de lecture
- ♥ **Favoris** - Sauvegarde des livres préférés
- ↓ **Téléchargements** - Gestion des téléchargements
- 🎨 **Interface moderne** - Dark theme, glassmorphism, animations

## 🚀 Démarrage rapide

### Prérequis

- **Java 17+** (pour le backend)
- **Node.js 18+** et npm (pour le frontend)
- **Maven** (ou utiliser le wrapper inclus)

### 1. Démarrer le backend

```bash
cd backend
./apache-maven-3.9.6/bin/mvn spring-boot:run
```

Le backend sera disponible sur **http://localhost:8080**

### 2. Démarrer le frontend

```bash
cd frontend
npm install
npm start
```

Le frontend sera disponible sur **http://localhost:4200**

## 📁 Structure du projet

```
fronte_Moteur-de-recherche/
├── backend/                    # Backend Java Spring Boot
│   ├── src/main/java/com/library/
│   │   ├── controller/        # REST API endpoints
│   │   ├── service/           # Logique métier (Jaccard, PageRank)
│   │   ├── repository/        # Accès aux données (20 livres)
│   │   ├── model/             # Entités (Document)
│   │   └── dto/               # Data Transfer Objects
│   └── pom.xml
│
└── frontend/                   # Frontend Angular 18
    ├── src/app/
    │   ├── pages/             # Pages de l'application
    │   │   ├── home/          # Page d'accueil
    │   │   ├── advanced-search/  # Recherche avancée
    │   │   ├── results/       # Résultats de recherche
    │   │   ├── reader/        # Lecteur de livres
    │   │   ├── my-library/    # Ma bibliothèque
    │   │   ├── favorites/     # Favoris
    │   │   └── downloads/     # Téléchargements
    │   ├── components/        # Composants réutilisables
    │   │   ├── book-detail/   # Panneau de détails
    │   │   └── suggestion-block/  # Bloc de suggestions
    │   └── core/
    │       ├── models/        # Interfaces TypeScript
    │       └── services/      # Services API
    └── package.json
```

## 🔌 API Backend

### Endpoints disponibles

| Méthode | URL | Paramètres | Description |
|---------|-----|------------|-------------|
| GET | `/api/search` | `keyword`, `sortBy`, `page`, `size` | Recherche par mot-clé |
| GET | `/api/advanced-search` | `regex`, `searchInIndex`, `sortBy` | Recherche par RegEx |
| GET | `/api/suggestions` | `query` | Obtenir des suggestions |
| GET | `/api/documents/{id}` | - | Obtenir un document complet |

### Exemples d'utilisation

```bash
# Recherche par mot-clé
curl "http://localhost:8080/api/search?keyword=jean"

# Recherche avancée
curl "http://localhost:8080/api/advanced-search?regex=Le"

# Obtenir un livre complet
curl "http://localhost:8080/api/documents/1"
```

## 🎨 Interface utilisateur

### Pages disponibles

- **/** - Page d'accueil avec livres recommandés
- **/recherche-avancee** - Recherche par expression régulière
- **/resultats** - Affichage des résultats de recherche
- **/lire/:id** - Lecteur de livres en ligne
- **/ma-bibliotheque** - Gestion de la bibliothèque personnelle
- **/favoris** - Liste des livres favoris
- **/telechargements** - Gestion des téléchargements

### Caractéristiques UI

- 🌙 **Dark theme** moderne avec dégradés
- 🪟 **Glassmorphism** sur la sidebar et les panneaux
- ✨ **Animations fluides** (hover, fade-in, slide-in)
- 📱 **Design responsive** (mobile, tablette, desktop)
- 🎯 **Navigation intuitive** avec sidebar

## 🧮 Algorithmes implémentés

### 1. Index inversé
Construction d'un index mot → liste de documents pour une recherche rapide.

### 2. Similarité de Jaccard
Calcul de la similarité entre documents basé sur leurs mots-clés :
```
Jaccard(A, B) = |A ∩ B| / |A ∪ B|
```

### 3. PageRank
Algorithme de centralité pour classer les documents par importance :
- Damping factor : 0.85
- Convergence : 100 itérations max
- Basé sur le graphe de Jaccard

## 📊 Données

Le backend contient **20 livres** de démonstration :
- Classiques français (Hugo, Dumas, Camus, etc.)
- Livres techniques (Algorithms, Clean Code, Design Patterns)
- Livres de business (Art of War, Thinking Fast and Slow)

## 🛠️ Build pour production

### Backend
```bash
cd backend
./apache-maven-3.9.6/bin/mvn clean package
java -jar target/library-search-engine-1.0.0.jar
```

### Frontend
```bash
cd frontend
npm run build
# Les fichiers seront dans dist/frontend/
```

## 🔧 Configuration

### Backend
Fichier : `backend/src/main/resources/application.properties`
```properties
server.port=8080
logging.level.com.library=DEBUG
```

### Frontend
Fichier : `frontend/src/environments/environment.ts`
```typescript
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080/api'
};
```

## 📝 Technologies utilisées

### Backend
- Java 17
- Spring Boot 3.2.2
- Maven
- Lombok

### Frontend
- Angular 18
- TypeScript
- SCSS
- Standalone Components
- RxJS

## 🎓 Fonctionnalités académiques

Ce projet implémente les concepts suivants :
- ✅ Indexation et recherche textuelle
- ✅ Expressions régulières
- ✅ Graphes de similarité (Jaccard)
- ✅ Algorithmes de centralité (PageRank)
- ✅ Architecture REST API
- ✅ Reactive programming (RxJS)

## 📄 License

Projet académique - MRec

---

**Développé avec ❤️ pour le cours MRec**
