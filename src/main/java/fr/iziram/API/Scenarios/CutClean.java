package fr.iziram.API.Scenarios;

import fr.iziram.API.Builders.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * Scénario CutClean
 */
public class CutClean implements ScenarioInterface {

	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		if (e.getBlock().getType() == Material.IRON_ORE || e.getBlock().getType() == Material.GOLD_ORE) {
			Player p = e.getPlayer();
			Block block = e.getBlock();
			Location location = block.getLocation();
			if (block.getType() == Material.IRON_ORE)
				location.getWorld().dropItem(location, new ItemStack(Material.IRON_INGOT, 1));
			else location.getWorld().dropItem(location, new ItemStack(Material.GOLD_INGOT, 1));
			block.setType(Material.AIR);
			int boost = ((XPBoost) Scenario.XPBOOST.scenario).boost;
			p.giveExp(boost);
			p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 1);
		}
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onAnimalKill(EntityDeathEvent e) {
		Entity entity = e.getEntity();
		List<EntityType> entities = new ArrayList<>(Arrays.asList(EntityType.COW, EntityType.MUSHROOM_COW, EntityType.SHEEP,
				EntityType.PIG, EntityType.CHICKEN, EntityType.RABBIT, EntityType.HORSE));
		if (entities.contains(entity.getType())) {
			e.getDrops().forEach((itemStack -> {
				if (itemStack != null) {
					Material[] list = {Material.MUTTON, Material.RABBIT, Material.PORK, Material.RAW_BEEF, Material.RAW_CHICKEN};
					List<Material> materialList = new ArrayList<>(Arrays.asList(list));
					if (materialList.contains(itemStack.getType()))
						switch (itemStack.getType()) {
							case MUTTON:
								itemStack.setType(Material.COOKED_MUTTON);
								break;
							case RABBIT:
								itemStack.setType(Material.COOKED_RABBIT);
								break;
							case PORK:
								itemStack.setType(Material.GRILLED_PORK);
								break;
							case RAW_CHICKEN:
								itemStack.setType(Material.COOKED_CHICKEN);
								break;
							default:
								itemStack.setType(Material.COOKED_BEEF);
								break;
						}
				}
			}));
			if (entity.getType() == EntityType.HORSE) {
				e.getDrops().add(new ItemBuilder()
						.setItemName("§cCooked by §6Findus")
						.setItemMaterial(Material.COOKED_BEEF)
						.setItemAmount(2)
						.build());
			}
		}

	}
}
