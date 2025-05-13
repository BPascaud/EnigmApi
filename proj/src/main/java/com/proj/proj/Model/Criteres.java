package com.proj.proj.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "Criteres")
public class Criteres {
    private List<String> competences;
    private List<String> niveaux;
    private String nom;
    @Id
    private Integer id;

}
