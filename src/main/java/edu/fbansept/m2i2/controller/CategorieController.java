package edu.fbansept.m2i2.controller;

import com.fasterxml.jackson.annotation.JsonView;
import edu.fbansept.m2i2.dao.CategorieDao;
import edu.fbansept.m2i2.model.Categorie;
import edu.fbansept.m2i2.security.IsAdministrateur;
import edu.fbansept.m2i2.view.CategorieView;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categorie")
public class CategorieController {

    protected CategorieDao categorieDao;

    @GetMapping("/liste")
    @JsonView(CategorieView.class)
    public List<Categorie> getAll() {
        return categorieDao.findAll();
    }

    @GetMapping("/{id}")
    @JsonView(CategorieView.class)
    public ResponseEntity<Categorie> get(@PathVariable int id) {

        Optional<Categorie> categorieOptional = categorieDao.findById(id);

        return categorieOptional.map(categorie -> new ResponseEntity<>(categorie, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @PostMapping
    @IsAdministrateur
    @JsonView(CategorieView.class)
    public ResponseEntity<Categorie> add(@RequestBody @Valid Categorie categorie) {

        categorieDao.save(categorie);

        return new ResponseEntity<>(categorie, HttpStatus.CREATED);

    }

    @DeleteMapping("/{id}")
    @IsAdministrateur
    public ResponseEntity<?> delete(
            @PathVariable int id
    ) {
        Optional<Categorie> categorieOptional = categorieDao.findById(id);

        if (categorieOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        categorieDao.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    @JsonView(CategorieView.class)
    @IsAdministrateur
    public ResponseEntity<?> update(
            @PathVariable int id,
            @RequestBody @Valid Categorie categorie) {

        categorie.setId(id);

        Optional<Categorie> categorieOptional = categorieDao.findById(id);

        if (categorieOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        categorieDao.save(categorie);

        return new ResponseEntity<>(categorie, HttpStatus.OK);
    }
}
