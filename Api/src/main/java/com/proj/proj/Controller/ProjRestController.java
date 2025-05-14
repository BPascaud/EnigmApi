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



    private final List<Enigme> enigmes = TestData.getFakeEnigmes();



    @RequestMapping("/")
    public String afficherAccueil() {
        return "accueil";
    }

    @PostMapping("/creer")//en partant du principe qu'il faut 10 enigmes pour un questionnaire
    public Questionnaire creerQuestionnaire(@RequestBody Criteres critere) {
        System.out.println("-----------");
        System.out.println("Creer Questionnaire");
        System.out.println("Criteres Compétences : " + critere.getCompetences()+"| Critéres niveaux :"+critere.getNiveaux());
        List<String> ordreNiveaux = Arrays.asList("diamant", "platine", "or", "bronze");

        List<String> niveauxTries = ordreNiveaux.stream()
                .filter(niv -> critere.getNiveaux().stream()
                        .map(String::toLowerCase)
                        .collect(Collectors.toList())
                        .contains(niv))
                .collect(Collectors.toList());

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
        System.out.println("nb Enigmes: " + enigmes.size());
        System.out.println("nb EnigmesFiltrées : "+enigmesFiltrees.size());
        System.out.println(enigmesFiltrees);
        //filtre minimum 10 questions(optionel)
        if(enigmesFiltrees.size() <10){
            throw new RuntimeException("Pas assez d'enigmes pour créer un questionnaire !");
        }else{
            return questionnaireService.creerQuestionnaire(enigmesFiltrees);
        }

    }


    @PostMapping("/envoyer")
    public List<String> envoyerLien(@RequestBody Envoi envoi) {
        System.out.println("-----------");
        System.out.println(envoi);
        List<String> urls = new ArrayList<>();
        System.out.println("envoyer");

        Optional<Questionnaire> optionalQuestionnaire = questionnaireService.getQuestionnaireById(envoi.getIdQuest());
        if (optionalQuestionnaire.isEmpty()) {
            throw new RuntimeException("Questionnaire non trouvé.");
        }

        Questionnaire questionnaire = optionalQuestionnaire.get();

        for (String nom : envoi.getNoms()) {
            Person collaborateur = personService.getPersonByName(nom);
            if (collaborateur == null) {
                continue;
            }

            int collabId = collaborateur.getId();
            String email = collaborateur.getMail();
            if (email == null) {
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
        System.out.println("-----------");
        System.out.println("envoiUUID");
        List<String> urls = new ArrayList<>();

        Questionnaire questionnaire = questionnaireService.getQuestionnaireById(envoi.getIdQuest())
                .orElse(null);

        if (questionnaire == null) {
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
            }
        }
        return urls;
    }

    @PostMapping("/nbQuest")
    public int nombreDeQuestionnairesPossibles(@RequestBody Criteres critere) {
        System.out.println("-----------");
        System.out.println("nbQuest");

        List<Enigme> enigmesFiltrees = enigmes.stream()
                .filter(e -> critere.getCompetences().contains(e.getCompetence()))
                .filter(e -> critere.getNiveaux().contains(e.getNiveau()))
                .collect(Collectors.toList());
        System.out.println(enigmesFiltrees);

        System.out.println("nb enigmes filtrées :"+enigmesFiltrees.size());

        int nbQuestionnaires = enigmesFiltrees.size() / 10;

        System.out.println("Nombre de questionnaires possibles : " + nbQuestionnaires);
        return nbQuestionnaires;
    }
    @PostMapping("/creerEnvoyer")
    public Questionnaire creerEnvoyerQuestionnaire(@RequestBody CreerEnvoyerRequest request) {
        System.out.println("-----------");
        System.out.println("CreerEnvoyerQuestionnaire");
        System.out.println(request.getCriteres());
        Questionnaire q=creerQuestionnaire(request.getCriteres());
        envoyerLienUUID(request.getEnvoi());
        return q;
    }
    @GetMapping("/access/{token}")
    public ModelAndView afficherQuestionnaireParToken(@PathVariable String token) {
        System.out.println("--------");
        System.out.println("access token");
        AccessToken access = tokenService.getToken(token);
        if (access != null) {
            ModelAndView modelAndView = new ModelAndView("questionnaireView");
            modelAndView.addObject("questionnaire", access.getQuestionnaire());
            modelAndView.addObject("collaborator", access.getCollaborateur());
            return modelAndView;
        } else {
            return new ModelAndView("errorPage");
        }
    }
}