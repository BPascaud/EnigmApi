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

    @GetMapping(" ")
    public String afficherAccueilVide() {
        return "accueil";
    }

    @GetMapping("/")
    public String afficherAccueil() {
        return "accueil";
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
