package fr.iziram.API.events;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TimePassesEvent extends Event {

	private static final HandlerList HANDLERS = new HandlerList();
	private final int time;
	private final int episode;

	public TimePassesEvent(int time, int episode) {
		this.time = time;
		this.episode = episode;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS;
	}

	public static String timeFormatter(int time) {
		int minutes = time / 60;
		int seconds = time % 60;
		return String.format("%d : %02d", minutes, seconds);
	}

	public static void broadCastDay(long time, String prefix) {
		if (time == 0 || time == 24000) Bukkit.broadcastMessage(prefix + "§eLe jour se lève.");
		else if (time == 12000) Bukkit.broadcastMessage(prefix + "§9La nuit tombe");
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}

	public int getEpisode() {
		return episode;
	}

	public int getTime() {
		return time;
	}


}
