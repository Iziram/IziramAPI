package fr.iziram.API.Builders;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Cette classe permet d'accélérer la création d'ItemStack
 */
public class ItemBuilder {

	private EntityType entityType;
	private String itemName;
	private int itemAmount;
	private Material itemMaterial;
	private List<String> itemLore;
	private byte itemData;
	private HashMap<Enchantment, Integer> itemEnchantment;
	private Pair<Boolean, String> isHead = Pair.with(false, "null");

	/**
	 * Initialisation du builder
	 */
	public ItemBuilder() {
		this.itemName = null;
		this.itemAmount = 1;
		this.itemMaterial = Material.APPLE;
		this.itemLore = new ArrayList<>();
		this.itemData = (byte) 0;
		this.itemEnchantment = new HashMap<>();
	}

	/**
	 * Cette fonction permet de savoir si l'itemstack donné est un outil (pioche, hache, pelle, faux, canne à pêche
	 * briquet ou cisailles).
	 * @param i -> itemStack
	 * @return -> Boolean True si c'est un outil False sinon
	 */
	public static boolean isTool(ItemStack i) {
		if (i == null) return false;
		Material m = i.getType();
		return m.toString().contains("AXE") || m.toString().contains("SPADE") || m.toString().contains("HOE")
				|| m == Material.FISHING_ROD || m == Material.FLINT_AND_STEEL || m == Material.SHEARS;
	}
	/**
	 * Cette fonction permet de savoir si l'itemstack donné est une épée.
	 * @param i -> itemStack
	 * @return -> Boolean True si c'est une épée False sinon
	 */
	public static boolean isSword(ItemStack i) {
		if (i == null) return false;
		Material m = i.getType();
		return m.toString().contains("SWORD");
	}
	/**
	 * Cette fonction permet de savoir si l'itemstack donné est une pièce d'armure.
	 * @param i -> itemStack
	 * @return -> Boolean True si c'est une pièce d'armure False sinon
	 */
	public static boolean isArmor(ItemStack i) {
		if (i == null) return false;
		Material m = i.getType();
		return m.toString().contains("HELMET") || m.toString().contains("CHESTPLATE") || m.toString().contains("LEGGINGS")
				|| m.toString().contains("BOOTS");
	}

	/**
	 * Cette fonction permet de récuppérer la liste des enchantements liés aux outils.
	 * @return -> La liste des enchantements
	 */
	public static Enchantment[] getToolEnchants() {
		return new Enchantment[]{Enchantment.DIG_SPEED, Enchantment.SILK_TOUCH, Enchantment.DURABILITY,
				Enchantment.LOOT_BONUS_BLOCKS, Enchantment.LUCK, Enchantment.LURE};
	}
	/**
	 * Cette fonction permet de récuppérer la liste des enchantements liés aux épées.
	 * @return -> La liste des enchantements
	 */
	public static Enchantment[] getSwordEnchants() {
		return new Enchantment[]{Enchantment.DURABILITY, Enchantment.DAMAGE_ALL, Enchantment.DAMAGE_ARTHROPODS,
				Enchantment.DAMAGE_UNDEAD, Enchantment.FIRE_ASPECT, Enchantment.KNOCKBACK, Enchantment.LOOT_BONUS_MOBS};
	}
	/**
	 * Cette fonction permet de récuppérer la liste des enchantements liés à une pièce d'armure
	 * @param m -> Material de la pièce d'armure
	 * @return -> La liste des enchantements
	 */
	public static Enchantment[] getArmorEnchants(Material m) {
		if (m.toString().contains("BOOTS"))
			return new Enchantment[]{Enchantment.DURABILITY, Enchantment.PROTECTION_ENVIRONMENTAL,
					Enchantment.PROTECTION_EXPLOSIONS, Enchantment.PROTECTION_FIRE, Enchantment.PROTECTION_PROJECTILE,
					Enchantment.PROTECTION_FALL, Enchantment.THORNS, Enchantment.DEPTH_STRIDER};
		if (m.toString().contains("LEGGINS"))
			return new Enchantment[]{Enchantment.DURABILITY, Enchantment.PROTECTION_ENVIRONMENTAL,
					Enchantment.PROTECTION_EXPLOSIONS, Enchantment.PROTECTION_FIRE, Enchantment.PROTECTION_PROJECTILE,
					Enchantment.THORNS};
		if (m.toString().contains("CHESTPLATE"))
			return new Enchantment[]{Enchantment.DURABILITY, Enchantment.PROTECTION_ENVIRONMENTAL,
					Enchantment.PROTECTION_EXPLOSIONS, Enchantment.PROTECTION_FIRE, Enchantment.PROTECTION_PROJECTILE,
					Enchantment.THORNS};
		if (m.toString().contains("HELMET"))
			return new Enchantment[]{Enchantment.DURABILITY, Enchantment.PROTECTION_ENVIRONMENTAL,
					Enchantment.PROTECTION_EXPLOSIONS, Enchantment.PROTECTION_FIRE, Enchantment.PROTECTION_PROJECTILE,
					Enchantment.THORNS, Enchantment.OXYGEN, Enchantment.WATER_WORKER};
		else return null;
	}
	/**
	 * Cette fonction permet de récuppérer la liste des enchantements liés aux arcs
	 * @return -> La liste des enchantements
	 */
	public static Enchantment[] getBowEnchants() {
		return new Enchantment[]{Enchantment.DURABILITY, Enchantment.ARROW_DAMAGE, Enchantment.ARROW_FIRE,
				Enchantment.ARROW_INFINITE, Enchantment.ARROW_KNOCKBACK};
	}
	/**
	 * Cette fonction permet de récuppérer la liste des enchantements liés à un type (tools, bow, armor, sword)
	 * @param type -> ce paramètre indique le type d'enchantment, par défaut, ce sera ceux des épées
	 * @param m -> ce paramètre est utilisé uniquement si le type est armor
	 * @return -> La liste des enchantements
	 */
	public static Enchantment[] getEnchants(String type, Material m) {
		switch (type) {
			case "tool":
				return getToolEnchants();
			case "bow":
				return getBowEnchants();
			case "armor":
				return getArmorEnchants(m);
			default:
				return getSwordEnchants();
		}
	}

	/**
	 * Cette fonction permet de donner un ItemStack à un joueur.
	 * Dans le cas où le joueur aurait l'inventaire plein, l'item sera drop au sol
	 * @param p -> Le joueur
	 * @param item -> l'item à donner
	 */
	public static void giveItem(Player p, ItemStack item) {
		if (p.getInventory().firstEmpty() == -1) {
			p.getLocation().getWorld().dropItem(p.getLocation(), item);
		} else {
			p.getInventory().addItem(item);
		}
	}

	/**
	 * Cette fonction permet de générer un ItemStack en fonction des informations données au builder
	 * @return -> ItemStack créé
	 */
	public ItemStack build() {
		ItemStack item;
		if (entityType == null) {
			item = new ItemStack(itemMaterial, itemAmount, itemData);
		} else {
			item = new ItemStack(itemMaterial, itemAmount, entityType.getTypeId());
		}

		ItemMeta meta = item.getItemMeta();
		if (itemName != null) meta.setDisplayName(itemName);
		meta.setLore(itemLore);
		ItemMeta finalMeta = meta;
		itemEnchantment.forEach((e, i) -> finalMeta.addEnchant(e, i, true));
		if (isHead.getValue0()) {
			meta = generateHead(isHead.getValue1(), meta);
		}

		item.setItemMeta(meta);

		return item;
	}

	/**
	 * Cette fonction permet de mettre à jour les Data de l'itemBuilder (utilisé pour les blocs de laine etc)
	 * (Laine blanche = 0 etc)
	 * @param data -> Byte
	 * @return -> Renvoie l'itemBuilder afin d'enchaîner les instructions (voir Wiki)
	 */
	public ItemBuilder setItemData(byte data) {

		this.itemData = data;
		return this;
	}

	/**
	 * Cette fonction permet de mettre à jour les lores de l'itemBuilder
	 * @param data -> Liste des lignes à écrire dans les lores
	 * @return -> Renvoie l'itemBuilder afin d'enchaîner les instructions (voir Wiki)
	 */
	public ItemBuilder setItemLore(List<String> data) {
		if (data != null)
			this.itemLore = data;
		return this;
	}

	/**
	 * Cette fonction permet de mettre à jour les lores de l'itemBuilder
	 * @param data -> Liste des lignes à écrire dans les lores
	 * @return -> Renvoie l'itemBuilder afin d'enchaîner les instructions (voir Wiki)
	 */
	public ItemBuilder setItemLore(String... data) {
		this.itemLore.clear();
		this.itemLore.addAll(Arrays.asList(data));
		return this;
	}
	/**
	 * Cette fonction permet de mettre à jour le type d'item de l'itemBuilder
	 * @param data -> Material, type de l'item
	 * @return -> Renvoie l'itemBuilder afin d'enchaîner les instructions (voir Wiki)
	 */
	public ItemBuilder setItemMaterial(Material data) {
		if (data != null)
			this.itemMaterial = data;
		return this;
	}
	/**
	 * Cette fonction permet de mettre à jour le nombre d'items produit par l'itemBuilder
	 * @param data -> nombre d'item (entre 1 et 255)
	 * @return -> Renvoie l'itemBuilder afin d'enchaîner les instructions (voir Wiki)
	 */
	public ItemBuilder setItemAmount(int data) {
		this.itemAmount = data;
		return this;
	}
	/**
	 * Cette fonction permet de mettre à jour le nom de l'item produit par l'itemBuilder
	 * @param data -> String nom de l'item
	 * @return -> Renvoie l'itemBuilder afin d'enchaîner les instructions (voir Wiki)
	 */
	public ItemBuilder setItemName(String data) {
		if (data != null)
			this.itemName = data;
		return this;
	}
	/**
	 * Cette fonction permet de mettre à jour des enchantements de l'item produit par l'itemBuilder
	 * @param data -> HashMap des enchantements avec clé = Enchantement et valeur = niveau d'enchantement
	 * @return -> Renvoie l'itemBuilder afin d'enchaîner les instructions (voir Wiki)
	 */
	public ItemBuilder setItemEnchantments(HashMap<Enchantment, Integer> data) {
		if (data != null)
			this.itemEnchantment = data;
		return this;
	}

	/**
	 * Cette fonction permet de mettre à jour des enchantements de l'item produit par l'itemBuilder
	 * @param data -> List d'enchantements sans valeur précise
	 * @return -> Renvoie l'itemBuilder afin d'enchaîner les instructions (voir Wiki)
	 */
	public ItemBuilder setItemEnchantments(Enchantment... data) {
		this.itemEnchantment.clear();
		for (Enchantment d : data) this.itemEnchantment.put(d, 1);
		return this;
	}
	/**
	 * Cette fonction permet de définir l'item comme une tête et donc d'assigner une texture en fonction de name
	 * @param name -> nom d'un joueur dont la tête prendra le skin
	 * @return -> Renvoie l'itemBuilder afin d'enchaîner les instructions (voir Wiki)
	 */
	public ItemBuilder setHead(String name) {
		isHead = isHead.setAt0(true).setAt1(name);
		return this;
	}
	/**
	 * Cette fonction permet de définir l'item comme un oeuf de spawn et donc de lui donner un type
	 * @param type -> type d'entity a spawn
	 * @return -> Renvoie l'itemBuilder afin d'enchaîner les instructions (voir Wiki)
	 */
	public ItemBuilder setSpawnEgg(EntityType type) {
		this.entityType = type;
		return this;
	}
	/**
	 * Cette fonction permet de générer la tête
	 */
	private ItemMeta generateHead(String name, ItemMeta meta) {
		if (meta instanceof SkullMeta) {
			SkullMeta skullMeta = (SkullMeta) meta;
			skullMeta.setOwner(name);
			return skullMeta;
		}
		return meta;
	}


}
