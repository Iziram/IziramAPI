package fr.iziram.API.Utils;


import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class Timer {
	private static final Map<String, Timer> register = new HashMap<>();
	private static JavaPlugin plugin;
	private final int initial;
	private final int delay;
	private final String name;
	public int elapsed = 0;
	private int task = -1;
	private Runnable action;

	public Timer(String name, int initial, int delay) throws Exception {
		this.delay = delay;
		this.initial = initial;
		this.name = name;
		if (Timer.register.containsKey(name)) throw new Exception("A timer is already named like that.");
		Timer.register.put(name, this);
	}

	public static Map<String, Timer> getRegister() {
		return Timer.register;
	}

	public static void setJavaPlugin(JavaPlugin plugin) {
		Timer.plugin = plugin;
	}

	public static JavaPlugin getPlugin() {
		return plugin;
	}

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

	public void start() {
		elapsed = 0;
		if (action != null)
			task = Bukkit.getScheduler().scheduleSyncRepeatingTask(Timer.plugin, action, initial, delay);
	}

	public void setRunnable(Runnable action) {
		this.action = action;
	}

	public void stop() {
		if (task != -1)
			Bukkit.getScheduler().cancelTask(task);
		Timer.register.remove(name);
	}

}
