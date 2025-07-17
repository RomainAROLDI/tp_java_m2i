package edu.fbansept.m2i2.model;

import com.fasterxml.jackson.annotation.JsonView;
import edu.fbansept.m2i2.view.CategorieView;
import edu.fbansept.m2i2.view.TicketView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Categorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({TicketView.class, CategorieView.class})
    protected Integer id;

    @NotBlank
    @Column(unique=true , nullable=false)
    @JsonView({TicketView.class, CategorieView.class})
    private String nom;
}