package fr.iziram.API.Utils;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Cette classe sert à pré généré un monde
 */
public class PreGenerator {
	/**
	 * Permet de pré générer un monde
	 * @param sender → utilisateur de la fonction
	 * @param size → taille de la zone à pré générer
	 * @param world → monde à pré générer
	 */
	public void startPregen(Player sender, int size, World world) {
		List<Pair<Integer, Integer>> chunks = new ArrayList<>();
		for (int x = -size; x < size; x += 16) {
			for (int z = -size; z < size; z += 16) {
				chunks.add(Pair.with(x, z));
			}
		}
		int chunkList = chunks.size();
		AtomicInteger chunkID = new AtomicInteger(1);
		try {
			Timer timer = new Timer("pregen", 0, 1);
			timer.setRunnable(() -> {
				if (chunks.isEmpty()) {
					ActionMessages.sendActionBar(sender, "§aPrégenération finie!");
					timer.stop();
				} else {
					Pair<Integer, Integer> pair = chunks.remove(chunks.size() - 1);
					Chunk chunk = world.getChunkAt(pair.getValue0(), pair.getValue1());
					chunk.load();
					chunk.unload();
					chunkID.getAndIncrement();
					ActionMessages.sendActionBar(sender, "§aPrégenération : §b" + (chunkID.get() - 1) + "§7/§c" + chunkList);
				}
			});
			timer.start();
		} catch (Exception e) {
			e.printStackTrace();
		}


	}
}
