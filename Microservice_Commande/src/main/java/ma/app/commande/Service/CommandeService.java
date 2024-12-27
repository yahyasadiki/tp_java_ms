package ma.app.commande.Service;

import ma.app.commande.Model.Commande;
import ma.app.commande.Repository.CommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CommandeService {

    @Autowired
    private CommandeRepository repository;

    public List<Commande> getAllCommandes() {
        return repository.findAll();
    }

    public Commande findCommandeById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Commande introuvable"));
    }

    public Commande saveCommande(Commande commande) {
        return repository.save(commande);
    }

    public Commande updateCommande(Long id, Commande updatedCommande) {
        Commande existingCommande = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande introuvable"));
        existingCommande.setDescription(updatedCommande.getDescription());
        existingCommande.setQuantite(updatedCommande.getQuantite());
        existingCommande.setDate(updatedCommande.getDate());
        existingCommande.setMontant(updatedCommande.getMontant());
        return repository.save(existingCommande);
    }

    public void deleteCommandeById(Long id) {
        repository.deleteById(id);
    }

    public List<Commande> findRecentCommandes(int days) {
        LocalDate thresholdDate = LocalDate.now().minusDays(days);
        return repository.findByDateAfter(thresholdDate);
    }
}