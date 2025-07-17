package edu.fbansept.m2i2;

import edu.fbansept.m2i2.model.Categorie;
import edu.fbansept.m2i2.model.Ticket;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class TicketUnitTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory =
                Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void createValidTicket_shouldNotBeInvalid() {
        Ticket ticket = new Ticket();
        ticket.setTitre("Titre valide");
        ticket.setDescription("Description valide");

        Set<ConstraintViolation<Ticket>> violations = validator.validate(ticket);

        Assertions.assertEquals(0, violations.size());
    }

    @Test
    public void createTicketWithBlankTitre_shouldNotBeValid() {
        Ticket ticket = new Ticket();
        ticket.setTitre(""); // Titre vide, donc invalide
        ticket.setDescription("Description valide");

        boolean violationExist = violationsContainPropertyWithCode(
                "titre", "NotBlank", validator.validate(ticket, Ticket.add.class)
        );

        Assertions.assertTrue(violationExist);
    }

    @Test
    public void addCategories_shouldContainThem() {
        Ticket ticket = new Ticket();

        Categorie cat1 = new Categorie();
        cat1.setId(1);

        Categorie cat2 = new Categorie();
        cat2.setId(2);

        ticket.getCategories().add(cat1);
        ticket.getCategories().add(cat2);

        Assertions.assertEquals(2, ticket.getCategories().size());
    }

    private boolean violationsContainPropertyWithCode(String property, String code, Set<ConstraintViolation<Ticket>> violations) {
        return violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals(property)
                && v.getMessageTemplate().contains(code));
    }
}