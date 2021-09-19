package fr.iziram.API.Scenarios;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
/**
 * Scénario Nerf Resistance
 */
public class NerfRes implements ScenarioInterface {
	public int multiplier = 3;

	@EventHandler
	public void onPlayerDamage(EntityDamageByEntityEvent event) {
		Entity e = event.getEntity();
		if (e instanceof Player) {
			Player p = (Player) e;
			if (p.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE)) {
				for (PotionEffect effect : p.getActivePotionEffects()) {
					if (effect.getType() == PotionEffectType.DAMAGE_RESISTANCE) {
						event.setDamage(EntityDamageEvent.DamageModifier.RESISTANCE,
								multiplier * (effect.getAmplifier() + 1));
					}
				}

			}
		}
	}
}
