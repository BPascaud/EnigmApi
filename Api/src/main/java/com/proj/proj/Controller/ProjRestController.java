package com.proj.proj.Controller;

import com.proj.proj.Model.*;
import com.proj.proj.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/questionnaires")
public class ProjRestController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private PersonService personService;

    @Autowired
    private QuestionnaireService questionnaireService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    public ErrorService errorService;


    private final List<Enigme> enigmes = TestData.getFakeEnigmes();

    private final Map<String, AccessToken> tokensMap = new HashMap<>();


    @RequestMapping("/")
    public String afficherAccueil() {
        return "accueil";
    }

    @PostMapping("/creer")//en partant du principe qu'il faut 5 enigmes pour un questionnaire
    public Questionnaire creerQuestionnaire(@RequestBody Criteres critere) {
        List<String> ordreNiveaux = Arrays.asList("diamant", "platine", "or", "bronze");

        // Met les niveaux reçus en minuscules
        List<String> niveauxTries = ordreNiveaux.stream()
                .filter(niv -> critere.getNiveaux().stream()
                        .map(String::toLowerCase)
                        .collect(Collectors.toList())
                        .contains(niv))
                .collect(Collectors.toList());

        // Met les compétences reçues en minuscules
        List<String> competences = critere.getCompetences().stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());

        List<Enigme> enigmesFiltrees = new ArrayList<>();

        //boucle d'abord sur les niveaux puis sur les compétences
        for (String niveau : niveauxTries) {
            for (String competence : competences) {
                List<Enigme> enigmesPourCompEtNiv = enigmes.stream()
                        .filter(e -> e.getCompetence().equalsIgnoreCase(competence))
                        .filter(e -> e.getNiveau().equalsIgnoreCase(niveau))
                        .collect(Collectors.toList());

                for (Enigme e : enigmesPourCompEtNiv) {
                    if (enigmesFiltrees.size() < 10) {
                        enigmesFiltrees.add(e);
                    } else {
                        break;
                    }
                }
                if (enigmesFiltrees.size() >= 10) break; //quitter la boucle compétence
            }
            if (enigmesFiltrees.size() >= 10) break;//quitte la boucle niveau
        }
        System.out.println(enigmesFiltrees);
        System.out.println("nbEnigmesFiltrées : "+enigmesFiltrees.size());
        //filtre minimum 5 questions(optionel)
        if(enigmesFiltrees.size() <10){
            throw new RuntimeException("Pas assez d'enigmes pour créer un questionnaire !");
        }else{
            return questionnaireService.creerQuestionnaire(enigmesFiltrees);
        }

    }


    @PostMapping("/envoyer")
    public List<String> envoyerLien(@RequestBody Envoi envoi) {
        System.out.println(envoi);
        List<String> urls = new ArrayList<>();
        System.out.println("envoyer");

        Optional<Questionnaire> optionalQuestionnaire = questionnaireService.getQuestionnaireById(envoi.getIdQuest());
        if (optionalQuestionnaire.isEmpty()) {
            errorService.setError("Questionnaire non trouvé");
            throw new RuntimeException("Questionnaire non trouvé.");
        }

        Questionnaire questionnaire = optionalQuestionnaire.get();

        for (String nom : envoi.getNoms()) {
            Person collaborateur = personService.getPersonByName(nom);
            if (collaborateur == null) {
                errorService.setError("Collaborateur " + nom + " non trouvé");
                continue;
            }

            int collabId = collaborateur.getId();
            String email = collaborateur.getMail();
            if (email == null) {
                errorService.setError("Email pour le collaborateur " + nom + " non trouvé");
                continue;
            }

            String url = "http://localhost:8080/questionnaires/" + questionnaire.getId() + "/" + collabId;
            urls.add(url);
            emailService.sendEmail(email, "Questionnaire", url);
        }

        return urls;
    }

    @PostMapping("/envoyerUUID")
    public List<String> envoyerLienUUID(@RequestBody Envoi envoi) {
        System.out.println("envoiUUID");
        List<String> urls = new ArrayList<>();

        Questionnaire questionnaire = questionnaireService.getQuestionnaireById(envoi.getIdQuest())
                .orElse(null);

        if (questionnaire == null) {
            errorService.setError("Questionnaire non trouvé");
            throw new RuntimeException("Questionnaire non trouvé.");
        }

        for (String nomCollab : envoi.getNom()) {
            System.out.println("nom : " + nomCollab);
            Person collaborateur = personService.getPersonByName(nomCollab);
            if (collaborateur != null) {
                String token = UUID.randomUUID().toString();
                AccessToken accessToken = new AccessToken(token, questionnaire, collaborateur);
                tokenService.storeToken(token, accessToken);

                String url = "http://localhost:8081/questionnaires/access/" + token;
                urls.add(url);

                String email = collaborateur.getMail();
                emailService.sendEmail(email, "Votre questionnaire", url);
            } else {
                errorService.setError("Collaborateur " + nomCollab + " non trouvé");
            }
        }
        return urls;
    }

    @PostMapping("/nbQuest")
    public int nombreDeQuestionnairesPossibles(@RequestBody Criteres critere) {
        System.out.println("nbQuest");

        List<Enigme> enigmesFiltrees = enigmes.stream()
                .filter(e -> critere.getCompetences().contains(e.getCompetence()))
                .filter(e -> critere.getNiveaux().contains(e.getNiveau()))
                .collect(Collectors.toList());
        System.out.println(enigmesFiltrees);

        System.out.println("nb enigmes filtrées :"+enigmesFiltrees.size());

        int nbQuestionnaires = enigmesFiltrees.size() / 5;

        System.out.println("Nombre de questionnaires possibles : " + nbQuestionnaires);
        return nbQuestionnaires;
    }
    @PostMapping("/creerEnvoyer")
    public Questionnaire creerEnvoyerQuestionnaire(@RequestBody CreerEnvoyerRequest request) {
        System.out.println(request.getCriteres());
        Questionnaire q=creerQuestionnaire(request.getCriteres());
        envoyerLienUUID(request.getEnvoi());
        return q;
    }
}