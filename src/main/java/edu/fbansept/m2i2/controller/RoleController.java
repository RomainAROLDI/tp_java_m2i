package edu.fbansept.m2i2.controller;

import edu.fbansept.m2i2.dao.RoleDao;
import edu.fbansept.m2i2.model.Role;
import edu.fbansept.m2i2.security.IsAdministrateur;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/role")
@IsAdministrateur
public class RoleController {

    @Autowired
    protected RoleDao roleDao;

    @GetMapping("/liste")
    public List<Role> getAll() {
        return roleDao.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> get(@PathVariable int id) {

        Optional<Role> roleOptional = roleDao.findById(id);

        if(roleOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            //return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<>(roleOptional.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Role> add(
            @RequestBody @Valid Role roleEnvoye) {

        roleDao.save(roleEnvoye);

        return new ResponseEntity<>(roleEnvoye, HttpStatus.CREATED);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {

        Optional<Role> roleOptional = roleDao.findById(id);

        if(roleOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        roleDao.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable int id,
            @RequestBody @Valid Role roleEnvoye) {

        roleEnvoye.setId(id);

        Optional<Role> roleOptional = roleDao.findById(id);

        if(roleOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        roleDao.save(roleEnvoye);

        return new ResponseEntity<>(roleEnvoye, HttpStatus.OK);

    }

}
