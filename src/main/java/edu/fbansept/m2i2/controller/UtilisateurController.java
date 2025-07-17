package edu.fbansept.m2i2.controller;

import com.fasterxml.jackson.annotation.JsonView;
import edu.fbansept.m2i2.dao.UtilisateurDao;
import edu.fbansept.m2i2.model.Produit;
import edu.fbansept.m2i2.model.Utilisateur;
import edu.fbansept.m2i2.security.IsAdministrateur;
import edu.fbansept.m2i2.security.IsVendeur;
import edu.fbansept.m2i2.view.VendeurView;
import edu.fbansept.m2i2.view.VendeurWithEmailView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/utilisateur")
@Tag(name = "CRUD utilisateur", description = "Permet de manipuler l'entité utilisateur")
@IsAdministrateur
public class UtilisateurController {

    @Autowired
    protected UtilisateurDao utilisateurDao;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @GetMapping("/liste")
    @JsonView(VendeurView.class)
    public List<Utilisateur> getAll() {
        return utilisateurDao.findAll();
    }

    @GetMapping("/{id}")
    @JsonView(VendeurWithEmailView.class)
    public ResponseEntity<Utilisateur> get(@PathVariable int id) {

        Optional<Utilisateur> utilisateurOptional = utilisateurDao.findById(id);

        if(utilisateurOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            //return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<>(utilisateurOptional.get(), HttpStatus.OK);
    }

    @PostMapping
    @Operation(
            summary = "Permet de persister un utilisateur",
            description = "Enregistre dans la base de donnée un utilisateur issu du JSON intégré au corp de la requête")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "L'utilisateur a été ajouté avec succès"),
            @ApiResponse(responseCode = "409", description = "L'opération a échouée car l'email est en doublon, ou le role absent"),
            @ApiResponse(responseCode = "400", description = "L'email ou le mot de passe est vide ou absent"),
    })
    public ResponseEntity<Utilisateur> add(
            @RequestBody @Validated(Utilisateur.add.class) Utilisateur utilisateurEnvoye) {

        utilisateurEnvoye.setPassword(passwordEncoder.encode(utilisateurEnvoye.getPassword()));

        utilisateurDao.save(utilisateurEnvoye);

        return new ResponseEntity<>(utilisateurEnvoye, HttpStatus.CREATED);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {

        Optional<Utilisateur> utilisateurOptional = utilisateurDao.findById(id);

        if(utilisateurOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        utilisateurDao.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable int id,
            @RequestBody @Validated(Utilisateur.update.class) Utilisateur utilisateurEnvoye) {

        utilisateurEnvoye.setId(id);

        Optional<Utilisateur> utilisateurOptional = utilisateurDao.findById(id);

        if(utilisateurOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        //pour forcer le fait de ne pas mettre à jour le mot de passe
        //on affecte à l'utilisateur a sauvegarder l'ancien mot de passe
        utilisateurEnvoye.setPassword(utilisateurOptional.get().getPassword());

        utilisateurDao.save(utilisateurEnvoye);

        return new ResponseEntity<>(utilisateurEnvoye, HttpStatus.OK);

    }

}
