package edu.fbansept.m2i2;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class ProduitApiIntegrationTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper mapper;

    private MockMvc mvc;

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    void fecthProductionList_shouldReturn200() throws Exception {
        mvc.perform(get("/api/produit/liste"))
                .andExpect(status().isOk());
    }

    @Test
    void fecthProduct_shouldProductJsonShowEmailSeller() throws Exception {
        mvc.perform(get("/api/produit/2"))
                .andExpect(jsonPath("$.vendeur.email").exists());
    }

    @Test
    @WithMockUser(roles = {"CLIENT"})
    void deleteProductAsClient_shouldSend403() throws Exception {
        mvc.perform(delete("/api/produit/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails("c@c")
    void deleteProductAsAdmin_shouldSend204() throws Exception {
        mvc.perform(delete("/api/produit/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithUserDetails("b@b")
    void deleteProductAsOwner_shouldSend204() throws Exception {
        mvc.perform(delete("/api/produit/2"))
                .andExpect(status().isNoContent());
    }

}
