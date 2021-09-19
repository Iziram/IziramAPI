package fr.iziram.API.Internal.scoreboard;

import fr.iziram.API.Utils.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;

public class TeamsRegister {

	private final HashMap<String, Teams> teamsList = new HashMap<>();
	private final Scoreboard scoreboard;

	public TeamsRegister(Scoreboard scoreboard) {
		this.scoreboard = scoreboard;
	}


	public void generateTeams(int number, int max, boolean friendlyFire, boolean nametag) {
		for (Team team : this.scoreboard.wrapper.getScoreboard().getTeams()) {
			team.unregister();
		}
		String[] names = {"Rouge", "Jaune", "Vert", "Bleu", "Rose", "Blanc", "Noir",
				"Rouge ❤", "Or", "Vert ❤", "Bleu ❤", "Violet", "Gris", "Gris ❤"};
		String[] prefixs = {"§c[Rouge]§r", "§e[Jaune]§r", "§a[Vert]§r", "§9[Bleu]§r", "§d[Rose]§r", "§f[Blanc]§r",
				"§0[Noir]§r", "§4[Rouge ❤]§r", "§6[Or]§r", "§2[Vert ❤]§r", "§1[Bleu ❤]§r", "§5[Violet]§r",
				"§7[Gris]§r", "§8[Gris ❤]§r"};
		if (number > names.length) {
			number = names.length;
		}

		for (int i = 0; i < number; i++) {
			Teams temp = new Teams(max, this.scoreboard, names[i]);
			temp.setNameTagVisibility(nametag);
			temp.setPrefix(prefixs[i] + " ");
			temp.setFriendlyFire(friendlyFire);

			teamsList.put(temp.getName(), temp);
		}
	}

	public HashMap<String, Teams> getTeamsList() {
		return teamsList;
	}
}
