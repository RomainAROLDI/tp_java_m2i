package edu.fbansept.m2i2.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;
import edu.fbansept.m2i2.view.ProduitView;
import edu.fbansept.m2i2.view.VendeurView;
import edu.fbansept.m2i2.view.VendeurWithEmailView;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Utilisateur {

    public interface add {
    }

    public interface update {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(VendeurView.class)
    protected Integer id;

    @Column(nullable = false, unique = true)
    @NotBlank(groups = {add.class, update.class})
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            groups = {add.class, update.class},
            message = "L'email est mal formé")
    @JsonView({VendeurWithEmailView.class, ProduitView.class})
    protected String email;

    @Column(nullable = false)
    @NotBlank(groups = {add.class})
    @JsonView()
    protected String password;

    @ManyToOne(optional = false)
    @JsonView()
    protected Role role;

    @OneToMany(mappedBy = "vendeur")
    @JsonView(VendeurView.class)
    protected List<Produit> produits = new ArrayList<>();

}
