package edu.fbansept.m2i2.controller;

import com.fasterxml.jackson.annotation.JsonView;
import edu.fbansept.m2i2.dao.TicketDao;
import edu.fbansept.m2i2.dao.UtilisateurDao;
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

import java.util.List;

@RestController
@RequestMapping("/api/ticket")
public class TicketController {

    @Autowired
    protected TicketDao ticketDao;

    @Autowired
    protected UtilisateurDao utilisateurDao;

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

        ticket.setAuteur(auteur);

        Priorite priorite = new Priorite();
        priorite.setId(1);
        ticket.setPriorite(priorite);

        return new ResponseEntity<>(ticketDao.save(ticket), HttpStatus.OK);
    }

    @PutMapping("/{id}/resoudre")
    @IsAdministrateur
    public Ticket resolveTicket(@PathVariable int id) {
        Ticket ticket = ticketDao.findById(id).orElseThrow();
        ticket.setResolu(true);
        return ticketDao.save(ticket);
    }
}
