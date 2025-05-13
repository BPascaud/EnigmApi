package com.proj.proj.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
@Table(name = "Enigme")
public class Enigme {
    @Id
    private int id;
    private String question;
    private String reponse;
    private String competence;
    private String niveau;
}