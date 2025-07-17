package edu.fbansept.m2i2.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Ticket {

    @Id @GeneratedValue
    private Long id;

    @NotBlank
    private String titre;

    private String description;

    private boolean resolu = false;

    @ManyToOne
    private Utilisateur auteur;

    @ManyToOne
    private Priorite priorite;

    @ManyToOne
    private Categorie categorie;
}