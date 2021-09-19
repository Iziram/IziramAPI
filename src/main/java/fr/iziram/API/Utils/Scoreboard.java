package fr.iziram.API.Utils;

import fr.iziram.API.Internal.ScoreboardWrapper;
import fr.iziram.API.Internal.TeamsRegister;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Scoreboard {
	public ScoreboardWrapper wrapper;
	public TeamsRegister teamsRegister;

	public Scoreboard(String title) {
		wrapper = new ScoreboardWrapper(title);
		teamsRegister = new TeamsRegister(this);
	}

	public static void remove() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
		}
	}

	public void show() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.setScoreboard(wrapper.getScoreboard());
		}
	}

	public void show(Player p) {
		p.setScoreboard(wrapper.getScoreboard());
	}

	public boolean changeTitle(String title) {
		if (title.length() <= 32) wrapper.setTitle(title);
		return title.length() <= 32;
	}

	public void updateScoreboard(int index, String text) {
		wrapper.setLine(index, text);
	}


}
