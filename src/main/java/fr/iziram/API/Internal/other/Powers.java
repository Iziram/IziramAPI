package fr.iziram.API.Internal.other;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Cette classe permet de faciliter certaines actions sur un joueur
 */
public class Powers {
	/**
	 * Cette fonction permet de donner un effet de potion à un joueur
	 * @param type → Type de l'effet
	 * @param duration → Durée de l'effet en seconde
	 * @param level → Niveau de l'effet (1 → 256)
	 * @param target → Le joueur
	 */
	public static void setPotionEffect(PotionEffectType type, int duration, int level, Player target) {
		if (target.hasPotionEffect(type)) target.removePotionEffect(type);
		target.addPotionEffect(new PotionEffect(type, duration * 20, level - 1, true, false));
	}

	/**
	 * Cette fonction permet de changer la vie d'un joueur, Le changement de cœurs conserve les dégâts déjà subits
	 * @param p → le joueur
	 * @param health → le nouveau montant de vie (20 par défaut)
	 */
	public static void setHealth(Player p, double health) {
		double current = p.getHealth();
		p.setMaxHealth(health);
		p.setHealth(Math.min(current, health));
	}

	/**
	 * Cette fonction permet de récupérer la liste des joueurs se trouvant autours d'un joueur dans un certain rayon
	 * @param user → Le joueur étant le centre de la recherche
	 * @param range → le rayon de recherche
	 * @param itself → si la recherche doit compter le joueur au centre ou pas
	 * @return List<UUID> → La liste des uuids des joueurs étant autours du centre
	 */
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
