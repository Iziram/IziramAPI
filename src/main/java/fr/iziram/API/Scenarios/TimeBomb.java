package fr.iziram.API.Scenarios;

import fr.iziram.API.Internal.scenarios.TimeBombTask;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * Scénario TimeBomb (configurable, voir Wiki)
 */
public class TimeBomb implements ScenarioInterface {

	private int time;

	public TimeBomb(int time) {
		this.time = time;
	}

	@EventHandler
	public void onKill(PlayerDeathEvent e) {
		Player p = e.getEntity();
		e.setKeepInventory(true);
		List<ItemStack> itemStackList = new ArrayList<>();
		itemStackList.addAll(Arrays.asList(p.getInventory().getContents()));
		itemStackList.addAll(Arrays.asList(p.getInventory().getArmorContents()));
		new TimeBombTask(time, itemStackList.toArray(new ItemStack[0]), p.getName(), p.getLocation()).startTimeBomb();
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}
}
