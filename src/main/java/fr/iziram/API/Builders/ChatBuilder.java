package fr.iziram.API.Builders;


import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Cette classe permet de facilement créer des messages dans le chat avec possibilité d'event (click / hover)
 */
public class ChatBuilder {

	private final List<BaseComponent> componentList = new ArrayList<>();

	/**
	 * Cette fonction permet de convertir un itemStack en un message représentant l'itemStack
	 * @param original → ItemStack
	 * @return → Un BaseComponent[] utilisable par la suite dans les messages
	 */
	public static BaseComponent[] itemStackToComponent(ItemStack original) {
		String json = convertItemStackToJsonRegular(original);
		return new BaseComponent[]{
				new TextComponent(json)
		};
	}

	/**
	 * Cette fonction permet de convertir un ItemStack en un Json
	 * @param itemStack → ItemStack
	 * @return String Json
	 */
	private static String convertItemStackToJsonRegular(ItemStack itemStack) {
		net.minecraft.server.v1_8_R3.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemStack);
		net.minecraft.server.v1_8_R3.NBTTagCompound compound = new NBTTagCompound();
		nmsItemStack.save(compound);
		return compound.toString();
	}

	/**
	 * Cette fonction permet de rajouter un morceau de message ayant un Click Event
	 * @param message → Le texte à afficher
	 * @param clickAction → L'action qui sera effectuée après le click
	 * @param value → les paramètres de l'action effectuée
	 * @return → Renvoie le ChatBuilder afin d'enchaîner les instructions (voir Wiki)
	 */
	public ChatBuilder addMessage(String message, ClickEvent.Action clickAction, String value) {
		TextComponent textComponent = new TextComponent(message);
		textComponent.setClickEvent(new ClickEvent(clickAction, value));
		componentList.add(textComponent);
		return this;
	}
	/**
	 * Cette fonction permet de rajouter un morceau de message ayant un Hover Event
	 * @param message → Le texte à afficher
	 * @param hoverAction → L'action qui sera effectuée lorsque la souris sera sur le texte
	 * @param value → les paramètres de l'action effectuée
	 * @return → Renvoie le ChatBuilder afin d'enchaîner les instructions (voir Wiki)
	 */
	public ChatBuilder addMessage(String message, HoverEvent.Action hoverAction, BaseComponent[] value) {
		TextComponent textComponent = new TextComponent(message);
		textComponent.setHoverEvent(new HoverEvent(hoverAction, value));
		componentList.add(textComponent);
		return this;
	}
	/**
	 * Cette fonction permet de rajouter un morceau de message n'ayant aucun event
	 * @param message → Le texte à afficher
	 * @return → Renvoie le ChatBuilder afin d'enchaîner les instructions (voir Wiki)
	 */
	public ChatBuilder addMessage(String message) {
		TextComponent textComponent = new TextComponent(message);
		componentList.add(textComponent);
		return this;
	}

	/**
	 * Cette fonction permet de créer le message au complet
	 * @return BaseComponent[] qui sera utilisable par spigot
	 */
	public BaseComponent[] build() {
		BaseComponent[] t = {};
		return componentList.toArray(t);
	}

	/**
	 * Cette fonction permet d'ajouter un morceau de message au message global
	 * @param component → Morceau de message
	 * @return → Renvoie le ChatBuilder afin d'enchaîner les instructions (voir Wiki)
	 */
	public ChatBuilder add(BaseComponent component) {
		componentList.add(component);
		return this;
	}
	/**
	 * Cette fonction permet d'ajouter une liste de morceaux de message au message global
	 * @param component → Morceaux de message
	 * @return → Renvoie le ChatBuilder afin d'enchaîner les instructions (voir Wiki)
	 */
	public ChatBuilder addAll(BaseComponent... component) {
		componentList.addAll(Arrays.asList(component));
		return this;
	}

}
