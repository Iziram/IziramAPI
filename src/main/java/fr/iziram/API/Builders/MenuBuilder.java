package fr.iziram.API.Builders;

import fr.iziram.API.Internal.ClickableItem;
import fr.iziram.API.Registers.MenuRegister;
import fr.iziram.API.Utils.Menu;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class MenuBuilder {

	private final HashMap<Integer, ClickableItem> itemHashMap = new HashMap<>();
	private String menuTitle;
	private int menuSize;
	private boolean autoCancel;
	private boolean autoExit;
	private Menu previousMenu;
	private Consumer<InventoryCloseEvent> closeAction;
	private UUID user;


	public MenuBuilder() {
		menuTitle = "Undefined";
		menuSize = 1;
		autoCancel = false;
		autoExit = false;
		previousMenu = null;
		closeAction = (event) -> {
		};
	}

	public MenuBuilder setTitle(String data) {
		menuTitle = data;
		return this;
	}

	public MenuBuilder setCloseAction(Consumer<InventoryCloseEvent> data) {
		closeAction = data;
		return this;
	}

	public MenuBuilder setAutoCancel(boolean data) {
		autoCancel = data;
		return this;
	}

	public MenuBuilder setAutoExit(boolean data) {
		autoExit = data;
		return this;
	}

	public MenuBuilder setSize(int data) {
		menuSize = data;
		return this;
	}

	public MenuBuilder setPreviousMenu(Menu menu) {
		previousMenu = menu;
		return this;
	}

	public MenuBuilder setClickableItem(int x, int y, ClickableItem clickableItem) {

		final int place = x + y * 9;

		if (itemHashMap.containsKey(place)) itemHashMap.replace(place, clickableItem);
		else itemHashMap.put(place, clickableItem);
		return this;
	}

	public MenuBuilder setClickableItem(int place, ClickableItem clickableItem) {
		if (itemHashMap.containsKey(place)) {
			itemHashMap.replace(place, clickableItem);
		} else itemHashMap.put(place, clickableItem);
		return this;
	}

	public MenuBuilder removeClickableItem(int x, int y) {
		final int place = x + y * 9;
		itemHashMap.remove(place);
		return this;
	}

	public ClickableItem getClickableItem(int x, int y) {
		final int place = x + y * 9;
		return itemHashMap.get(place);
	}

	public MenuBuilder setRowClickableItem(int row, ClickableItem item) {
		for (int i = 0; i < 9; i++) setClickableItem(i, row, item);
		return this;
	}

	public MenuBuilder setColumnClickableItem(int column, ClickableItem item) {
		for (int i = 0; i < menuSize; i++) setClickableItem(column, i, item);
		return this;
	}

	public MenuBuilder setMultipleClickableItem(HashMap<Integer, ClickableItem> clickableItem) {
		clickableItem.forEach(this::setClickableItem);
		return this;
	}

	public MenuBuilder addItemStackAsPlaceHolders(ItemStack... itemStacks) {
		int place = 0;
		for (ItemStack i : itemStacks) {
			ClickableItem c = new ClickableItem().setItem(i).setCategory(ClickableItem.ItemCategory.PLACEHOLDER);
			setClickableItem(place, c);
			place++;
		}
		return this;
	}

	public MenuBuilder addItemStackAsPlaceHolders(List<ItemStack> itemStacks) {
		int place = 0;
		for (ItemStack i : itemStacks) {
			ClickableItem c = new ClickableItem().setItem(i).setCategory(ClickableItem.ItemCategory.PLACEHOLDER);
			setClickableItem(place, c);
			place++;
		}
		return this;
	}

	public Menu build(String basic) {
		Menu menu = new Menu(menuTitle, menuSize, itemHashMap, autoCancel, autoExit, previousMenu, closeAction, user);
		MenuRegister.addMenu(menu, basic);
		return menu;
	}

	public Menu update(Menu menu) {
		return menu.update(menuTitle, menuSize, itemHashMap, autoCancel, autoExit, previousMenu, closeAction, user);
	}

	public String getMenuTitle() {
		return menuTitle;
	}

	public String getUser() {
		if (user != null) return user.toString();
		else return "";
	}

	public MenuBuilder setUser(UUID data) {
		user = data;
		return this;
	}
}
