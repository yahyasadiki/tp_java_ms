package ma.app.commande.Controller;

import ma.app.commande.Repository.CommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class CommandeHealthIndicator implements HealthIndicator {

    @Autowired
    private CommandeRepository repository;

    @Override
    public Health health() {
        try {
            boolean hasCommandes = repository.count() > 0;
            if (hasCommandes) {
                return Health.up().build();
            } else {
                return Health.down().withDetail("error", "No commandes found").build();
            }
        } catch (Exception e) {
            return Health.down(e).withDetail("error", "Exception occurred while checking commandes").build();
        }
    }
}