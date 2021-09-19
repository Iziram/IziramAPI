package fr.iziram.API.Scenarios;

import org.bukkit.Material;

public enum Scenario {
	DIAMOND_LIMIT("Diamond Limit", Material.DIAMOND, new DiamondLimit()),
	FIRELESS("Fireless", Material.FLINT_AND_STEEL, new FireLess()),
	HASTEYBOYS("HasteyBoys", Material.DIAMOND_PICKAXE, new HasteyBoys()),
	CUTCLEAN("CutClean", Material.IRON_INGOT, new CutClean()),
	FASTSMELTING("Fast Smelting", Material.FURNACE, new FastSmelting()),
	XPBOOST("XP Boost", Material.EXP_BOTTLE, new XPBoost()),
	NERFFORCE("Nerf Force", Material.DIAMOND_SWORD, new NerfForce()),
	RODLESS("Rodless", Material.FISHING_ROD, new RodLess()),
	NONAMETAG("NoNameTag", Material.NAME_TAG, new NoNameTag()),
	CATEYES("CatEyes", Material.EYE_OF_ENDER, new CatEyes()),
	TIMBER("Timber", Material.LOG, new Timber(1000)),
	TIMEBOMB("TimeBomb", Material.CHEST, new TimeBomb(30)),
	NERFRES("Nerf Résistance", Material.DIAMOND_CHESTPLATE, new NerfRes());

	public String NAME;
	public Material MATERIAL;
	public ScenarioInterface scenario;

	Scenario(String name, Material material, ScenarioInterface scenario) {
		this.NAME = name;
		this.MATERIAL = material;
		this.scenario = scenario;
	}

	public void setIcon(Material MATERIAL) {
		this.MATERIAL = MATERIAL;
	}
}
