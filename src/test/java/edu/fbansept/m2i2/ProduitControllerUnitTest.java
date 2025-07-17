package edu.fbansept.m2i2;

import edu.fbansept.m2i2.controller.ProduitController;
import edu.fbansept.m2i2.mock.ProduitDaoMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ProduitControllerUnitTest {

    @Test
    public void fetchExistingProduct_shouldSendResponseCode200() {

        ProduitController produitController =
                new ProduitController(new ProduitDaoMock());

        ResponseEntity response = produitController.get(1);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void fetchNotExistingProduct_shouldSendResponseCode404() {

        ProduitController produitController =
                new ProduitController(new ProduitDaoMock());

        ResponseEntity response = produitController.get(2);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}
