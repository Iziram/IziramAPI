package fr.iziram.API.Internal.scoreboard;

import fr.iziram.API.Utils.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;

/**
 * Cette classe permet générer et gérer plusieurs équipes dans un même scoreboard
 */
public class TeamsRegister {

	private final HashMap<String, Teams> teamsList = new HashMap<>();
	private final Scoreboard scoreboard;

	/**
	 * Initialisation du Register
	 * @param scoreboard → Scoreboard qui sera utilisé par les équipes
	 */
	public TeamsRegister(Scoreboard scoreboard) {
		this.scoreboard = scoreboard;
	}

	/**
	 * Génère les équipes en fonction des paramètres donnés
	 * @param number → nombre d'équipe à générer
	 * @param max → maximum de joueur par équipe
	 * @param friendlyFire → si le friendlyfire est actif
	 * @param nametag → si les nametag sont visibles
	 */
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

	/**
	 * Renvoie la liste des équipes qui ont été générées
	 * @return Hashmap → Map avec comme clé les noms des équipes et comme valeurs les équipes
	 */
	public HashMap<String, Teams> getTeamsList() {
		return teamsList;
	}
}
