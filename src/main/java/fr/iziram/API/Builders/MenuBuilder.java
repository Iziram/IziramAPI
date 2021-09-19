package fr.iziram.API.Builders;

import fr.iziram.API.Internal.other.ClickableItem;
import fr.iziram.API.Registers.MenuRegister;
import fr.iziram.API.Utils.Menu;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

/**
 Ce builder permet de créer facilement des menus (inventaires minecraft) customisables et intéractifs
 */
public class MenuBuilder {
	private final HashMap<Integer, ClickableItem> itemHashMap = new HashMap<>();
	private String menuTitle;
	private int menuSize;
	private boolean autoCancel;
	private boolean autoExit;
	private Menu previousMenu;
	private Consumer<InventoryCloseEvent> closeAction;
	private UUID user;

	/**
	 * Initialise le MenuBuilder avec des valeurs par défaut
	 */
	public MenuBuilder() {
		menuTitle = "Undefined";
		menuSize = 1;
		autoCancel = false;
		autoExit = false;
		previousMenu = null;
		closeAction = (event) -> {
		};
	}

	/**
	 * Cette fonction permet de changer le nom du menu
	 * L'état par défaut est "Undefined"
	 * @param data → Nouveau nom du menu
	 * @return MenuBuilder → renvoie l'instance MenuBuilder afin de pouvoir enchaîner les instructions (voir wiki)
	 */
	public MenuBuilder setTitle(String data) {
		menuTitle = data;
		return this;
	}

	/**
	 * Cette fonction permet d'assigner une action à effectuer lorsque le menu est fermé.
	 * @param data → Consumer utilisant l'InventoryCloseEvent
	 * @return MenuBuilder → renvoie l'instance MenuBuilder afin de pouvoir enchaîner les instructions (voir wiki)
	 */
	public MenuBuilder setCloseAction(Consumer<InventoryCloseEvent> data) {
		closeAction = data;
		return this;
	}

	/**
	 * Cette fonction permet choisir si le menu peut être modifié en jeu (prendre un item de l'inventaire etc)
	 * L'état par défaut est False
	 * @param data → Boolean décidant. True = Aucune modification possible, False = modification possible
	 * @return MenuBuilder → renvoie l'instance MenuBuilder afin de pouvoir enchaîner les instructions (voir wiki)
	 */
	public MenuBuilder setAutoCancel(boolean data) {
		autoCancel = data;
		return this;
	}

	/**
	 * Cette fonction permet choisir si le menu se ferme automatiquement après l'exécution d'une action
	 * L'état par défaut est False
	 * @param data → Boolean décidant. True = fermeture automatique, False = pas de fermeture automatique
	 * @return MenuBuilder → renvoie l'instance MenuBuilder afin de pouvoir enchaîner les instructions (voir wiki)
	 */
	public MenuBuilder setAutoExit(boolean data) {
		autoExit = data;
		return this;
	}
	/**
	 * Cette fonction permet choisir le nombre de lignes de l'inventaire
	 * L'état par défaut est 1
	 *
	 * L'inventaire peut posséder un maximum de 6 lignes.
	 * Dans le cas où data serait négatif ou supérieur à 6, la fonction ne renverra pas d'erreur.
	 * l'action sera juste annulée
	 *
	 * @param data → int déterminant le nombre de lignes
	 * @return MenuBuilder → renvoie l'instance MenuBuilder afin de pouvoir enchaîner les instructions (voir wiki)
	 */
	public MenuBuilder setSize(int data) {
		if(data >= 1 && data <= 6) menuSize = data;
		return this;
	}

	/**
	 * Cette fonction permet choisir le menu précédent qui sera appelé par l'action du ClickableItem "RETURN"
	 * L'état par défaut est null
	 * @param menu → Menu, objet de type Menu
	 * @return MenuBuilder → renvoie l'instance MenuBuilder afin de pouvoir enchaîner les instructions (voir wiki)
	 */
	public MenuBuilder setPreviousMenu(Menu menu) {
		previousMenu = menu;
		return this;
	}
	/**
	 * Cette fonction permet de placer un ClickableItem grâce à des coordonnées
	 *
	 * @param x → emplacement de l'objet sur la ligne (x appartient à [0;8])
	 * @param y → emplacement de l'objet sur la colonne (x appartient à [0;(nombre de lignes total (max = 5)])
	 * @param clickableItem → Objet cliquable (Voir ClickableItem)
	 * @return MenuBuilder → renvoie l'instance MenuBuilder afin de pouvoir enchaîner les instructions (voir wiki)
	 */
	public MenuBuilder setClickableItem(int x, int y, ClickableItem clickableItem) {

		final int place = x + y * 9;

		if (itemHashMap.containsKey(place)) itemHashMap.replace(place, clickableItem);
		else itemHashMap.put(place, clickableItem);
		return this;
	}

	/**
	 * Cette fonction permet de placer un ClickableItem grâce à des coordonnées
	 *
	 * @param place → emplacement de l'objet dans l'inventaire  (x appartient à [0;9*nb_ligne_menu - 1])
	 * @param clickableItem → Objet cliquable (Voir ClickableItem)
	 * @return MenuBuilder → renvoie l'instance MenuBuilder afin de pouvoir enchaîner les instructions (voir wiki)
	 */
	public MenuBuilder setClickableItem(int place, ClickableItem clickableItem) {
		if (itemHashMap.containsKey(place)) {
			itemHashMap.replace(place, clickableItem);
		} else itemHashMap.put(place, clickableItem);
		return this;
	}

	/**
	 * Cette fonction permet de retirer un ClickableItem grâce à des coordonnées
	 *
	 * @param x → emplacement de l'objet sur la ligne (x appartient à [0;8])
	 * @param y → emplacement de l'objet sur la colonne (x appartient à [0;(nombre de lignes total (max = 5)])
	 * @return MenuBuilder → renvoie l'instance MenuBuilder afin de pouvoir enchaîner les instructions (voir wiki)
	 */
	public MenuBuilder removeClickableItem(int x, int y) {
		final int place = x + y * 9;
		itemHashMap.remove(place);
		return this;
	}

	/**
	 * Cette fonction permet de retirer un ClickableItem grâce à sa place dans l'inventaire
	 * @param place → emplacement de l'objet dans l'inventaire  (x appartient à [0;9*nb_ligne_menu - 1])
	 * @return MenuBuilder → renvoie l'instance MenuBuilder afin de pouvoir enchaîner les instructions (voir wiki)
	 */
	public MenuBuilder removeClickableItem(int place) {
		itemHashMap.remove(place);
		return this;
	}

	/**
	 * Cette fonction permet de récupérer l'instance d'un ClickableItem grâce à des coordonnées
	 *
	 * @param x → emplacement de l'objet sur la ligne (x appartient à [0;8])
	 * @param y → emplacement de l'objet sur la colonne (x appartient à [0;(nombre de lignes total (max = 5)])
	 * @return ClickableItem → renvoie l'instance du ClickableItem à la position (x;y)
	 * renvoie null si aucun item n'est trouvé
	 */
	public ClickableItem getClickableItem(int x, int y) {
		final int place = x + y * 9;
		return itemHashMap.get(place);
	}

	/**
	 * Cette fonction permet de remplir une ligne du menu avec le même objet cliquable (pratique pour les placeholder)
	 *
	 * @param row → numéro de la ligne (row appartient à [0;(nombre de lignes total (max = 5)])
	 * @param item → Objet cliquable (Voir ClickableItem)
	 * @return MenuBuilder → renvoie l'instance MenuBuilder afin de pouvoir enchaîner les instructions (voir wiki)
	 */
	public MenuBuilder setRowClickableItem(int row, ClickableItem item) {
		for (int i = 0; i < 9; i++) setClickableItem(i, row, item);
		return this;
	}

	/**
	 * Cette fonction permet de remplir une colonne du menu avec le même objet cliquable (pratique pour les placeholder)
	 *
	 * @param column → numéro de la ligne (row appartient à [0;8])
	 * @param item → Objet cliquable (Voir ClickableItem)
	 * @return MenuBuilder → renvoie l'instance MenuBuilder afin de pouvoir enchaîner les instructions (voir wiki)
	 */
	public MenuBuilder setColumnClickableItem(int column, ClickableItem item) {
		for (int i = 0; i < menuSize; i++) setClickableItem(column, i, item);
		return this;
	}

	/**
	 * Cette fonction permet de remplir le menu avec plusieurs objets cliquables en même temps
	 * @param clickableItems -> Une HashMap contenant en clé la place de l'objet et en valeur l'objet cliquable
	 * @return MenuBuilder → renvoie l'instance MenuBuilder afin de pouvoir enchaîner les instructions (voir wiki)
	 */
	public MenuBuilder setMultipleClickableItem(HashMap<Integer, ClickableItem> clickableItems) {
		clickableItems.forEach(this::setClickableItem);
		return this;
	}

	/**
	 * Cette fonction permet de remplir le menu avec des itemStacks en tant que placeHolder, utile pour afficher un inventaire
	 * sans pouvoir le modifier
	 * @param itemStacks -> ItemStacks
	 * @return MenuBuilder → renvoie l'instance MenuBuilder afin de pouvoir enchaîner les instructions (voir wiki)
	 */
	public MenuBuilder addItemStackAsPlaceHolders(ItemStack... itemStacks) {
		int place = 0;
		for (ItemStack i : itemStacks) {
			ClickableItem c = new ClickableItem().setItem(i).setCategory(ClickableItem.ItemCategory.PLACEHOLDER);
			setClickableItem(place, c);
			place++;
		}
		return this;
	}
	/**
	 * Cette fonction permet de remplir le menu avec des itemStacks en tant que placeHolder, utile pour afficher un inventaire
	 * sans pouvoir le modifier
	 * @param itemStacks -> ItemStacks
	 * @return MenuBuilder → renvoie l'instance MenuBuilder afin de pouvoir enchaîner les instructions (voir wiki)
	 */
	public MenuBuilder addItemStackAsPlaceHolders(List<ItemStack> itemStacks) {
		int place = 0;
		for (ItemStack i : itemStacks) {
			ClickableItem c = new ClickableItem().setItem(i).setCategory(ClickableItem.ItemCategory.PLACEHOLDER);
			setClickableItem(place, c);
			place++;
		}
		return this;
	}
	/**
	 * Cette fonction permet de générer le menu et le qualifié de menu de base
	 * Un menu identifié comme basic peut être récupéré grâce au register plus simplement qu'un menu classique.
	 * Cependant les modifications faites sur ce menu seront visibles par tous le monde utilisant le menu de base
	 * @param basic -> identifiant basique
	 * @return Menu → renvoie l'instance Menu construite
	 */
	public Menu build(String basic) {
		Menu menu = new Menu(menuTitle, menuSize, itemHashMap, autoCancel, autoExit, previousMenu, closeAction, user);
		MenuRegister.addMenu(menu, basic);
		return menu;
	}

	/**
	 * Cette fonction permet de mettre à jour un menu déjà existant
	 * @param menu -> identifiant basique
	 * @return Menu → renvoie l'instance Menu construite
	 */
	public Menu update(Menu menu) {
		return menu.update(menuTitle, menuSize, itemHashMap, autoCancel, autoExit, previousMenu, closeAction, user);
	}

	/**
	 * Cette fonction permet de récupérer le titre du Menu
	 * @return le titre du Menu
	 */
	public String getMenuTitle() {
		return menuTitle;
	}

	/**
	 * Cette fonction permet de récupérer l'utilisateur unique qui a été donné au Menu
	 * @return l'utilisateur du Menu
	 */
	public String getUser() {
		if (user != null) return user.toString();
		else return "";
	}

	/**
	 * Cette fonction permet d'assigner le menu à un utilisateur unique. Le menu ne sera alors utilisable que par cet
	 * utilisateur.
	 * @param data -> UUID de l'utilisateur
	 * @return MenuBuilder → renvoie l'instance MenuBuilder afin de pouvoir enchaîner les instructions (voir wiki)
	 */
	public MenuBuilder setUser(UUID data) {
		user = data;
		return this;
	}
}
