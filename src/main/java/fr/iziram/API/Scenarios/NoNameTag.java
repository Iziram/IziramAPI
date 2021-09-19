package fr.iziram.API.Scenarios;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

/**
 * Scénario NoNameTag WIP
 */
public class NoNameTag implements ScenarioInterface {

	public static Scoreboard scoreboard;
	private final String NoTagTeamName = "NoNameTagTeam";

	@Override
	public void registerEvent(JavaPlugin plugin) {
		ScenarioInterface.super.registerEvent(plugin);
		removeNameTags();
	}

	@Override
	public void unRegisterEvent() {
		ScenarioInterface.super.unRegisterEvent();
		enableNameTags();
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		removeNameTags();
	}

	private void removeNameTags() {
		Team NoNameTagTeam = scoreboard.getTeam(NoTagTeamName);
		if (NoNameTagTeam == null) {
			scoreboard.registerNewTeam(NoTagTeamName);
			NoNameTagTeam = scoreboard.getTeam(NoTagTeamName);
			NoNameTagTeam.setNameTagVisibility(NameTagVisibility.NEVER);
		}
		NoNameTagTeam = scoreboard.getTeam(NoTagTeamName);
		for (Player player : Bukkit.getOnlinePlayers())
			NoNameTagTeam.addEntry(player.getDisplayName());
	}

	private void enableNameTags() {
		Team NoNameTagTeam = scoreboard.getTeam(NoTagTeamName);
		if (NoNameTagTeam == null) {
			scoreboard.registerNewTeam(NoTagTeamName);
			NoNameTagTeam = scoreboard.getTeam(NoTagTeamName);
			NoNameTagTeam.setNameTagVisibility(NameTagVisibility.ALWAYS);
		}
		NoNameTagTeam = scoreboard.getTeam(NoTagTeamName);
		for (Player player : Bukkit.getOnlinePlayers())
			NoNameTagTeam.addEntry(player.getDisplayName());
	}

}
