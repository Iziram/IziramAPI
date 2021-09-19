package fr.iziram.API.Scenarios;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CatEyes implements ScenarioInterface {

	public static void enableCatEyes() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 1000000, 0, false, false));
		}
	}

	public static void disableCatEyes() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.removePotionEffect(PotionEffectType.NIGHT_VISION);
		}
	}

	@Override
	public void registerEvent(JavaPlugin ignored) {
		enableCatEyes();
	}

	@Override
	public void unRegisterEvent() {
		disableCatEyes();
	}
}
