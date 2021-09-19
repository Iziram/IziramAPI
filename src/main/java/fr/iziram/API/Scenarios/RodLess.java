package fr.iziram.API.Scenarios;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.CraftItemEvent;

public class RodLess implements ScenarioInterface {

	@EventHandler
	public void onCraft(CraftItemEvent e) {
		if (e.getInventory().getResult().getType() == Material.FISHING_ROD) {
			e.setCancelled(true);
		}
	}
}
