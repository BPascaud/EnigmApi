package com.proj.proj.Repo;

import com.proj.proj.CustomProperties;
import com.proj.proj.Model.*;
import com.proj.proj.Service.QuestionnaireService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Component
public class ProjProxy {

    @Autowired
    private CustomProperties props;

    @Autowired
    private RestTemplate restTemplate;

    public Questionnaire creerQuestionnaire(Criteres criteres) {
        System.out.println("-----------");
        System.out.println("créerQuestionnaire");
        System.out.println("Compétences : "+criteres.getCompetences()+"| Niveaux : "+criteres.getNiveaux());
        String url = props.getApiUrl() + "/questionnaires/creer";
        try {
            ResponseEntity<Questionnaire> response = restTemplate.postForEntity(url, criteres, Questionnaire.class);
            return response.getBody();
        } catch (Exception e) {
            log.error("Erreur lors de la création du questionnaire : (pas assez d'énigmes){}", e.getMessage());
            return null;
        }
    }

    public List<String> envoyerLien(Envoi envoi) {
        System.out.println("-----------");
        System.out.println("envoiSansUUID");
        String url = props.getApiUrl() + "/questionnaires/envoyer";
        try {
            ResponseEntity<List<String>> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    new HttpEntity<>(envoi),
                    new ParameterizedTypeReference<List<String>>() {}
            );
            return response.getBody();
        } catch (Exception e) {
            log.error("Erreur lors de l'envoi des liens : {}", e.getMessage());
            return List.of();
        }
    }

    public List<String> envoyerLienUUID(Envoi envoi) {
        System.out.println("-----------");
        System.out.println("envoiUUID");
        System.out.println("idQuest : "+envoi.getIdQuest()+"| Nom : "+envoi.getNom());
        String url = props.getApiUrl() + "/questionnaires/envoyerUUID";
        try {
            ResponseEntity<List<String>> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    new HttpEntity<>(envoi),
                    new ParameterizedTypeReference<List<String>>() {}
            );
            return response.getBody();
        } catch (Exception e) {
            log.error("Erreur lors de l'envoi des liens UUID : {}", e.getMessage());
            return List.of();
        }
    }

    public int nbQuest(Criteres criteres) {
        System.out.println("-----------");
        System.out.println("nbQuest");
        String url = props.getApiUrl() + "/questionnaires/nbQuest";
        try {
            ResponseEntity<Integer> response = restTemplate.postForEntity(url, criteres, Integer.class);
            return response.getBody() != null ? response.getBody() : 0;
        } catch (Exception e) {
            log.error("Erreur lors du calcul du nombre de questionnaires possibles : {}", e.getMessage());
            return 0;
        }
    }


}
