package ma.app.commande.Controller;

import ma.app.commande.Model.Commande;
import ma.app.commande.Service.CommandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/commandes")
public class CommandeController {

    @Autowired
    private CommandeService service;

    @Value("${myapp.recent-days}")
    private int recentDays;

    @GetMapping
    public List<Commande> getAllCommandes() {
        return service.getAllCommandes();
    }

    @GetMapping("/{id}")
    public Commande findCommandeById(@PathVariable Long id) {
        return service.findCommandeById(id);
    }

    @PostMapping
    public Commande saveCommande(@RequestBody Commande commande) {
        return service.saveCommande(commande);
    }

    @PutMapping("/{id}")
    public Commande updateCommande(@PathVariable Long id, @RequestBody Commande commande) {
        return service.updateCommande(id, commande);
    }

    @DeleteMapping("/{id}")
    public void deleteCommandeById(@PathVariable Long id) {
        service.deleteCommandeById(id);
    }

    @GetMapping("/recent")
    public List<Commande> findRecentCommandes() {
        return service.findRecentCommandes(recentDays);
    }
}