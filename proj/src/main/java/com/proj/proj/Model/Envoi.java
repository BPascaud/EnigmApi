package com.proj.proj.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "Envoi")
public class Envoi {
    private Integer idQuest;
    private List<Integer> idCollab;
    private List<String> nom;
    @Id
    private Integer id;

}
