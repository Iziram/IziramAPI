package fr.iziram.API.Registers;

import fr.iziram.API.Utils.Menu;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;


public class MenuRegister implements Listener {

	private static MenuRegister instance;
	private final HashMap<String, Menu> menuMap = new HashMap<>();
	private final HashMap<String, Menu> basicMap = new HashMap<>();


	public MenuRegister(Plugin plugin) {
		instance = this;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	public static MenuRegister getInstance() {
		return instance;
	}

	public static void addMenu(Menu menu, String basic) {
		String key = menu.getMenuTitle() + menu.getUser();
		if (getInstance().menuMap.containsKey(key)) {
			getInstance().menuMap.replace(key, menu);
		} else {
			getInstance().menuMap.put(key, menu);
		}

		if (getInstance().basicMap.containsKey(basic)) {
			getInstance().basicMap.replace(basic, menu);
		} else {
			getInstance().basicMap.put(basic, menu);
		}

	}

	public static void addMenu(Menu menu) {
		String key = menu.getMenuTitle() + menu.getUser();
		if (getInstance().menuMap.containsKey(key)) {
			getInstance().menuMap.replace(key, menu);
		} else {
			getInstance().menuMap.put(key, menu);
		}
	}

	public static void removeMenu(String key) {
		getInstance().menuMap.remove(key);
	}

	public static void removeMenu(String key, String basic) {
		getInstance().menuMap.remove(key);
		getInstance().basicMap.remove(basic);
	}

	public static Menu getMenu(String key) {
		return getInstance().menuMap.get(key);
	}

	public static Menu getBasicMenu(String basic) {
		return getInstance().basicMap.get(basic);
	}

	@EventHandler
	public void onInventoryInteract(InventoryClickEvent event) {
		if (event.getClickedInventory() == null) return;
		String title = event.getClickedInventory().getTitle();
		String uuid = event.getWhoClicked().getUniqueId().toString();

		Menu menu = getMenu(title + uuid);
		if (menu == null) menu = getMenu(title);
		if (menu != null) {
			menu.getClickAction().accept(event);
		}
	}

	@EventHandler
	public void onInventoryInteract(InventoryCloseEvent event) {
		String title = event.getInventory().getTitle();
		String uuid = event.getPlayer().getUniqueId().toString();
		Menu menu = getMenu(title + uuid);
		if (menu == null) menu = getMenu(title);
		if (menu != null) {
			menu.getCloseAction().accept(event);
		}
	}


}
