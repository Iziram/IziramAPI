package fr.iziram.API.events;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Cette classe ajoute un nouvel event qui permet de faciliter l'utilisation d'une boucle temporelle pour les modes de jeux
 */
public class TimePassesEvent extends Event {

	private static final HandlerList HANDLERS = new HandlerList();
	private final int time;
	private final int episode;

	/**
	 * Pour que l'event puisse fonctionner correctement il faut lui donner le temps actuel et le numéro d'épisode.
	 * @param time → temps
	 * @param episode → épisode
	 */
	public TimePassesEvent(int time, int episode) {
		this.time = time;
		this.episode = episode;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS;
	}

	/**
	 * Cette fonction permet de convertir un temps en seconde à un temps en minutes : secondes
	 * @param time → temps en seconde
	 * @return → String représentant le temps en minutes : secondes
	 */
	public static String timeFormatter(int time) {
		int minutes = time / 60;
		int seconds = time % 60;
		return String.format("%d : %02d", minutes, seconds);
	}

	/**
	 * Cette fonction permet d'afficher un message lorsque le jour se lève et lorsqu'il se couche. Le message n'est pas modifiable
	 * mais le préfix l'est.
	 * @param time → temps relatif de minecraft
	 * @param prefix → préfix à utiliser
	 */
	public static void broadCastDay(long time, String prefix) {
		if(prefix == null) prefix = "";
		if (time == 0 || time == 24000) Bukkit.broadcastMessage(prefix + "§eLe jour se lève.");
		else if (time == 12000) Bukkit.broadcastMessage(prefix + "§9La nuit tombe");
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}

	/**
	 * Cette fonction renvoie l'épisode
	 * @return → l'épisode
	 */
	public int getEpisode() {
		return episode;
	}
	/**
	 * Cette fonction renvoie le temps
	 * @return → temps
	 */
	public int getTime() {
		return time;
	}


}
