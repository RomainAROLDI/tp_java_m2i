package edu.fbansept.m2i2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import edu.fbansept.m2i2.view.EtiquetteView;
import edu.fbansept.m2i2.view.ProduitView;
import edu.fbansept.m2i2.view.VendeurView;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(ProduitView.class)
    protected Integer id;

    @NotBlank
    @Column(unique=true , nullable=false)
    @JsonView({VendeurView.class, ProduitView.class, EtiquetteView.class})
    protected String nom;

    @DecimalMin(value = "0.01")
    @Column(columnDefinition = "DECIMAL(6,2)")
    @JsonView({VendeurView.class, ProduitView.class})
    protected float prix;

    @ManyToOne
    @JsonView(ProduitView.class)
    protected Utilisateur vendeur;

    @ManyToMany
    @JoinTable(
            name = "etiquette_produit",
            joinColumns = @JoinColumn(name = "produit_id"),
            inverseJoinColumns = @JoinColumn(name = "etiquette_id"))
    @JsonView(ProduitView.class)
    protected List<Etiquette> etiquettes = new ArrayList<>();

}

