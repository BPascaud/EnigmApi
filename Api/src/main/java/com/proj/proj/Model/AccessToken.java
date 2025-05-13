package com.proj.proj.Model;

import lombok.Getter;

@Getter
public class AccessToken {
    private String token;
    private Questionnaire questionnaire;
    private Person collaborateur;

    public AccessToken(String token, Questionnaire questionnaire, Person collaborateur) {
        this.token = token;
        this.questionnaire = questionnaire;
        this.collaborateur = collaborateur;
    }

}
