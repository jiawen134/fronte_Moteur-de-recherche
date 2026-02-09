package com.library.repository;

import com.library.model.Document;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Dépôt de documents (livres) en mémoire.
 * Simule une base de données avec des livres d'exemple.
 * Implémente l'index inversé pour la recherche.
 */
@Repository
public class DocumentRepository {
    
    private final Map<String, Document> documents = new HashMap<>();
    private final Map<String, Set<String>> invertedIndex = new HashMap<>();
    
    @PostConstruct
    public void init() {
        // Initialiser avec des livres d'exemple (simulation Gutenberg)
        loadSampleBooks();
        buildInvertedIndex();
    }
    
    private void loadSampleBooks() {
        // Livres d'exemple avec contenu réaliste
        addBook("1", "Les Misérables", "Victor Hugo", "Fiction",
            "Jean Valjean était un homme condamné au bagne pour avoir volé du pain. " +
            "Après dix-neuf ans de prison, il est libéré mais se retrouve rejeté par la société. " +
            "Seul l'évêque Myriel lui offre l'hospitalité et change sa vie par un acte de charité extraordinaire. " +
            "Valjean décide alors de devenir un homme nouveau, consacrant sa vie à aider les pauvres et les opprimés. " +
            "Il rencontre Fantine, une ouvrière désespérée, et promet de sauver sa fille Cosette. " +
            "Poursuivi par l'inspecteur Javert, Valjean doit constamment fuir tout en protégeant ceux qu'il aime.");
        
        addBook("2", "Le Comte de Monte-Cristo", "Alexandre Dumas", "Fiction",
            "Edmond Dantès, un jeune marin prometteur, est injustement emprisonné au château d'If. " +
            "Pendant quatorze ans, il médite sa vengeance contre ceux qui l'ont trahi. " +
            "Grâce à l'abbé Faria, il découvre l'emplacement d'un immense trésor sur l'île de Monte-Cristo. " +
            "Après son évasion spectaculaire, il devient le mystérieux comte de Monte-Cristo. " +
            "Armé de sa fortune colossale et de sa connaissance des secrets de ses ennemis, " +
            "il orchestre une vengeance complexe et implacable contre Danglars, Fernand et Villefort.");
        
        addBook("3", "Madame Bovary", "Gustave Flaubert", "Fiction",
            "Emma Bovary est une jeune femme romantique mariée à un médecin de campagne. " +
            "Déçue par sa vie monotone et bourgeoise, elle cherche l'évasion dans les rêves et les liaisons. " +
            "Sa quête insatiable de passion et de luxe la mène à contracter des dettes considérables. " +
            "Les amants qu'elle prend, Rodolphe puis Léon, finissent par la décevoir. " +
            "Acculée par ses créanciers et abandonnée de tous, Emma sombre dans le désespoir. " +
            "Ce roman est considéré comme un chef-d'œuvre du réalisme français.");
        
        addBook("4", "Le Petit Prince", "Antoine de Saint-Exupéry", "Fiction",
            "Un aviateur tombé en panne dans le désert du Sahara rencontre un petit garçon extraordinaire. " +
            "Le petit prince vient d'une minuscule planète où il a laissé une rose qu'il aime. " +
            "Il raconte ses voyages sur différentes planètes habitées par des adultes étranges. " +
            "Le roi, le vaniteux, le buveur, le businessman, l'allumeur de réverbères et le géographe. " +
            "Sur Terre, il se lie d'amitié avec un renard qui lui apprend le secret de l'apprivoisement. " +
            "L'essentiel est invisible pour les yeux, on ne voit bien qu'avec le cœur.");
        
        addBook("5", "Vingt mille lieues sous les mers", "Jules Verne", "Sci-Fi",
            "Le professeur Aronnax part en expédition pour chasser un mystérieux monstre marin. " +
            "Ce monstre s'avère être le Nautilus, un sous-marin révolutionnaire commandé par le capitaine Nemo. " +
            "Aronnax, son domestique Conseil et le harponneur Ned Land sont retenus prisonniers à bord. " +
            "Ils découvrent les merveilles des océans: l'Atlantide, la banquise antarctique, les trésors engloutis. " +
            "Le capitaine Nemo est un homme tourmenté qui a renoncé à la société des hommes. " +
            "Cette aventure scientifique extraordinaire explore les profondeurs inconnues de la mer.");
        
        addBook("6", "Notre-Dame de Paris", "Victor Hugo", "Fiction",
            "Quasimodo, le sonneur de cloches bossu de Notre-Dame, est un être difforme mais au cœur pur. " +
            "Il voue une dévotion absolue à son maître, l'archidiacre Claude Frollo. " +
            "La belle Esmeralda, danseuse bohémienne, éveille des passions destructrices dans le cœur de Frollo. " +
            "Quasimodo sauve Esmeralda et la protège dans la cathédrale en invoquant le droit d'asile. " +
            "Le poète Gringoire et le capitaine Phoebus sont également fascinés par la jeune femme. " +
            "La cathédrale de Paris devient le personnage central de cette tragédie médiévale.");
        
        addBook("7", "Germinal", "Émile Zola", "Fiction",
            "Étienne Lantier arrive dans le bassin minier du Nord de la France à la recherche de travail. " +
            "Il découvre les conditions de vie effroyables des mineurs et de leurs familles. " +
            "Les Maheu, une famille de mineurs, l'accueillent et lui font découvrir la réalité de la mine. " +
            "Face à une nouvelle baisse de salaire, Étienne organise une grève qui se transforme en révolte. " +
            "La violence éclate, les morts s'accumulent, mais la flamme de la révolte reste vivace. " +
            "Ce roman est un tableau saisissant de la condition ouvrière au XIXe siècle.");
        
        addBook("8", "L'Étranger", "Albert Camus", "Fiction",
            "Meursault, un employé de bureau à Alger, apprend la mort de sa mère avec une indifférence troublante. " +
            "Il continue sa vie routinière, se lie avec Marie et aide son voisin Raymond Sintès. " +
            "Sur une plage ensoleillée, il tue un Arabe sans raison apparente. " +
            "Lors de son procès, c'est moins le meurtre que son comportement à l'enterrement qu'on lui reproche. " +
            "Condamné à mort, Meursault refuse les consolations de l'aumônier. " +
            "Ce roman explore l'absurde et l'indifférence de l'univers face à l'existence humaine.");
        
        addBook("9", "Les Trois Mousquetaires", "Alexandre Dumas", "Fiction",
            "D'Artagnan, un jeune Gascon ambitieux, monte à Paris pour devenir mousquetaire du roi. " +
            "Il se lie d'amitié avec Athos, Porthos et Aramis, les trois mousquetaires inséparables. " +
            "Ensemble, ils affrontent les intrigues du cardinal de Richelieu et de la mystérieuse Milady. " +
            "L'affaire des ferrets de diamants les entraîne dans une course contre la montre. " +
            "Tous pour un, un pour tous devient leur devise légendaire. " +
            "Cette aventure de cape et d'épée célèbre l'amitié, l'honneur et le courage.");
        
        addBook("10", "Le Rouge et le Noir", "Stendhal", "Fiction",
            "Julien Sorel, fils d'un charpentier, rêve de gloire et d'ascension sociale. " +
            "Il devient précepteur chez les Rênal et séduit Madame de Rênal, sa première conquête. " +
            "Au séminaire de Besançon, il apprend l'hypocrisie nécessaire pour réussir. " +
            "Secrétaire du marquis de la Mole à Paris, il séduit sa fille Mathilde. " +
            "Son ambition démesurée le conduit finalement à la tragédie et à l'échafaud. " +
            "Ce roman analyse la société française de la Restauration avec une acuité remarquable.");
        
        addBook("11", "Candide", "Voltaire", "Fiction",
            "Candide vit heureux dans le château du baron Thunder-ten-tronckh avec son maître Pangloss. " +
            "Chassé pour avoir embrassé Cunégonde, il découvre la cruauté du monde. " +
            "Guerres, tremblements de terre, inquisition, esclavage parsèment son voyage. " +
            "Malgré tout, Pangloss répète que tout est pour le mieux dans le meilleur des mondes. " +
            "De l'Europe à l'Amérique, Candide cherche Cunégonde et le bonheur. " +
            "Il conclut qu'il faut cultiver son jardin, rejetant l'optimisme naïf de Pangloss.");
        
        addBook("12", "La Comédie Humaine", "Honoré de Balzac", "Fiction",
            "Le père Goriot a sacrifié toute sa fortune pour ses deux filles ingrates. " +
            "Rastignac, jeune étudiant ambitieux, découvre les rouages de la société parisienne. " +
            "Vautrin, le mystérieux pensionnaire, lui propose un pacte criminel pour réussir. " +
            "La pension Vauquer réunit une galerie de personnages de la comédie humaine. " +
            "Goriot meurt seul et abandonné, ses filles refusant même de venir à son enterrement. " +
            "Rastignac défie alors Paris du haut du Père-Lachaise : À nous deux maintenant!");
        
        addBook("13", "Cyrano de Bergerac", "Edmond Rostand", "Drama",
            "Cyrano est un brillant duelliste et poète, mais son nez immense le complexe. " +
            "Il aime en secret sa cousine Roxane, mais n'ose pas lui déclarer sa flamme. " +
            "Roxane aime Christian, un beau cadavre dépourvue d'éloquence. " +
            "Cyrano prête sa plume et son cœur à Christian pour séduire Roxane. " +
            "Sous le balcon, c'est Cyrano qui murmure les mots d'amour que Christian répète. " +
            "La vérité n'éclate qu'au moment de la mort de Cyrano, quinze ans plus tard.");
        
        addBook("14", "Du côté de chez Swann", "Marcel Proust", "Fiction",
            "Le narrateur, couché, laisse vagabonder ses souvenirs d'enfance à Combray. " +
            "Une madeleine trempée dans du thé fait ressurgir tout un monde enfoui. " +
            "Les promenades du côté de Méséglise et du côté de Guermantes structurent son univers. " +
            "L'amour de Swann pour Odette, une cocotte, est disséqué dans toutes ses nuances. " +
            "Le temps perdu et retrouvé par la mémoire involontaire est le thème central. " +
            "Cette œuvre révolutionne le roman par son exploration de la conscience et du temps.");
        
        addBook("15", "Le Tour du monde en quatre-vingts jours", "Jules Verne", "Sci-Fi",
            "Phileas Fogg, gentleman britannique méthodique, parie qu'il peut faire le tour du monde en 80 jours. " +
            "Accompagné de son valet Passepartout, il part de Londres le 2 octobre 1872. " +
            "Le détective Fix le soupçonne d'être un voleur de banque et le suit partout. " +
            "De Suez à Bombay, de Calcutta à Hong Kong, de Yokohama à San Francisco, l'aventure continue. " +
            "Ils sauvent la princesse indienne Aouda et affrontent mille obstacles imprévus. " +
            "Le dénouement réserve une surprise grâce au franchissement de la ligne de changement de date.");
        
        addBook("16", "Introduction to Algorithms", "Thomas H. Cormen", "Education",
            "This comprehensive textbook covers a broad range of algorithms in depth. " +
            "It provides accessible pseudocode for all algorithms discussed. " +
            "Topics include sorting, searching, graph algorithms, and dynamic programming. " +
            "The book analyzes algorithm complexity using asymptotic notation. " +
            "Data structures like trees, heaps, and hash tables are thoroughly explained. " +
            "It is considered the definitive guide to algorithms for computer science students.");
        
        addBook("17", "Clean Code", "Robert C. Martin", "Education",
            "Writing clean code is essential for maintainable software development. " +
            "Meaningful names, small functions, and proper formatting improve readability. " +
            "Comments should explain why, not what the code does. " +
            "Error handling and testing are integral parts of clean code practices. " +
            "Refactoring techniques help transform messy code into clean code. " +
            "The book provides practical examples and patterns for professional developers.");
        
        addBook("18", "Design Patterns", "Gang of Four", "Education",
            "Design patterns are reusable solutions to common software design problems. " +
            "Creational patterns like Singleton and Factory manage object creation. " +
            "Structural patterns like Adapter and Decorator compose objects. " +
            "Behavioral patterns like Observer and Strategy define object interactions. " +
            "Each pattern includes intent, motivation, and implementation details. " +
            "Understanding patterns improves communication among developers.");
        
        addBook("19", "The Art of War", "Sun Tzu", "Business",
            "The supreme art of war is to subdue the enemy without fighting. " +
            "Know yourself and know your enemy to win a hundred battles. " +
            "Strategic planning and tactical flexibility are essential for victory. " +
            "Deception, speed, and surprise give advantage over opponents. " +
            "Leadership requires wisdom, sincerity, benevolence, courage, and strictness. " +
            "These ancient principles apply to business and competitive strategy today.");
        
        addBook("20", "Thinking, Fast and Slow", "Daniel Kahneman", "Business",
            "The human mind operates with two systems of thinking. " +
            "System 1 is fast, intuitive, and emotional in its judgments. " +
            "System 2 is slow, deliberate, and logical in its reasoning. " +
            "Cognitive biases affect our decisions in predictable ways. " +
            "Anchoring, availability, and loss aversion distort rational thinking. " +
            "Understanding these biases helps make better decisions in life and business.");
    }
    
    private void addBook(String id, String title, String author, String category, String content) {
        Document doc = new Document(id, title, author, content);
        doc.setCategory(category);
        
        // Extraire les mots-clés
        Set<String> keywords = new HashSet<>();
        String[] words = content.toLowerCase().split("[\\s,.;:!?()]+");
        for (String word : words) {
            if (word.length() > 3) {
                keywords.add(word);
            }
        }
        doc.setKeywords(new ArrayList<>(keywords));
        
        documents.put(id, doc);
    }
    
    private void buildInvertedIndex() {
        for (Document doc : documents.values()) {
            if (doc.getContent() != null) {
                String[] words = doc.getContent().toLowerCase().split("[\\s,.;:!?()]+");
                for (String word : words) {
                    if (word.length() > 2) {
                        invertedIndex.computeIfAbsent(word, k -> new HashSet<>()).add(doc.getId());
                    }
                }
            }
            if (doc.getTitle() != null) {
                String[] titleWords = doc.getTitle().toLowerCase().split("[\\s,.;:!?()]+");
                for (String word : titleWords) {
                    if (word.length() > 1) {
                        invertedIndex.computeIfAbsent(word, k -> new HashSet<>()).add(doc.getId());
                    }
                }
            }
        }
    }
    
    public List<Document> findAll() {
        return new ArrayList<>(documents.values());
    }
    
    public Optional<Document> findById(String id) {
        return Optional.ofNullable(documents.get(id));
    }
    
    public List<Document> searchByKeyword(String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        Set<String> matchingIds = new HashSet<>();
        
        // Recherche dans l'index inversé
        for (Map.Entry<String, Set<String>> entry : invertedIndex.entrySet()) {
            if (entry.getKey().contains(lowerKeyword)) {
                matchingIds.addAll(entry.getValue());
            }
        }
        
        return matchingIds.stream()
            .map(documents::get)
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }
    
    public List<Document> searchByRegex(String regex, boolean searchInIndex) {
        Pattern pattern;
        try {
            pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        } catch (Exception e) {
            return Collections.emptyList();
        }
        
        if (searchInIndex) {
            // Recherche dans les clés de l'index
            Set<String> matchingIds = new HashSet<>();
            for (Map.Entry<String, Set<String>> entry : invertedIndex.entrySet()) {
                if (pattern.matcher(entry.getKey()).find()) {
                    matchingIds.addAll(entry.getValue());
                }
            }
            return matchingIds.stream()
                .map(documents::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        } else {
            // Recherche dans le contenu complet (plus lent)
            return documents.values().stream()
                .filter(doc -> doc.getContent() != null && pattern.matcher(doc.getContent()).find())
                .collect(Collectors.toList());
        }
    }
    
    public Map<String, Set<String>> getInvertedIndex() {
        return invertedIndex;
    }
    
    public int countOccurrences(Document doc, String keyword) {
        if (doc.getContent() == null) return 0;
        String content = doc.getContent().toLowerCase();
        String lowerKeyword = keyword.toLowerCase();
        int count = 0;
        int index = 0;
        while ((index = content.indexOf(lowerKeyword, index)) != -1) {
            count++;
            index += lowerKeyword.length();
        }
        return count;
    }
    
    public String extractSnippet(Document doc, String keyword, int contextLength) {
        if (doc.getContent() == null) return "";
        String content = doc.getContent();
        String lowerContent = content.toLowerCase();
        String lowerKeyword = keyword.toLowerCase();
        int index = lowerContent.indexOf(lowerKeyword);
        if (index == -1) return content.substring(0, Math.min(100, content.length())) + "...";
        
        int start = Math.max(0, index - contextLength);
        int end = Math.min(content.length(), index + keyword.length() + contextLength);
        
        String snippet = content.substring(start, end);
        if (start > 0) snippet = "..." + snippet;
        if (end < content.length()) snippet = snippet + "...";
        
        return snippet;
    }
}
