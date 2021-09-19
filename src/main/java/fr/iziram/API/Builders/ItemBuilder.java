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

public class ItemBuilder {

	private EntityType entityType;
	private String itemName;
	private int itemAmount;
	private Material itemMaterial;
	private List<String> itemLore;
	private byte itemData;
	private HashMap<Enchantment, Integer> itemEnchantment;
	private Pair<Boolean, String> isHead = Pair.with(false, "null");

	public ItemBuilder() {
		this.itemName = null;
		this.itemAmount = 1;
		this.itemMaterial = Material.APPLE;
		this.itemLore = new ArrayList<>();
		this.itemData = (byte) 0;
		this.itemEnchantment = new HashMap<>();
	}

	public static boolean isTool(ItemStack i) {
		if (i == null) return false;
		Material m = i.getType();
		return m.toString().contains("AXE") || m.toString().contains("SPADE") || m.toString().contains("HOE")
				|| m == Material.FISHING_ROD || m == Material.FLINT_AND_STEEL || m == Material.SHEARS;
	}

	public static boolean isSword(ItemStack i) {
		if (i == null) return false;
		Material m = i.getType();
		return m.toString().contains("SWORD");
	}

	public static boolean isArmor(ItemStack i) {
		if (i == null) return false;
		Material m = i.getType();
		return m.toString().contains("HELMET") || m.toString().contains("CHESTPLATE") || m.toString().contains("LEGGINGS")
				|| m.toString().contains("BOOTS");
	}

	public static Enchantment[] getToolEnchants() {
		return new Enchantment[]{Enchantment.DIG_SPEED, Enchantment.SILK_TOUCH, Enchantment.DURABILITY,
				Enchantment.LOOT_BONUS_BLOCKS, Enchantment.LUCK, Enchantment.LURE};
	}

	public static Enchantment[] getSwordEnchants() {
		return new Enchantment[]{Enchantment.DURABILITY, Enchantment.DAMAGE_ALL, Enchantment.DAMAGE_ARTHROPODS,
				Enchantment.DAMAGE_UNDEAD, Enchantment.FIRE_ASPECT, Enchantment.KNOCKBACK, Enchantment.LOOT_BONUS_MOBS};
	}

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

	public static Enchantment[] getBowEnchants() {
		return new Enchantment[]{Enchantment.DURABILITY, Enchantment.ARROW_DAMAGE, Enchantment.ARROW_FIRE,
				Enchantment.ARROW_INFINITE, Enchantment.ARROW_KNOCKBACK};
	}

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

	public static void giveItem(Player p, ItemStack item) {
		if (p.getInventory().firstEmpty() == -1) {
			p.getLocation().getWorld().dropItem(p.getLocation(), item);
		} else {
			p.getInventory().addItem(item);
		}
	}

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

	public ItemBuilder setItemData(byte data) {

		this.itemData = data;
		return this;
	}

	public ItemBuilder setItemLore(List<String> data) {
		if (data != null)
			this.itemLore = data;
		return this;
	}

	public ItemBuilder setItemLore(String... data) {
		this.itemLore.clear();
		this.itemLore.addAll(Arrays.asList(data));
		return this;
	}

	public ItemBuilder setItemMaterial(Material data) {
		if (data != null)
			this.itemMaterial = data;
		return this;
	}

	public ItemBuilder setItemAmount(int data) {
		this.itemAmount = data;
		return this;
	}

	public ItemBuilder setItemName(String data) {
		if (data != null)
			this.itemName = data;
		return this;
	}

	public ItemBuilder setItemEnchantments(HashMap<Enchantment, Integer> data) {
		if (data != null)
			this.itemEnchantment = data;
		return this;
	}

	public ItemBuilder setItemEnchantments(Enchantment... data) {
		this.itemEnchantment.clear();
		for (Enchantment d : data) this.itemEnchantment.put(d, 1);
		return this;
	}

	public ItemBuilder setHead(String name) {
		isHead = isHead.setAt0(true).setAt1(name);
		return this;
	}

	public ItemBuilder setSpawnEgg(EntityType type) {
		this.entityType = type;
		return this;
	}

	private ItemMeta generateHead(String name, ItemMeta meta) {
		if (meta instanceof SkullMeta) {
			SkullMeta skullMeta = (SkullMeta) meta;
			skullMeta.setOwner(name);
			return skullMeta;
		}
		return meta;
	}


}
