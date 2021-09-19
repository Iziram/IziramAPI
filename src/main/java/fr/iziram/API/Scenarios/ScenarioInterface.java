package fr.iziram.API.Scenarios;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public interface ScenarioInterface extends Listener {

	default void unRegisterEvent() {
		HandlerList.unregisterAll(this);
	}

	default void registerEvent(JavaPlugin plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
}
