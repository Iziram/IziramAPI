package fr.iziram.API.Utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldBorder;

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


	public BorderManager() {
		world = null;
		center = new Location(world, 0, 0, 0);
		damage = 1;
		borderSizeBefore = 10000;
		borderSizeAfter = 10000;
		started = false;
		speed = 1.5d;
	}

	public String getFinishMessage() {
		return finishMessage;
	}

	public BorderManager setFinishMessage(String finishMessage) {
		this.finishMessage = finishMessage;
		return this;
	}

	public String getStartMessage() {
		return startMessage;
	}

	public BorderManager setStartMessage(String startMessage) {
		this.startMessage = startMessage;
		return this;
	}

	public int getBorderSizeAfter() {
		return borderSizeAfter;
	}

	public BorderManager setBorderSizeAfter(int borderSizeAfter) {
		this.borderSizeAfter = borderSizeAfter;
		return this;
	}

	public boolean isStarted() {
		return started;
	}

	public BorderManager setStarted(boolean started) {
		this.started = started;
		return this;
	}

	public int getBorderSizeBefore() {
		return borderSizeBefore;
	}

	public BorderManager setBorderSizeBefore(int borderSizeBefore) {
		this.borderSizeBefore = borderSizeBefore;
		return this;
	}

	public int getDamage() {
		return damage;
	}

	public BorderManager setDamage(int damage) {
		this.damage = damage;
		return this;
	}

	public Location getCenter() {
		return center;
	}

	public BorderManager setCenter(Location center) {
		this.center = center;
		return this;
	}

	public World getWorld() {
		return world;
	}

	public BorderManager setWorld(World world) {
		this.world = world;
		return this;
	}

	public double getSpeed() {
		return speed;
	}

	public BorderManager setSpeed(double speed) {
		this.speed = speed;
		return this;
	}

	public BorderManager setWorldBorder() {
		WorldBorder worldBorder = world.getWorldBorder();
		worldBorder.setCenter(center);
		worldBorder.setDamageAmount(damage);
		worldBorder.setSize(borderSizeBefore * 2);
		started = false;
		return this;
	}

	private void updateSize(double size) {
		WorldBorder worldBorder = world.getWorldBorder();
		worldBorder.setSize(size);
	}

	public Double getSize() {
		WorldBorder worldBorder = world.getWorldBorder();
		return worldBorder.getSize();
	}

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

	public void stopWorldBorder() {
		started = false;
	}

}
