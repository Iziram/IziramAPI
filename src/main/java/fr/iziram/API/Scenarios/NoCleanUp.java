package fr.iziram.API.Scenarios;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
/**
 * Scénario NoCleanUp
 */
public class NoCleanUp implements ScenarioInterface {

	@EventHandler
	public void onKill(PlayerDeathEvent e) {
		Player killer = e.getEntity().getKiller();
		killer.setHealth(Double.min(killer.getMaxHealth(), killer.getHealth() + 6));
	}
}
