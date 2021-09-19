package fr.iziram.API.Internal;

import fr.iziram.API.Utils.Timer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class TimeBombTask {

	private final int duration;
	private final ItemStack[] inventory;
	private final String killedName;
	private final Location deathLocation;
	private ArmorStand armorStand;

	public TimeBombTask(int duration, ItemStack[] inventory, String killedName, Location deathLocation) {
		this.duration = duration;
		this.inventory = inventory;
		this.killedName = killedName;
		this.deathLocation = deathLocation;
		generateArmorStand();
		generateChests();
	}

	public void startTimeBomb() {
		try {
			Timer timer = new Timer("TimeBomb:" + killedName + "/" + duration, 0, 20);

			timer.setRunnable(() -> {
				if (duration - timer.elapsed <= 0) {
					explode();
					timer.stop();
				}
				updateArmorStand(duration - timer.elapsed);
				timer.elapsed++;
			});

			timer.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void generateChests() {
		Block point = deathLocation.getBlock();
		point.setType(Material.CHEST);
		Location poi = deathLocation.clone();
		poi.add(1d, 0d, 0d);
		Block point2 = poi.getBlock();
		point2.setType(Material.CHEST);
		Chest c = (Chest) point.getState();
		InventoryHolder holder = c.getInventory().getHolder();
		if (holder instanceof DoubleChest) {
			DoubleChest doubleChest = (DoubleChest) holder;
			doubleChest.getInventory().setContents(inventory);
		}
	}

	private void explode() {
		Block point = deathLocation.getBlock();
		Location poi = deathLocation.clone();
		poi.add(1d, 0d, 0d);
		Block point2 = poi.getBlock();

		point.breakNaturally();
		point2.breakNaturally();

		deathLocation.getWorld().createExplosion(deathLocation, 6F, true);
		armorStand.remove();
	}

	private void generateArmorStand() {
		Location armorStandLocation = deathLocation.clone();
		armorStandLocation.add(0, -1, 0);
		armorStand = (ArmorStand) deathLocation.getWorld().spawnEntity(armorStandLocation, EntityType.ARMOR_STAND);
		armorStand.setVisible(false);
		armorStand.setGravity(false);
		armorStand.setCustomName("§4" + duration);
		armorStand.setCustomNameVisible(true);
	}

	private void updateArmorStand(int time) {
		armorStand.setCustomName("§4" + time);
	}
}
