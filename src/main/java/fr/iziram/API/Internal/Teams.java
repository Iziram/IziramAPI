package fr.iziram.API.Internal;

import fr.iziram.API.Utils.Scoreboard;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Team;

public class Teams {
	private final int max;
	private final Scoreboard scoreboard;
	private final String name;
	private Team team;

	public Teams(int max, Scoreboard scoreboard, String name) {
		this.max = max;
		this.scoreboard = scoreboard;
		this.name = name;
		create();
	}

	public void create() {
		this.team = scoreboard.wrapper.getScoreboard().registerNewTeam(name);
	}

	public boolean addPlayer(Player p) {
		if (team.getEntries().size() < max) this.team.addEntry(p.getName());
		else return false;
		return true;
	}

	public boolean removePlayer(Player p) {
		if (team.hasEntry(p.getName())) {
			team.removeEntry(p.getName());
			return true;
		}
		return false;
	}

	public void setSuffix(String suffix) {
		team.setPrefix(suffix);
	}

	public void setDisplayName(String s) {
		team.setDisplayName(s);
	}

	public void setNameTagVisibility(boolean nametag) {
		if (nametag)
			team.setNameTagVisibility(NameTagVisibility.ALWAYS);
		else
			team.setNameTagVisibility(NameTagVisibility.NEVER);

	}

	public void setFriendlyFire(boolean ff) {
		team.setAllowFriendlyFire(ff);
	}

	public String getPrefix() {
		return team.getPrefix();
	}

	public void setPrefix(String prefix) {
		team.setPrefix(prefix);
	}

	public String getName() {
		return name;
	}

}
