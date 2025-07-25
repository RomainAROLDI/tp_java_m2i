package edu.fbansept.m2i2.controller;

import com.fasterxml.jackson.annotation.JsonView;
import edu.fbansept.m2i2.dao.CategorieDao;
import edu.fbansept.m2i2.dao.PrioriteDao;
import edu.fbansept.m2i2.dao.TicketDao;
import edu.fbansept.m2i2.dao.UtilisateurDao;
import edu.fbansept.m2i2.model.Categorie;
import edu.fbansept.m2i2.model.Priorite;
import edu.fbansept.m2i2.model.Ticket;
import edu.fbansept.m2i2.model.Utilisateur;
import edu.fbansept.m2i2.security.AppUserDetails;
import edu.fbansept.m2i2.security.IsAdministrateur;
import edu.fbansept.m2i2.view.TicketView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/ticket")
public class TicketController {

    private final TicketDao ticketDao;
    private final UtilisateurDao utilisateurDao;
    private final PrioriteDao prioriteDao;
    private final CategorieDao categorieDao;

    @Autowired
    public TicketController(
            TicketDao ticketDao,
            UtilisateurDao utilisateurDao,
            PrioriteDao prioriteDao,
            CategorieDao categorieDao
    ) {
        this.ticketDao = ticketDao;
        this.utilisateurDao = utilisateurDao;
        this.prioriteDao = prioriteDao;
        this.categorieDao = categorieDao;
    }

    @GetMapping("/liste")
    @JsonView(TicketView.class)
    public List<Ticket> getAll(@AuthenticationPrincipal AppUserDetails userDetails) {
        if (userDetails == null || userDetails.getUtilisateur() == null) {
            return ticketDao.findByResoluFalse();
        }

        return ticketDao.findAll();
    }

    @PostMapping
    public ResponseEntity<Ticket> createTicket(
            @RequestBody @Validated(Ticket.add.class) Ticket ticket,
            @AuthenticationPrincipal AppUserDetails userDetails
    ) {
        if (userDetails == null || userDetails.getUtilisateur() == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Utilisateur auteur = utilisateurDao.findById(userDetails.getUtilisateur().getId()).orElse(null);
        if (auteur == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Priorite priorite = prioriteDao.findById(1).orElse(null);
        if (priorite == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Categorie> categoriesFromDb = new ArrayList<>();
        for (Categorie cat : ticket.getCategories()) {
            Categorie categorie = categorieDao.findById(cat.getId())
                    .orElseThrow(() -> new RuntimeException("Categorie non trouvée : " + cat.getId()));
            categoriesFromDb.add(categorie);
        }

        ticket.setAuteur(auteur);
        ticket.setPriorite(priorite);
        ticket.setCategories(categoriesFromDb);

        return new ResponseEntity<>(ticketDao.save(ticket), HttpStatus.OK);
    }

    @PutMapping("/{id}/resoudre")
    @IsAdministrateur
    public ResponseEntity<Ticket> resolveTicket(@PathVariable int id, @AuthenticationPrincipal AppUserDetails userDetails) {
        Ticket ticket = ticketDao.findById(id).orElse(null);

        if (ticket == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Utilisateur resolveur = utilisateurDao.findById(userDetails.getUtilisateur().getId()).orElse(null);
        if (resolveur == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        ticket.setResolu(true);
        ticket.setResolveur(resolveur);

        return new ResponseEntity<>(ticketDao.save(ticket), HttpStatus.OK);
    }
}
