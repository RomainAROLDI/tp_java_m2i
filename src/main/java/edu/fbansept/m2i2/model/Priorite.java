package edu.fbansept.m2i2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Priorite {
    @Id @GeneratedValue
    private Long id;
    private String nom;
}