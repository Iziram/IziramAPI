package fr.iziram.API.Internal.other;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class ClickableItem {

	private Consumer<InventoryClickEvent> action;
	private ItemStack item;
	private ItemCategory category;

	public ClickableItem() {
		this.item = null;
		this.action = null;
		this.category = ItemCategory.NULL;
	}

	public ItemStack getItem() {
		return item;
	}

	public ClickableItem setItem(ItemStack item) {
		this.item = item;
		return this;
	}

	public Consumer<InventoryClickEvent> getAction() {
		return action;
	}

	public ClickableItem setAction(Consumer<InventoryClickEvent> action) {
		this.action = action;
		return this;
	}

	public ItemCategory getCategory() {
		return category;
	}

	public ClickableItem setCategory(ItemCategory category) {
		this.category = category;
		return this;
	}

	@Override
	public String toString() {
		return "ClickableItem{" +
				"action=" + action +
				", item=" + item +
				", category=" + category +
				'}';
	}

	public enum ItemCategory {
		CLICKABLE,
		PLACEHOLDER,
		RETURN,
		NULL
	}
}
