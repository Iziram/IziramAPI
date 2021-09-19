package fr.iziram.API.Scenarios;


import fr.iziram.API.Internal.scenarios.TimberTask;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

public class Timber implements ScenarioInterface {

	private final int limit;

	public Timber(int limit) {
		this.limit = limit;
	}

	public int getLimit() {
		return limit;
	}

	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		if (e.getPlayer() != null && e.getBlock().getType().toString().contains("LOG")) {
			if (e.getPlayer().getItemInHand().getType().toString().contains("_AXE")) {
				TimberTask task = new TimberTask(e.getBlock(), e.getPlayer(), getLimit());
				task.setNewObjective(e.getBlock());
				task.breakTree();
			}
		}
	}
}
