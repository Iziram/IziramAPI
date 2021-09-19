package fr.iziram.API.Utils;

import fr.iziram.API.Internal.ClickableItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;
import java.util.function.Consumer;

public class Menu implements Listener {
	private static JavaPlugin plugin;
	private String menuTitle;
	private int menuSize;
	private HashMap<Integer, ClickableItem> itemHashMap;
	private boolean autoCancel;
	private boolean autoExit;
	private Menu previousMenu;
	private Consumer<InventoryCloseEvent> closeAction;
	private UUID user;


	public Menu(String menuTitle, int menuSize, HashMap<Integer, ClickableItem> itemHashMap, boolean autoCancel, boolean autoExit, Menu previousMenu, Consumer<InventoryCloseEvent> closeAction, UUID user) {
		this.menuTitle = menuTitle;
		this.menuSize = menuSize;
		this.itemHashMap = itemHashMap;
		this.autoCancel = autoCancel;
		this.autoExit = autoExit;
		this.previousMenu = previousMenu;
		this.closeAction = closeAction;
		this.user = user;
	}

	public Menu update(String menuTitle, int menuSize, HashMap<Integer, ClickableItem> itemHashMap, boolean autoCancel, boolean autoExit, Menu previousMenu, Consumer<InventoryCloseEvent> closeAction, UUID user) {
		this.menuTitle = menuTitle;
		this.menuSize = menuSize;
		this.itemHashMap = itemHashMap;
		this.autoCancel = autoCancel;
		this.autoExit = autoExit;
		this.previousMenu = previousMenu;
		this.closeAction = closeAction;
		this.user = user;
		return this;
	}

	public Menu unRegisterEvent() {
		HandlerList.unregisterAll(this);
		return this;
	}


	public Menu registerEvent() {
		unRegisterEvent();
		Bukkit.getPluginManager().registerEvents(this, plugin);
		return this;
	}

	public String getMenuTitle() {
		return menuTitle;
	}

	public String getUser() {
		if (user != null) return user.toString();
		else return "";
	}

	public Inventory buildInventory() {
		Inventory menuInventory = Bukkit.createInventory(null, menuSize * 9, menuTitle);
		itemHashMap.forEach((i, c) -> {
			if (c.getItem() != null && c.getItem().getType() != Material.AIR) menuInventory.setItem(i, c.getItem());
		});
		return menuInventory;
	}

	public Consumer<InventoryClickEvent> getClickAction() {
		return (event) -> {
			if (event.getClickedInventory() != null && event.getClickedInventory().getTitle().equals(menuTitle)
					&& event.getCurrentItem() != null) {
				event.setCancelled(autoCancel);
				int slot = event.getSlot();
				ClickableItem clickableItem = itemHashMap.get(slot);
				if (clickableItem != null) {
					if (clickableItem.getCategory() != ClickableItem.ItemCategory.NULL) {
						if (autoExit) {
							event.getWhoClicked().closeInventory();
						}
						if (!autoCancel) event.setCancelled(true);
						if (clickableItem.getCategory() == ClickableItem.ItemCategory.CLICKABLE) {
							clickableItem.getAction().accept(event);
						}
						if (clickableItem.getCategory() == ClickableItem.ItemCategory.RETURN) {
							if (previousMenu != null) {
								event.getWhoClicked().openInventory(previousMenu.buildInventory());
							} else {
								event.getWhoClicked().closeInventory();
							}
						}
					}
				}
			}
		};
	}

	public Consumer<InventoryCloseEvent> getCloseAction() {
		return (e) -> {
			if (e.getInventory().getTitle().equalsIgnoreCase(menuTitle)) {
				if (closeAction != null) closeAction.accept(e);
			}
		};
	}

}
