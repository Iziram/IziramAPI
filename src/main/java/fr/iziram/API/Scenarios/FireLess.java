package fr.iziram.API.Scenarios;

import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
/**
 * Scénario FireLess
 */
public class FireLess implements ScenarioInterface {

	@EventHandler
	public void onFire(EntityDamageEvent e) {
		if (e.getCause() == EntityDamageEvent.DamageCause.FIRE ||
				e.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK ||
				e.getCause() == EntityDamageEvent.DamageCause.LAVA) e.setCancelled(true);
	}
}
