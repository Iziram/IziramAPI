package fr.iziram.API.Scenarios;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class DiamondLimit implements ScenarioInterface {
	private final HashMap<UUID, Integer> diamondHashMap = new HashMap<>();
	private int limit = 22;
	private boolean replaceByGold = false;

	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		if (p != null && e.getBlock() != null && e.getBlock().getType() == Material.DIAMOND_ORE) {
			int playerLimit = diamondHashMap.getOrDefault(p.getUniqueId(), 0);
			if (playerLimit == 0) diamondHashMap.put(p.getUniqueId(), 1);
			else if (playerLimit < limit) diamondHashMap.replace(p.getUniqueId(), playerLimit + 1);
			else {
				e.setCancelled(true);
				p.sendMessage("§7[§bDiamondLimit§7]" + "§cVous avez atteint votre limite de diamants.");
				if (isReplaceByGold()) {
					e.getBlock().getDrops().clear();
					e.getBlock().setType(Material.AIR);
					ItemStack item = p.getItemInHand();
					if (item.containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) {
						int level = item.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);
						int amout = 1 + new Random().nextInt(level + 1);
						e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.GOLD_INGOT, amout));
					}


				}

			}
		}
	}

	public int getLimit() {
		return limit;
	}

	public DiamondLimit setLimit(int limit) {
		this.limit = limit;
		return this;
	}

	public boolean isReplaceByGold() {
		return replaceByGold;
	}

	public DiamondLimit setReplaceByGold(boolean replaceByGold) {
		this.replaceByGold = replaceByGold;
		return this;
	}
}
