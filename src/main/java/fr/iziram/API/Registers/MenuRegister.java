package fr.iziram.API.Registers;

import fr.iziram.API.Utils.Menu;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;

/**
 * Cette classe est le Register utilisé par le MenuBuilder pour stocker les Menus
 * Voir Utilisation sur le Wiki
 */
public class MenuRegister implements Listener {

	private static MenuRegister instance;
	private final HashMap<String, Menu> menuMap = new HashMap<>();
	private final HashMap<String, Menu> basicMap = new HashMap<>();

	/**
	 * Initialisation du plugin
	 * @param plugin → Instance du plugin utilisant l'api
	 */
	public MenuRegister(Plugin plugin) {
		instance = this;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	/**
	 * Renvoie le MenuRegister
	 * @return MenuRegister → renvoir le register de menus
	 */
	public static MenuRegister getInstance() {
		return instance;
	}

	/**
	 * Permet d'ajouter un menu au register basic
	 * @param menu → Menu généré par le MenuBuilder
	 * @param basic → nom basic attribué au menu
	 */
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
	/**
	 * Permet d'ajouter un menu au register
	 * @param menu → Menu généré par le MenuBuilder
	 */
	public static void addMenu(Menu menu) {
		String key = menu.getMenuTitle() + menu.getUser();
		if (getInstance().menuMap.containsKey(key)) {
			getInstance().menuMap.replace(key, menu);
		} else {
			getInstance().menuMap.put(key, menu);
		}
	}

	/**
	 * Permet de retirer un menu du register
	 * @param key → nom du menu
	 */
	public static void removeMenu(String key) {
		getInstance().menuMap.remove(key);
	}
	/**
	 * Permet de retirer un menu du register
	 * @param key → nom du menu
	 * @param basic → nom basic attribué au menu
	 */
	public static void removeMenu(String key, String basic) {
		getInstance().menuMap.remove(key);
		getInstance().basicMap.remove(basic);
	}

	/**
	 * Renvoie un Menu en fonction d'une clé
	 * @param key → nom du menu
	 * @return Menu → Renvoie null si aucun menu n'est trouvé
	 */
	public static Menu getMenu(String key) {
		return getInstance().menuMap.get(key);
	}
	/**
	 * Renvoie un Menu en fonction d'une clé basic
	 * @param basic → nom du menu
	 * @return Menu → Renvoie null si aucun menu n'est trouvé
	 */
	public static Menu getBasicMenu(String basic) {
		return getInstance().basicMap.get(basic);
	}

	/**
	 * Gère les intéractions des menus
	 */
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
	/**
	 * Gère les fermetures de menus
	 */
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		String title = event.getInventory().getTitle();
		String uuid = event.getPlayer().getUniqueId().toString();
		Menu menu = getMenu(title + uuid);
		if (menu == null) menu = getMenu(title);
		if (menu != null) {
			menu.getCloseAction().accept(event);
		}
	}


}
