package fr.iziram.API.Internal.scoreboard;

import fr.iziram.API.Utils.Scoreboard;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Team;

/**
 * Classe permettant de gérer une équipe.
 * Voir l'utilisation sur le wiki
 */
public class Teams {
	private final int max;
	private final Scoreboard scoreboard;
	private final String name;
	private Team team;

	/**
	 * Initialisation de la team
	 * @param max → Nombre de joueur maximum
	 * @param scoreboard → scoreboard qui utilisera l'équipe
	 * @param name → nom de l'équipe
	 */
	public Teams(int max, Scoreboard scoreboard, String name) {
		this.max = max;
		this.scoreboard = scoreboard;
		this.name = name;
		create();
	}

	/**
	 * Cette fonction permet de créer l'équipe dans le scoreboard
	 */
	public void create() {
		this.team = scoreboard.wrapper.getScoreboard().registerNewTeam(name);
	}

	/**
	 * Cette fonction permet d'ajouter un joueur à l'équipe
	 * @param p → le joueur
	 * @return Boolean → Renvoie True si le joueur a été ajouté, False sinon
	 */
	public boolean addPlayer(Player p) {
		if (team.getEntries().size() < max) this.team.addEntry(p.getName());
		else return false;
		return true;
	}
	/**
	 * Cette fonction permet de retirer un joueur à l'équipe
	 * @param p → le joueur
	 * @return Boolean → Renvoie True si le joueur a été retiré, False sinon
	 */
	public boolean removePlayer(Player p) {
		if (team.hasEntry(p.getName())) {
			team.removeEntry(p.getName());
			return true;
		}
		return false;
	}

	/**
	 * Permet d'ajouter un suffix à l'équipe (visible dans le tab)
	 * @param suffix → String
	 */
	public void setSuffix(String suffix) {
		team.setPrefix(suffix);
	}
	/**
	 * Permet de changer le nom d'affichage de l'équipe
	 * @param s → String
	 */
	public void setDisplayName(String s) {
		team.setDisplayName(s);
	}

	/**
	 * Permet de changer la visibilité du nametag
	 * @param nametag → True si on voit le nametag, False sinon
	 */
	public void setNameTagVisibility(boolean nametag) {
		if (nametag)
			team.setNameTagVisibility(NameTagVisibility.ALWAYS);
		else
			team.setNameTagVisibility(NameTagVisibility.NEVER);

	}
	/**
	 * Permet de changer le FriendlyFire de l'équipe
	 * @param ff → True si c'est actif, False sinon
	 */
	public void setFriendlyFire(boolean ff) {
		team.setAllowFriendlyFire(ff);
	}

	/**
	 * Renvoie le préfix de l'équipe
	 * @return String → préfix de l'équipe
	 */
	public String getPrefix() {
		return team.getPrefix();
	}
	/**
	 * Permet d'ajouter un prefix à l'équipe (visible dans le tab)
	 * @param prefix → String
	 */
	public void setPrefix(String prefix) {
		team.setPrefix(prefix);
	}

	/**
	 * Renvoie le nom de l'équipe
	 * @return String → Nom de l'équipe
	 */
	public String getName() {
		return name;
	}

}
