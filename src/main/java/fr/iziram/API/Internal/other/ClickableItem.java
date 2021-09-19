package fr.iziram.API.Internal.other;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

/**
 * Cette classe permet de créer des objets cliquables utilisables dans les menus.
 * Voir utilisation sur le Wiki
 */
public class ClickableItem {

	private Consumer<InventoryClickEvent> action;
	private ItemStack item;
	private ItemCategory category;

	/**
	 * Initialise l'objet cliquable
	 */
	public ClickableItem() {
		this.item = null;
		this.action = null;
		this.category = ItemCategory.NULL;
	}

	/**
	 * Cette fonction renvoie l'itemStack qui sera Affiché
	 * @return → ItemStack qui sera affiché
	 */
	public ItemStack getItem() {
		return item;
	}
	/**
	 * Cette fonction change l'itemStack qui sera Affiché
	 * @param item → ItemStack qui sera affiché
	 * @return ClickableItem → renvoie l'instance ClickableItem afin de pouvoir enchaîner les instructions (voir wiki)
	 */
	public ClickableItem setItem(ItemStack item) {
		this.item = item;
		return this;
	}

	public Consumer<InventoryClickEvent> getAction() {
		return action;
	}
	/**
	 * Cette fonction change l'action qui sera effectuée lorsqu'un joueur cliquera sur l'objet (si l'objet est en mode
	 * Clickable)
	 * @param action → Consumer utilisant l'InventoryClickEvent
	 * @return ClickableItem → renvoie l'instance ClickableItem afin de pouvoir enchaîner les instructions (voir wiki)
	 */
	public ClickableItem setAction(Consumer<InventoryClickEvent> action) {
		this.action = action;
		return this;
	}

	/**
	 * Cette fonction renvoie la catégorie de l'objet cliquable (PlaceHolder, Return, Clickable)
	 * @return ItemCategory → categories de l'objet cliquable
	 */
	public ItemCategory getCategory() {
		return category;
	}

	/**
	 * Cette fonction permet de changer la catégorie de l'objet cliquable (PlaceHolder, Return, Clickable)
	 * @param category → Catégorie de l'objet cliquable
	 * @return ClickableItem → renvoie l'instance ClickableItem afin de pouvoir enchaîner les instructions (voir wiki)
	 */
	public ClickableItem setCategory(ItemCategory category) {
		this.category = category;
		return this;
	}

	/**
	 * Représentation des objets cliquables en chaine de caractères
	 */
	@Override
	public String toString() {
		return "ClickableItem{" +
				"action=" + action +
				", item=" + item +
				", category=" + category +
				'}';
	}

	/**
	 * Les catégories d'objets cliquables
	 */
	public enum ItemCategory {
		CLICKABLE,
		PLACEHOLDER,
		RETURN,
		NULL
	}
}
