package fr.iziram.API.Scenarios;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HasteyBoys implements ScenarioInterface {

	@EventHandler
	public void onCraft(CraftItemEvent e) {
		Material[] materials = {Material.WOOD_AXE, Material.WOOD_SPADE, Material.WOOD_PICKAXE, Material.STONE_AXE,
				Material.STONE_PICKAXE, Material.STONE_SPADE, Material.IRON_AXE, Material.IRON_SPADE,
				Material.IRON_PICKAXE, Material.GOLD_AXE, Material.GOLD_PICKAXE, Material.GOLD_SPADE,
				Material.DIAMOND_AXE, Material.DIAMOND_SPADE, Material.DIAMOND_PICKAXE};
		List<Material> materialList = new ArrayList<>(Arrays.asList(materials));
		Recipe recipe = e.getRecipe();
		ItemStack result = recipe.getResult();
		if (materialList.contains(result.getType())) {
			ItemMeta meta = result.getItemMeta();
			meta.addEnchant(Enchantment.DIG_SPEED, 3, false);
			meta.addEnchant(Enchantment.DURABILITY, 3, false);
			e.getCurrentItem().setItemMeta(meta);
		}

	}
}
