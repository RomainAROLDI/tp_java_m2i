package edu.fbansept.m2i2;

import edu.fbansept.m2i2.controller.TicketController;
import edu.fbansept.m2i2.dao.CategorieDao;
import edu.fbansept.m2i2.dao.PrioriteDao;
import edu.fbansept.m2i2.dao.TicketDao;
import edu.fbansept.m2i2.dao.UtilisateurDao;
import edu.fbansept.m2i2.model.Ticket;
import edu.fbansept.m2i2.model.Utilisateur;
import edu.fbansept.m2i2.security.AppUserDetails;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;
import java.util.Optional;

public class TicketControllerUnitTest {

    private TicketController createControllerWithMocks(TicketDao ticketDao, UtilisateurDao utilisateurDao) {
        return new TicketController(
                ticketDao,
                utilisateurDao,
                Mockito.mock(PrioriteDao.class),
                Mockito.mock(CategorieDao.class)
        );
    }

    @Test
    public void resolveTicket_nonAdmin_shouldReturnUnauthorized() {
        TicketDao ticketDao = Mockito.mock(TicketDao.class);
        UtilisateurDao utilisateurDao = Mockito.mock(UtilisateurDao.class);

        Ticket ticket = new Ticket();
        ticket.setId(1);

        Mockito.when(ticketDao.findById(1)).thenReturn(Optional.of(ticket));
        Mockito.when(utilisateurDao.findById(999)).thenReturn(Optional.empty());

        AppUserDetails userDetails = Mockito.mock(AppUserDetails.class);
        Utilisateur fakeUser = new Utilisateur();
        fakeUser.setId(999);
        Mockito.when(userDetails.getUtilisateur()).thenReturn(fakeUser);

        TicketController controller = createControllerWithMocks(ticketDao, utilisateurDao);

        ResponseEntity<Ticket> response = controller.resolveTicket(1, userDetails);

        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void resolveTicket_adminIsSetAsResolveur_shouldWork() {
        TicketDao ticketDao = Mockito.mock(TicketDao.class);
        UtilisateurDao utilisateurDao = Mockito.mock(UtilisateurDao.class);

        Ticket ticket = new Ticket();
        ticket.setId(1);

        Utilisateur admin = new Utilisateur();
        admin.setId(1);

        Mockito.when(ticketDao.findById(1)).thenReturn(Optional.of(ticket));
        Mockito.when(utilisateurDao.findById(1)).thenReturn(Optional.of(admin));

        AppUserDetails userDetails = Mockito.mock(AppUserDetails.class);
        Mockito.when(userDetails.getUtilisateur()).thenReturn(admin);

        Mockito.when(ticketDao.save(Mockito.any())).thenAnswer(invocation -> invocation.getArgument(0));

        TicketController controller = createControllerWithMocks(ticketDao, utilisateurDao);
        ResponseEntity<Ticket> response = controller.resolveTicket(1, userDetails);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertTrue(Objects.requireNonNull(response.getBody()).isResolu());
        Assertions.assertEquals(admin, response.getBody().getResolveur());
    }
}
