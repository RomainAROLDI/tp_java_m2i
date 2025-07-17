package edu.fbansept.m2i2.model;

import com.fasterxml.jackson.annotation.JsonView;
import edu.fbansept.m2i2.view.EtiquetteView;
import edu.fbansept.m2i2.view.ProduitView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Etiquette {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @NotBlank
    @Column(unique=true , nullable=false)
    @JsonView({ProduitView.class,EtiquetteView.class})
    protected String nom;

//    @ManyToMany(mappedBy = "etiquettes")
//    @JsonView(EtiquetteView.class)
//    protected List<Produit> produits;

}

