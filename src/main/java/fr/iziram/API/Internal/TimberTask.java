package fr.iziram.API.Internal;

import fr.iziram.API.Utils.Timer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.UUID;

public class TimberTask {
	private final UUID uuid;
	private final int limit;
	private Block startBlock;
	private Material startBlockMaterial;
	private Player player;
	private Material leavesType;
	private BukkitTask task;
	private boolean isRunning = false;

	public TimberTask(Block startBlock, Player player, int limit) {
		this.player = player;
		this.uuid = player.getUniqueId();
		this.limit = limit;
		setNewObjective(startBlock);

	}

	public void setNewObjective(Block startBlock) {
		this.player = Bukkit.getPlayer(uuid);
		if (player != null) {
			this.startBlock = startBlock;
			this.startBlockMaterial = startBlock.getType();
			if (startBlockMaterial.toString().contains("_2")) this.leavesType = Material.LEAVES_2;
			else this.leavesType = Material.LEAVES;
		}
	}

	private boolean isWoodCorrect(Block blockToCheck) {
		return blockToCheck.getType() == this.startBlockMaterial;
	}

	private boolean areLeavesCorrect(Block leaves) {
		return leaves.getType() == leavesType;
	}

	public void breakTree() {
		int maxBlocks = limit;
		isRunning = true;
		this.task = new BukkitRunnable() {
			@Override
			public void run() {
				ArrayList<Block> totalBlocks = new ArrayList<>();
				ArrayList<Block> blocksUpdate = new ArrayList<>();
				ArrayList<Block> blocksNext = new ArrayList<>();
				blocksNext.add(startBlock);
				int roundFoundLogs;
				do {
					roundFoundLogs = 0;
					for (Block block : blocksNext) {
						for (int x = -1; x < 2; x++) {
							for (int y = 0; y < 2; y++) {
								for (int z = -1; z < 2; z++) {
									Block next = block.getLocation().clone().add(x, y, z).getBlock();
									if (!totalBlocks.contains(next)) {
										if (isWoodCorrect(next)) {
											blocksUpdate.add(next);
											totalBlocks.add(next);
											breakBlock(next);
											roundFoundLogs++;
										} else if (areLeavesCorrect(next)) {
											totalBlocks.add(next);
											breakBlock(next);
										}
									}
								}
							}
						}
					}
					blocksNext.clear();
					if (!blocksUpdate.isEmpty()) {
						blocksNext.addAll(blocksUpdate);
					}
					blocksUpdate.clear();
				} while (roundFoundLogs > 0 && totalBlocks.size() < maxBlocks);


				player.updateInventory();
				task.cancel();
			}
		}.runTaskAsynchronously(Timer.getPlugin());
	}

	private void breakBlock(Block block) {
		if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE) {
			Bukkit.getScheduler().runTaskLater(Timer.getPlugin(), block::breakNaturally, 2);
		} else {
			Bukkit.getScheduler().runTaskLater(Timer.getPlugin(), () -> block.setType(Material.AIR), 1);
		}
	}
}
