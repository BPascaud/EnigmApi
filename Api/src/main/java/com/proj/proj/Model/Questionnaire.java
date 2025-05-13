package com.proj.proj.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Entity
@Table(name = "Questionnaire")
public class Questionnaire {
    @Id
    private int id;
    @OneToMany
    private List<Enigme> enigmes;


    public Questionnaire() {

    }
}
