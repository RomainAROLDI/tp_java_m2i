package edu.fbansept.m2i2;

import edu.fbansept.m2i2.controller.TicketController;
import edu.fbansept.m2i2.dao.TicketDao;
import edu.fbansept.m2i2.model.Ticket;
import edu.fbansept.m2i2.security.AppUserDetails;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

public class TicketControllerIntegrationTest {

    @Test
    public void getAll_notLoggedIn_shouldReturnOnlyUnresolvedTickets() {
        TicketDao ticketDao = Mockito.mock(TicketDao.class);

        Ticket ticket1 = new Ticket();
        ticket1.setResolu(false);
        Ticket ticket2 = new Ticket();
        ticket2.setResolu(false);

        Mockito.when(ticketDao.findByResoluFalse()).thenReturn(Arrays.asList(ticket1, ticket2));

        TicketController controller = new TicketController(
                ticketDao,
                Mockito.mock(edu.fbansept.m2i2.dao.UtilisateurDao.class),
                Mockito.mock(edu.fbansept.m2i2.dao.PrioriteDao.class),
                Mockito.mock(edu.fbansept.m2i2.dao.CategorieDao.class)
        );

        List<Ticket> result = controller.getAll(null);

        Assertions.assertEquals(2, result.size());
        Assertions.assertTrue(result.stream().noneMatch(Ticket::isResolu));
    }

    @Test
    public void getAll_loggedIn_shouldReturnAllTickets() {
        TicketDao ticketDao = Mockito.mock(TicketDao.class);

        Ticket ticket1 = new Ticket();
        ticket1.setResolu(false);
        Ticket ticket2 = new Ticket();
        ticket2.setResolu(true);

        Mockito.when(ticketDao.findAll()).thenReturn(Arrays.asList(ticket1, ticket2));

        AppUserDetails userDetails = Mockito.mock(AppUserDetails.class);
        Mockito.when(userDetails.getUtilisateur()).thenReturn(new edu.fbansept.m2i2.model.Utilisateur());

        TicketController controller = new TicketController(
                ticketDao,
                Mockito.mock(edu.fbansept.m2i2.dao.UtilisateurDao.class),
                Mockito.mock(edu.fbansept.m2i2.dao.PrioriteDao.class),
                Mockito.mock(edu.fbansept.m2i2.dao.CategorieDao.class)
        );

        List<Ticket> result = controller.getAll(userDetails);

        Assertions.assertEquals(2, result.size());
    }
}