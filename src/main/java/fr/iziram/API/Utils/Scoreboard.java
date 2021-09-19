package fr.iziram.API.Utils;

import fr.iziram.API.Internal.scoreboard.ScoreboardWrapper;
import fr.iziram.API.Internal.scoreboard.TeamsRegister;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Classe simplifiant l'utilisation des scoreboard
 */
public class Scoreboard {
	public ScoreboardWrapper wrapper;
	public TeamsRegister teamsRegister;

	/**
	 * Initialisation du scoreboard
	 */
	public Scoreboard(String title) {
		wrapper = new ScoreboardWrapper(title);
		teamsRegister = new TeamsRegister(this);
	}

	/**
	 * Supprimer l'affichage du scoreboard
	 */
	public static void remove() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
		}
	}

	/**
	 * Affiche le scoreboard à tous
	 */
	public void show() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.setScoreboard(wrapper.getScoreboard());
		}
	}

	/**
	 * Affiche le scoreboard au joueur
	 * @param p → le joueur
	 */
	public void show(Player p) {
		p.setScoreboard(wrapper.getScoreboard());
	}

	/**
	 * Change le titre du scoreboard
	 * @param title → le titre
	 * @return Boolean → renvoie False si le titre dépasse les 32 caractères sinon True
	 */
	public boolean changeTitle(String title) {
		if (title.length() <= 32) wrapper.setTitle(title);
		return title.length() <= 32;
	}

	/**
	 * Permet de changer une ligne du scoreboard
	 * @param index → index de la ligne
	 * @param text → texte de la ligne
	 */
	public void updateScoreboard(int index, String text) {
		wrapper.setLine(index, text);
	}


}
