package fr.iziram.API.Scenarios;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
/**
 * Scénario Nerf Force
 */
public class NerfForce implements ScenarioInterface {
	public int multiplier = 3;

	@EventHandler
	public void onPlayerDamage(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {
			Player player = (Player) event.getDamager();
			if (player.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) {

				for (PotionEffect effect : player.getActivePotionEffects()) {
					if (effect.getType().equals(PotionEffectType.INCREASE_DAMAGE)) {
						int level = effect.getAmplifier() + 1;
						double newDamage = event.getDamage(EntityDamageEvent.DamageModifier.BASE) / ((double) level * 1.3D + 1.0D) + (double) (multiplier * level);
						double damagePercent = newDamage / event.getDamage(EntityDamageEvent.DamageModifier.BASE);

						try {
							event.setDamage(EntityDamageEvent.DamageModifier.ARMOR, event.getDamage(EntityDamageEvent.DamageModifier.ARMOR) * damagePercent);
						} catch (Exception ignored) {
						}

						try {
							event.setDamage(EntityDamageEvent.DamageModifier.MAGIC, event.getDamage(EntityDamageEvent.DamageModifier.MAGIC) * damagePercent);
						} catch (Exception ignored) {
						}

						try {
							event.setDamage(EntityDamageEvent.DamageModifier.RESISTANCE, event.getDamage(EntityDamageEvent.DamageModifier.RESISTANCE) * damagePercent);
						} catch (Exception ignored) {
						}

						try {
							event.setDamage(EntityDamageEvent.DamageModifier.BLOCKING, event.getDamage(EntityDamageEvent.DamageModifier.BLOCKING) * damagePercent);
						} catch (Exception ignored) {
						}

						event.setDamage(EntityDamageEvent.DamageModifier.BASE, newDamage);
						break;
					}
				}
			}
		}
	}
}
