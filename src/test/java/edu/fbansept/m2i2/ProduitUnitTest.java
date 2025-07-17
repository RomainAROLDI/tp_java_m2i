package edu.fbansept.m2i2;

import edu.fbansept.m2i2.model.Produit;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class ProduitUnitTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory =
                Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void createValidProduct_shouldNotBeInvalid() {
        Produit produitValide = new Produit();

        produitValide.setNom("Nom produit");
        produitValide.setPrix(99.99f);

        Set<ConstraintViolation<Produit>> violations = validator.validate(produitValide);

        Assertions.assertEquals(0, violations.size());
    }

    @Test
    public void createProductWithBlankName_shouldNotBeValid() {
        Produit produitValide = new Produit();

        produitValide.setNom("");
        produitValide.setPrix(99.99f);

        boolean violationExist = TestUtils.constraintExist(
                validator.validate(produitValide),
                "nom",
                "NotBlank");

        Assertions.assertTrue(violationExist);
    }

    @Test
    public void createProductWithNegativePrice_shouldNotBeValid() {
        Produit produitValide = new Produit();

        produitValide.setNom("Nom produit");
        produitValide.setPrix(-1);

        boolean violationExist = TestUtils.constraintExist(
                validator.validate(produitValide),
                "prix",
                "DecimalMin");

        Assertions.assertTrue(violationExist);
    }

}
