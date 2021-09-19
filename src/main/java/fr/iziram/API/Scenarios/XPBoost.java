package fr.iziram.API.Scenarios;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class XPBoost implements ScenarioInterface {
	public int boost = 1;

	@Override
	public void registerEvent(JavaPlugin plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
		boost++;
	}

	@Override
	public void unRegisterEvent() {
		HandlerList.unregisterAll(this);
		this.boost = 1;
	}

	@EventHandler
	public void onXP(PlayerExpChangeEvent e) {
		e.setAmount(e.getAmount() * boost);
	}
}
