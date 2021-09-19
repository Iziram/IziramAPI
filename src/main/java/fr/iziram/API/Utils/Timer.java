package fr.iziram.API.Utils;


import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe permettant de créer des taches répétitives facilement
 */
public class Timer {
	private static final Map<String, Timer> register = new HashMap<>();
	private static JavaPlugin plugin;
	private final int initial;
	private final int delay;
	private final String name;
	public int elapsed = 0;
	private int task = -1;
	private Runnable action;

	/**
	 * Initialisation de la tache
	 * @param name → nom de la tache (attention, il ne peut y avoir qu'une seule tache par nom
	 * @param initial → temps avant lancement
	 * @param delay → temps avant répétition
	 * @throws Exception → Si le nom existe déjà
	 */
	public Timer(String name, int initial, int delay) throws Exception {
		this.delay = delay;
		this.initial = initial;
		this.name = name;
		if (Timer.register.containsKey(name)) throw new Exception("A timer is already named like that.");
		Timer.register.put(name, this);
	}

	/**
	 * Renvoie le Register ayant toutes les taches
	 * @return Map avec en clé le nom des taches et en valeur la tache
	 */
	public static Map<String, Timer> getRegister() {
		return Timer.register;
	}

	/**
	 * Permet de mettre à jour le Plugin utilisé (voir Utilisation sur le Wiki)
	 * @param plugin → le plugin à utiliser
	 */
	public static void setJavaPlugin(JavaPlugin plugin) {
		Timer.plugin = plugin;
	}

	/**
	 * Renvoie le plugin utilisé par les taches
	 * @return → le plugin
	 */
	public static JavaPlugin getPlugin() {
		return plugin;
	}

	/**
	 * Permet de formater un temps en seconde, en affichant jours, heures, minutes et secondes
	 * @param seconds → le temps
	 * @return → le temps formaté
	 */
	public static String timeFormatter(int seconds) {
		final int SECOND = 1;
		final int MINUTE = 60 * SECOND;
		final int HOUR = 60 * MINUTE;
		final int DAY = 24 * HOUR;

		StringBuilder text = new StringBuilder("");
		if (seconds > DAY) {
			text.append(seconds / DAY).append(" jours ");
			seconds %= DAY;
		}
		if (seconds > HOUR) {
			text.append(seconds / HOUR).append(" heures ");
			seconds %= HOUR;
		}
		if (seconds > MINUTE) {
			text.append(seconds / MINUTE).append(" minutes ");
			seconds %= MINUTE;
		}
		if (seconds > SECOND) {
			text.append(seconds / SECOND).append(" secondes ");
		}

		return text.toString();
	}

	/**
	 * Lance la tache
	 */
	public void start() {
		elapsed = 0;
		if (action != null)
			task = Bukkit.getScheduler().scheduleSyncRepeatingTask(Timer.plugin, action, initial, delay);
	}

	/**
	 * Permet de définir l'action à effectuer par la tache.
	 * @param action → l'action à effectuer
	 */
	public void setRunnable(Runnable action) {
		this.action = action;
	}

	/**
	 * Stoppe la tache.
	 */
	public void stop() {
		if (task != -1)
			Bukkit.getScheduler().cancelTask(task);
		Timer.register.remove(name);
	}

}
