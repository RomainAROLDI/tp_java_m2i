package edu.fbansept.m2i2.controller;

import com.fasterxml.jackson.annotation.JsonView;
import edu.fbansept.m2i2.dao.EtiquetteDao;
import edu.fbansept.m2i2.model.Etiquette;
import edu.fbansept.m2i2.security.IsAdministrateur;
import edu.fbansept.m2i2.security.IsVendeur;
import edu.fbansept.m2i2.view.EtiquetteView;
import edu.fbansept.m2i2.view.ProduitView;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/etiquette")
public class EtiquetteController {

    @Autowired
    protected EtiquetteDao etiquetteDao;

    @GetMapping("/liste")
    public List<Etiquette> getAll() {
        return etiquetteDao.findAll();
    }

    @GetMapping("/{id}")
    @JsonView(EtiquetteView.class)
    public ResponseEntity<Etiquette> get(@PathVariable int id) {

        Optional<Etiquette> etiquetteOptional = etiquetteDao.findById(id);

        if(etiquetteOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            //return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<>(etiquetteOptional.get(), HttpStatus.OK);
    }

    @PostMapping
    @IsAdministrateur
    public ResponseEntity<Etiquette> add(
            @RequestBody @Valid Etiquette etiquetteEnvoye) {

        etiquetteDao.save(etiquetteEnvoye);

        return new ResponseEntity<>(etiquetteEnvoye, HttpStatus.CREATED);

    }

    @DeleteMapping("/{id}")
    @IsAdministrateur
    public ResponseEntity<?> delete(@PathVariable int id) {

        Optional<Etiquette> etiquetteOptional = etiquetteDao.findById(id);

        if(etiquetteOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        etiquetteDao.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @PutMapping("/{id}")
    @IsAdministrateur
    public ResponseEntity<?> update(
            @PathVariable int id,
            @RequestBody @Valid Etiquette etiquetteEnvoye) {

        etiquetteEnvoye.setId(id);

        Optional<Etiquette> etiquetteOptional = etiquetteDao.findById(id);

        if(etiquetteOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        etiquetteDao.save(etiquetteEnvoye);

        return new ResponseEntity<>(etiquetteEnvoye, HttpStatus.OK);

    }

}
