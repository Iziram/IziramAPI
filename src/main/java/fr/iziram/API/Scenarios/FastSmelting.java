package fr.iziram.API.Scenarios;


import org.bukkit.block.Furnace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
/**
 * Scénario FastSmelting
 */
public class FastSmelting implements ScenarioInterface {

	private JavaPlugin plugin;

	public void setPlugin(JavaPlugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onBurn(FurnaceBurnEvent e) {
		this.handleCookingTime((Furnace) e.getBlock().getState());
	}

	private void handleCookingTime(final Furnace block) {
		(new BukkitRunnable() {
			public void run() {
				if (block.getCookTime() <= 0 && block.getBurnTime() <= 0) {
					this.cancel();
				} else {
					block.setCookTime((short) (block.getCookTime() + 6));
					block.update();
				}

			}
		}).runTaskTimer(plugin, 1L, 1L);
	}


}
