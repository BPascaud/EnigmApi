package com.proj.proj.Model;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

public class TestData {

    public static List<Enigme> getFakeEnigmes() {
        return List.of(
                new Enigme(1, "Combien font 2 + 2 ?","4", "java", "BRONZE"),
                new Enigme(2, "Quel mot-clé déclare une variable en Java ?","let", "java", "OR"),
                new Enigme(3, "Quelle clause filtre en SQL ?",";", "sql", "OR"),
                new Enigme(4, "Quelle clause regroupe en SQL ?","*", "sql", "PLATINE"),
                new Enigme(5, "Annotation REST Spring ?","@RestController", "spring boot", "PLATINE"),
                new Enigme(6, "Injection de dépendance avec Spring ?","Autowired", "spring boot", "DIAMANT"),
                new Enigme(7, "typeof null retourne quoi ?","null", "js", "DIAMANT"),
                new Enigme(8, "Quel mot-clé JS déclare une constante ?","let", "js", "OR"),
                new Enigme(9, "Directive pour afficher sous condition ?","sout", "vue.js", "OR"),
                new Enigme(10, "Boucle sur une liste en Vue ?","boucle", "vue.js", "PLATINE"),

                new Enigme(11, "Quelle est la portée d'une variable déclarée avec 'var' en Java ?", "bloc", "java", "BRONZE"),
                new Enigme(12, "Quelle est la valeur par défaut d’un int en Java ?", "0", "java", "BRONZE"),
                new Enigme(13, "Comment convertir un String en int en Java ?", "Integer.parseInt", "java", "OR"),
                new Enigme(14, "Quelle exception est levée lors d’une division par zéro en Java ?", "ArithmeticException", "java", "OR"),
                new Enigme(15, "Quel est le mot-clé pour hériter d'une classe ?", "extends", "java", "OR"),
                new Enigme(16, "Qu'est-ce qu'une interface en Java ?", "Contrat de méthodes", "java", "PLATINE"),
                new Enigme(17, "Comment implémente-t-on une interface en Java ?", "implements", "java", "PLATINE"),
                new Enigme(18, "Comment rendre une classe non modifiable ?", "final", "java", "PLATINE"),
                new Enigme(19, "À quoi sert le mot-clé 'this' ?", "Référence à l'objet courant", "java", "PLATINE"),
                new Enigme(20, "Que signifie 'polymorphisme' ?", "Objet avec plusieurs formes", "java", "DIAMANT"),

                new Enigme(21, "Quel mot-clé exécute une requête conditionnelle en SQL ?", "WHERE", "sql", "BRONZE"),
                new Enigme(22, "Comment trier les résultats en SQL ?", "ORDER BY", "sql", "OR"),
                new Enigme(23, "Quelle clause compte les lignes ?", "COUNT", "sql", "OR"),
                new Enigme(24, "Comment joindre deux tables ?", "JOIN", "sql", "OR"),
                new Enigme(25, "Comment éviter les doublons ?", "DISTINCT", "sql", "OR"),
                new Enigme(26, "Quelle commande supprime une table ?", "DROP", "sql", "PLATINE"),
                new Enigme(27, "Comment renommer une colonne en SQL ?", "AS", "sql", "PLATINE"),
                new Enigme(28, "Quelle commande modifie une table ?", "ALTER", "sql", "PLATINE"),
                new Enigme(29, "Comment créer une clé primaire ?", "PRIMARY KEY", "sql", "DIAMANT"),
                new Enigme(30, "Comment créer une relation entre deux tables ?", "FOREIGN KEY", "sql", "DIAMANT"),

                new Enigme(31, "Quel fichier configure Spring Boot ?", "application.properties", "spring boot", "BRONZE"),
                new Enigme(32, "Annotation pour une classe contrôleur Spring ?", "@Controller", "spring boot", "BRONZE"),
                new Enigme(33, "Annotation pour injecter un bean ?", "@Autowired", "spring boot", "OR"),
                new Enigme(34, "Annotation pour mapper une méthode GET ?", "@GetMapping", "spring boot", "OR"),
                new Enigme(35, "Quel est le rôle de @Service ?", "Marquer une classe métier", "spring boot", "OR"),
                new Enigme(36, "Quelle annotation configure une classe de config ?", "@Configuration", "spring boot", "PLATINE"),
                new Enigme(37, "Comment définir un bean ?", "@Bean", "spring boot", "PLATINE"),
                new Enigme(38, "Comment lire une propriété ?", "@Value", "spring boot", "PLATINE"),
                new Enigme(39, "Que fait Spring Security ?", "Gère l'authentification", "spring boot", "DIAMANT"),
                new Enigme(40, "Annotation pour une entité JPA ?", "@Entity", "spring boot", "DIAMANT"),

                new Enigme(41, "Comment déclarer une variable en JavaScript ?", "let", "js", "BRONZE"),
                new Enigme(42, "Quelle fonction affiche une alerte ?", "alert", "js", "BRONZE"),
                new Enigme(43, "Comment écrire une fonction fléchée ?", "() => {}", "js", "OR"),
                new Enigme(44, "Quel opérateur compare sans type coercion ?", "===", "js", "OR"),
                new Enigme(45, "Que retourne typeof NaN ?", "number", "js", "PLATINE"),
                new Enigme(46, "Comment vérifier si une variable est undefined ?", "typeof x === 'undefined'", "js", "PLATINE"),
                new Enigme(47, "Que fait setTimeout ?", "Exécute après un délai", "js", "PLATINE"),
                new Enigme(48, "Quel est le résultat de '5' + 1 ?", "51", "js", "PLATINE"),
                new Enigme(49, "Comment faire une promesse ?", "new Promise()", "js", "DIAMANT"),
                new Enigme(50, "À quoi sert async/await ?", "Gérer les promesses", "js", "DIAMANT"),

                new Enigme(51, "Comment afficher une variable dans Vue.js ?", "{{ variable }}", "vue.js", "BRONZE"),
                new Enigme(52, "Quelle directive boucle sur une liste ?", "v-for", "vue.js", "OR"),
                new Enigme(53, "Comment cacher un élément conditionnellement ?", "v-if", "vue.js", "OR"),
                new Enigme(54, "Que fait v-model ?", "Lie l’entrée à une donnée", "vue.js", "PLATINE"),
                new Enigme(55, "Comment écouter un événement ?", "@click", "vue.js", "PLATINE"),
                new Enigme(56, "À quoi sert 'methods' dans un composant ?", "Définir des fonctions", "vue.js", "PLATINE"),
                new Enigme(57, "Comment passer une prop à un composant enfant ?", ":prop", "vue.js", "PLATINE"),
                new Enigme(58, "Quelle méthode monte le composant ?", "mounted()", "vue.js", "DIAMANT"),
                new Enigme(59, "Comment émettre un événement au parent ?", "$emit", "vue.js", "DIAMANT"),
                new Enigme(60, "Quelle est la fonction de watch ?", "Surveiller une variable", "vue.js", "DIAMANT"),

        new Enigme(61, "Quel type de boucle existe en Java ?", "for", "java", "BRONZE"),
                new Enigme(62, "Quel est le mot-clé pour une classe abstraite ?", "abstract", "java", "OR"),
                new Enigme(63, "Comment créer un thread en Java ?", "Thread", "java", "PLATINE"),
                new Enigme(64, "À quoi sert 'synchronized' ?", "Accès exclusif", "java", "DIAMANT"),
                new Enigme(65, "Que fait la méthode finalize() ?", "Nettoyage avant GC", "java", "DIAMANT"),

                new Enigme(66, "Que retourne une jointure INNER JOIN ?", "Lignes correspondantes", "sql", "BRONZE"),
                new Enigme(67, "Quel mot-clé insère des données ?", "INSERT", "sql", "BRONZE"),
                new Enigme(68, "Comment mettre à jour des données ?", "UPDATE", "sql", "OR"),
                new Enigme(69, "Comment effacer des lignes ?", "DELETE", "sql", "OR"),
                new Enigme(70, "Quelle clause limite le nombre de résultats ?", "LIMIT", "sql", "PLATINE"),

                new Enigme(71, "Comment mapper une méthode POST ?", "@PostMapping", "spring boot", "BRONZE"),
                new Enigme(72, "Comment lancer une appli Spring Boot ?", "SpringApplication.run", "spring boot", "BRONZE"),
                new Enigme(73, "Comment capturer un paramètre d’URL ?", "@PathVariable", "spring boot", "OR"),
                new Enigme(74, "Annotation pour paramètre de requête ?", "@RequestParam", "spring boot", "OR"),
                new Enigme(75, "Que fait @RequestBody ?", "Lier un corps JSON à un objet", "spring boot", "PLATINE"),

                new Enigme(76, "Quelle méthode ajoute un élément dans un tableau JS ?", "push", "js", "BRONZE"),
                new Enigme(77, "Comment vérifier si un tableau contient un élément ?", "includes", "js", "OR"),
                new Enigme(78, "Comment filtrer un tableau ?", "filter", "js", "OR"),
                new Enigme(79, "Comment transformer un tableau en une chaîne ?", "join", "js", "PLATINE"),
                new Enigme(80, "Que fait 'map' sur un tableau ?", "Transforme chaque élément", "js", "PLATINE"),

                new Enigme(81, "Comment appliquer une classe CSS conditionnellement ?", ":class", "vue.js", "OR"),
                new Enigme(82, "Quelle directive permet de répéter du contenu ?", "v-for", "vue.js", "BRONZE"),
                new Enigme(83, "Comment accéder à une prop dans le template ?", "this.propName", "vue.js", "PLATINE"),
                new Enigme(84, "Que retourne computed ?", "Valeur calculée", "vue.js", "DIAMANT"),
                new Enigme(85, "Comment exécuter du code au montage ?", "mounted()", "vue.js", "DIAMANT"),

                new Enigme(86, "Quel est le rôle de @Repository en Spring ?", "Accès aux données", "spring boot", "OR"),
                new Enigme(87, "Comment récupérer une liste depuis la BDD ?", "findAll", "spring boot", "PLATINE"),
                new Enigme(88, "À quoi sert @Transactional ?", "Gérer la transaction", "spring boot", "DIAMANT"),
                new Enigme(89, "Comment faire un test unitaire avec Spring ?", "@SpringBootTest", "spring boot", "DIAMANT"),
                new Enigme(90, "Quel starter Spring intègre JPA ?", "spring-boot-starter-data-jpa", "spring boot", "PLATINE"),

                new Enigme(91, "Comment vérifier le type d'une variable JS ?", "typeof", "js", "BRONZE"),
                new Enigme(92, "Quel opérateur logique inverse une condition ?", "!", "js", "BRONZE"),
                new Enigme(93, "Comment empêcher un formulaire de recharger la page ?", "event.preventDefault()", "js", "PLATINE"),
                new Enigme(94, "Quelle méthode JS détecte une erreur dans un bloc ?", "try/catch", "js", "OR"),
                new Enigme(95, "Quelle est la différence entre == et === ?", "Comparaison stricte", "js", "OR"),

                new Enigme(96, "Comment créer un composant dans Vue 3 ?", "defineComponent", "vue.js", "PLATINE"),
                new Enigme(97, "Quel hook remplace 'created' dans Composition API ?", "setup()", "vue.js", "DIAMANT"),
                new Enigme(98, "Comment importer un composant enfant ?", "import", "vue.js", "OR"),
                new Enigme(99, "Comment utiliser une prop dans Composition API ?", "defineProps", "vue.js", "DIAMANT"),
                new Enigme(100, "Comment observer une donnée réactive ?", "watch", "vue.js", "DIAMANT")
        );
    }
}

