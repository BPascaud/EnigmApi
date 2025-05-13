package com.proj.proj.Controller;

import com.proj.proj.Model.*;
import com.proj.proj.Repo.ProjProxy;
import com.proj.proj.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/questionnaires")
public class ProjController {

    @Autowired
    private PersonService personService;

    @Autowired
    private QuestionnaireService questionnaireService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ProjProxy projProxy;

    @Autowired
    private ErrorService errorService;

    @Autowired
    private EmailService emailService;

    private final List<Questionnaire> listeQuest = new ArrayList<>();

    @GetMapping(" ")
    public String afficherAccueilVide() {
        return "accueil";
    }

    @GetMapping("/")
    public String afficherAccueil() {
        return "accueil";
    }

    @GetMapping("/creer")
    public String afficherQuestionnaireForm() {
        return "Creation";
    }



    @PostMapping("/creer")
    public String creerQuestionnaire(@ModelAttribute Criteres criteres) {

        projProxy.creerQuestionnaire(criteres);

        return "redirect:/questionnaires/creer";
    }


    @GetMapping("/envoyer")
    public String afficherEnvoiForm() {
        return "Envoi";
    }

    @PostMapping("/envoyer")
    public ResponseEntity<?> envoyerLien(@RequestBody Envoi envoi) {
        try {
            if (envoi.getIdQuest() == null || envoi.getIdCollab() == null || envoi.getIdCollab().isEmpty()) {
                return ResponseEntity.badRequest().body("Les données sont incorrectes.");
            }

            List<String> liensEnvoyés = projProxy.envoyerLien(envoi);

            if (liensEnvoyés.isEmpty()) {
                return ResponseEntity.status(500).body("Aucun lien généré. Vérifier les adresses ou critères.");
            }

            return ResponseEntity.ok(liensEnvoyés);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erreur interne : " + e.getMessage());
        }
    }



    @GetMapping("/envoyerUUID")
    public String afficherEnvoiUUIDForm() {
        return "EnvoiUUID";
    }

    @PostMapping("/envoyerUUID")
    public ResponseEntity<?> envoyerLienUUID(@RequestBody Envoi envoi) {
        System.out.println("envoiUUID");

        List<String> urls=projProxy.envoyerLienUUID(envoi);

        try {

            if (envoi.getIdQuest() == null || envoi.getIdCollab() == null || envoi.getIdCollab().isEmpty()) {
                return ResponseEntity.badRequest().body("Les données sont incorrectes.");
            }

            if (urls.isEmpty()) {
                return ResponseEntity.status(500).body("Aucun lien généré. Vérifier les adresses ou critères.");
            }
            return ResponseEntity.ok(urls);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erreur interne : " + e.getMessage());
        }
    }

    @GetMapping("/nbQuest")
    public String afficherNbQuestForm() {
        return "nbQuest";
    }

    @PostMapping("/nbQuest")
    public String affichernbQuest(@ModelAttribute Criteres criteres, Model model) {
        int nbQuest = projProxy.nbQuest(criteres);
        System.out.println("Nombre de questionnaires : " + nbQuest);
        model.addAttribute("nbQuest", nbQuest);
        return "nbQuest";
    }



    @GetMapping("/{questionnaireId}/{collaboratorId}")
    public ModelAndView afficherQuestionnaire(@PathVariable int questionnaireId, @PathVariable int collaboratorId) {
        System.out.println(questionnaireId);
        System.out.println(questionnaireService.getQuestionnaireById(questionnaireId));
        System.out.println(collaboratorId);
        Questionnaire questionnaire = questionnaireService.getQuestionnaireById(questionnaireId)
                .orElse(null);
        System.out.println(questionnaire);

        Person collaborateur = personService.getPersonById(collaboratorId);

        if (questionnaire != null && collaborateur != null) {
            ModelAndView modelAndView = new ModelAndView("questionnaireView");
            modelAndView.addObject("questionnaire", questionnaire);
            modelAndView.addObject("collaborator", collaborateur);
            return modelAndView;
        } else {
            return new ModelAndView("errorPage");
        }
    }


    @GetMapping("/access/{token}")
    public ModelAndView afficherQuestionnaireParToken(@PathVariable String token) {
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
    @GetMapping("/creerEnvoyer")
    public String afficherEnvoyerQuestionnaireForm() {
        return "CreationEnvoi";
    }
    @PostMapping("/creerEnvoyer")
    public ResponseEntity<?> creerEnvoyer(@RequestBody CreerEnvoyerRequest creerEnvoyerRequest) {

        Criteres criteres = creerEnvoyerRequest.getCriteres();
        Envoi envoi = creerEnvoyerRequest.getEnvoi();

        try {
            Questionnaire questionnaire = projProxy.creerQuestionnaire(criteres);
            envoi.setIdQuest(questionnaire.getId());
            List<String> liensEnvoyes = projProxy.envoyerLienUUID(envoi);

            return ResponseEntity.ok().body(liensEnvoyes);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erreur : " + e.getMessage());
        }
    }






}
