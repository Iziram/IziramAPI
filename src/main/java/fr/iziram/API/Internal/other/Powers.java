package fr.iziram.API.Internal.other;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Powers {
	public static void setPotionEffect(PotionEffectType type, int duration, int level, Player target) {
		if (target.hasPotionEffect(type)) target.removePotionEffect(type);
		target.addPotionEffect(new PotionEffect(type, duration * 20, level - 1, true, false));
	}

	public static void setHealth(Player p, double health) {
		double current = p.getHealth();
		p.setMaxHealth(health);
		p.setHealth(Math.min(current, health));
	}

	public static List<UUID> getNearbyPlayers(Player user, int range, boolean itself) {
		List<UUID> nearby = new ArrayList<>();
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (user.getLocation().distance(p.getLocation()) <= range && p.getGameMode() == GameMode.SURVIVAL) {
				if (user == p) {
					if (itself) nearby.add(p.getUniqueId());
				} else {
					nearby.add(p.getUniqueId());
				}
			}
		}

		return nearby;
	}
}
