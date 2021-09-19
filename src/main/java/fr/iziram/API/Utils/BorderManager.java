package fr.iziram.API.Utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldBorder;

/**
 * Cette classe permet de gérer la bordure d'un monde
 */
public class BorderManager {

	private World world;
	private Location center;
	private int damage;
	private int borderSizeBefore;
	private int borderSizeAfter;
	private boolean started = false;
	private double speed = 1.5d;
	private int task = -1;
	private String startMessage = "";
	private String finishMessage = "";

	/**
	 * Initialisation du border manager
	 */
	public BorderManager() {
		this.world = null;
		center = new Location(null, 0, 0, 0);
		damage = 1;
		borderSizeBefore = 10000;
		borderSizeAfter = 10000;
		started = false;
		speed = 1.5d;
	}

	/**
	 * Renvoie le message qui sera affiché lorsque la border aura fini de bouger
	 * @return String → le message
	 */
	public String getFinishMessage() {
		return finishMessage;
	}

	/**
	 * Permet de modifier le message de fin de mouvement de la border
	 * @param finishMessage → le message
	 * @return → Renvoie le BorderManager afin d'enchaîner les instructions (voir Wiki)
	 */
	public BorderManager setFinishMessage(String finishMessage) {
		this.finishMessage = finishMessage;
		return this;
	}

	/**
	 * Renvoie le message qui sera affiché lorsque la border commencera à bouger
	 * @return String → le message
	 */
	public String getStartMessage() {
		return startMessage;
	}

	/**
	 * Permet de modifier le message de debut de mouvement de la border
	 * @param startMessage → le message
	 * @return → Renvoie le BorderManager afin d'enchaîner les instructions (voir Wiki)
	 */
	public BorderManager setStartMessage(String startMessage) {
		this.startMessage = startMessage;
		return this;
	}

	/**
	 * Renvoie la taille de la border à laquelle elle sera quand elle aura fini
	 * @return int → La taille
	 */
	public int getBorderSizeAfter() {
		return borderSizeAfter;
	}

	/**
	 * Permet de modifier la taille de fin de la border
	 * @param borderSizeAfter → la taille de fin
	 * @return → Renvoie le BorderManager afin d'enchaîner les instructions (voir Wiki)
	 */
	public BorderManager setBorderSizeAfter(int borderSizeAfter) {
		this.borderSizeAfter = borderSizeAfter;
		return this;
	}

	/**
	 * Renvoie True si la bordure à commencer à bouger
	 * @return → Boolean
	 */
	public boolean isStarted() {
		return started;
	}

	/**
	 * Renvoie la taille de la border à laquelle elle sera quand elle commencera
	 * @return int → La taille
	 */
	public int getBorderSizeBefore() {
		return borderSizeBefore;
	}

	/**
	 * Permet de modifier la taille de début de la border
	 * @param borderSizeBefore → la taille de début
	 * @return → Renvoie le BorderManager afin d'enchaîner les instructions (voir Wiki)
	 */
	public BorderManager setBorderSizeBefore(int borderSizeBefore) {
		this.borderSizeBefore = borderSizeBefore;
		return this;
	}
	/**
	 * Renvoie les dégâts affectués par la border
	 * @return → int
	 */
	public int getDamage() {
		return damage;
	}

	/**
	 * Permet de changer les dégâts effectués par la border
	 * @param damage → Les dégâts
	 * @return → Renvoie le BorderManager afin d'enchaîner les instructions (voir Wiki)
	 */
	public BorderManager setDamage(int damage) {
		this.damage = damage;
		return this;
	}
	/**
	 * Renvoie le centre de la border
	 * @return → Location
	 */
	public Location getCenter() {
		return center;
	}

	/**
	 * Permet de changer le centre de la border
	 * @param center → le centre de la border
	 * @return → Renvoie le BorderManager afin d'enchaîner les instructions (voir Wiki)
	 */
	public BorderManager setCenter(Location center) {
		this.center = center;
		return this;
	}

	/**
	 * Renvoie le monde de la border
	 * @return World
	 */
	public World getWorld() {
		return world;
	}

	/**
	 * Permet de changer le monde de la border
	 * @param world → Le monde
	 * @return → Renvoie le BorderManager afin d'enchaîner les instructions (voir Wiki)
	 */
	public BorderManager setWorld(World world) {
		this.world = world;
		return this;
	}
	/**
	 * Renvoie la vitesse de la border (en block par seconde)
	 * @return World
	 */
	public double getSpeed() {
		return speed;
	}
	/**
	 * Permet de changer la vitesse de la border
	 * @param speed → La vitesse en block par secondes
	 * @return → Renvoie le BorderManager afin d'enchaîner les instructions (voir Wiki)
	 */
	public BorderManager setSpeed(double speed) {
		this.speed = speed;
		return this;
	}
	/**
	 * Permet de mettre en place la border
	 */
	public BorderManager setWorldBorder() {
		WorldBorder worldBorder = world.getWorldBorder();
		worldBorder.setCenter(center);
		worldBorder.setDamageAmount(damage);
		worldBorder.setSize(borderSizeBefore * 2);
		started = false;
		return this;
	}

	/**
	 * Permet de changer la taille de la border
	 * @param size → Taille de la border
	 */
	private void updateSize(double size) {
		WorldBorder worldBorder = world.getWorldBorder();
		worldBorder.setSize(size);
	}

	/**
	 * Renvoie la taille de la border
	 * @return Taille de la border
	 */
	public Double getSize() {
		WorldBorder worldBorder = world.getWorldBorder();
		return worldBorder.getSize();
	}

	/**
	 * Permet de lancer la border
	 */
	public void startWorldBorder() {
		if (started) return;
		started = true;
		Bukkit.broadcastMessage(getStartMessage());
		double blockPerTick = speed / 20d;
		try {
			Timer border = new Timer("worldborder." + world.getName(), 0, 1);
			border.setRunnable(() -> {
				if (started && getSize() > getBorderSizeAfter()) {
					updateSize(getSize() - blockPerTick);
				} else {
					Bukkit.broadcastMessage(getFinishMessage());
					border.stop();
				}
				border.elapsed++;
			});
			border.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Permet de stopper la border
	 */
	public void stopWorldBorder() {
		started = false;
	}

}
