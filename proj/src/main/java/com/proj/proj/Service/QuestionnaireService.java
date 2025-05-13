package com.proj.proj.Service;

import com.proj.proj.Model.Enigme;
import com.proj.proj.Model.Questionnaire;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionnaireService {

    private final List<Questionnaire> listeQuest = new ArrayList<>();
    private int compteurId = 1;

    public Questionnaire creerQuestionnaire(List<Enigme> enigmesFiltrees) {
        Questionnaire q = new Questionnaire(compteurId++, enigmesFiltrees);
        listeQuest.add(q);
        return q;
    }

    public Optional<Questionnaire> getQuestionnaireById(int id) {
        System.out.println("Liste actuelle : " + listeQuest);
        return listeQuest.stream().filter(q -> q.getId() == id).findFirst();
    }

    public void ajouterQuestionnaire(Questionnaire questionnaire) {
        listeQuest.add(questionnaire);
    }

}
