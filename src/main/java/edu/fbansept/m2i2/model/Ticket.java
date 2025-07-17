package edu.fbansept.m2i2.model;

import com.fasterxml.jackson.annotation.JsonView;
import edu.fbansept.m2i2.view.TicketView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Ticket {

    public interface add {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(TicketView.class)
    protected Integer id;

    @NotBlank(groups = {add.class})
    @JsonView(TicketView.class)
    private String titre;

    @NotBlank(groups = {add.class})
    @JsonView(TicketView.class)
    private String description;

    @JsonView(TicketView.class)
    private boolean resolu = false;

    @ManyToOne(optional = false)
    @JsonView(TicketView.class)
    private Utilisateur auteur;

    @ManyToOne
    @JsonView(TicketView.class)
    private Utilisateur resolveur;

    @ManyToOne(optional = false)
    @JsonView(TicketView.class)
    private Priorite priorite;

    @ManyToMany
    @JoinTable(
            name = "categorie_ticket",
            joinColumns = @JoinColumn(name = "ticket_id"),
            inverseJoinColumns = @JoinColumn(name = "categorie_id"))
    @JsonView(TicketView.class)
    protected List<Categorie> categories = new ArrayList<>();
}