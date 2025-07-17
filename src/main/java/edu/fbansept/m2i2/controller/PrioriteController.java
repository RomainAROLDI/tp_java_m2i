package edu.fbansept.m2i2.controller;

import com.fasterxml.jackson.annotation.JsonView;
import edu.fbansept.m2i2.dao.PrioriteDao;
import edu.fbansept.m2i2.model.Priorite;
import edu.fbansept.m2i2.security.IsAdministrateur;
import edu.fbansept.m2i2.view.PrioriteView;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/priorite")
public class PrioriteController {

    protected PrioriteDao prioriteDao;

    @GetMapping("/liste")
    @JsonView(PrioriteView.class)
    public List<Priorite> getAll() {
        return prioriteDao.findAll();
    }

    @GetMapping("/{id}")
    @JsonView(PrioriteView.class)
    public ResponseEntity<Priorite> get(@PathVariable int id) {

        Optional<Priorite> prioriteOptional = prioriteDao.findById(id);

        return prioriteOptional.map(priorite -> new ResponseEntity<>(priorite, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @PostMapping
    @IsAdministrateur
    @JsonView(PrioriteView.class)
    public ResponseEntity<Priorite> add(@RequestBody @Valid Priorite priorite) {

        prioriteDao.save(priorite);

        return new ResponseEntity<>(priorite, HttpStatus.CREATED);

    }

    @DeleteMapping("/{id}")
    @IsAdministrateur
    public ResponseEntity<?> delete(
            @PathVariable int id
    ) {
        Optional<Priorite> prioriteOptional = prioriteDao.findById(id);

        if (prioriteOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        prioriteDao.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    @JsonView(PrioriteView.class)
    @IsAdministrateur
    public ResponseEntity<?> update(
            @PathVariable int id,
            @RequestBody @Valid Priorite priorite) {

        priorite.setId(id);

        Optional<Priorite> prioriteOptional = prioriteDao.findById(id);

        if (prioriteOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        prioriteDao.save(priorite);

        return new ResponseEntity<>(priorite, HttpStatus.OK);
    }
}
