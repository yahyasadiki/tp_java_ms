package ma.app.commande;

import ma.app.commande.Controller.CommandeController;
import ma.app.commande.Model.Commande;
import ma.app.commande.Service.CommandeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class TestRest {

    @Mock
    private CommandeService service;

    @InjectMocks
    private CommandeController controller;

    private MockMvc mockMvc;

    @Value("${myapp.recent-days}")
    private int recentDays;

    @BeforeEach
    public void setup(WebApplicationContext webApplicationContext) {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testGetAllCommandes() throws Exception {
        List<Commande> commandes = Arrays.asList(new Commande(), new Commande());
        when(service.getAllCommandes()).thenReturn(commandes);

        mockMvc.perform(get("/api/commandes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(commandes.size()));
    }

    @Test
    public void testFindCommandeById() throws Exception {
        Commande commande = new Commande();
        when(service.findCommandeById(anyLong())).thenReturn(commande);

        mockMvc.perform(get("/api/commandes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(commande.getId()));
    }

    @Test
    public void testSaveCommande() throws Exception {
        Commande commande = new Commande();
        when(service.saveCommande(any(Commande.class))).thenReturn(commande);

        mockMvc.perform(post("/api/commandes")
                .contentType("application/json")
                .content("{\"description\":\"Test Commande\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(commande.getId()));
    }

    @Test
    public void testUpdateCommande() throws Exception {
        Commande commande = new Commande();
        when(service.updateCommande(anyLong(), any(Commande.class))).thenReturn(commande);

        mockMvc.perform(put("/api/commandes/1")
                .contentType("application/json")
                .content("{\"description\":\"Updated Commande\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(commande.getId()));
    }

    @Test
    public void testDeleteCommandeById() throws Exception {
        mockMvc.perform(delete("/api/commandes/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testFindRecentCommandes() throws Exception {
        List<Commande> commandes = Arrays.asList(new Commande(), new Commande());
        when(service.findRecentCommandes(recentDays)).thenReturn(commandes);

        mockMvc.perform(get("/api/commandes/recent"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(commandes.size()));
    }
}