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


public class ChatBuilder {

	private final List<BaseComponent> componentList = new ArrayList<>();

	public static BaseComponent[] itemStackToComponent(ItemStack original) {
		String json = convertItemStackToJsonRegular(original);
		return new BaseComponent[]{
				new TextComponent(json)
		};
	}

	private static String convertItemStackToJsonRegular(ItemStack itemStack) {
		net.minecraft.server.v1_8_R3.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemStack);
		net.minecraft.server.v1_8_R3.NBTTagCompound compound = new NBTTagCompound();
		compound = nmsItemStack.save(compound);
		return compound.toString();
	}

	public ChatBuilder addMessage(String message, ClickEvent.Action eventAction, String value) {
		TextComponent textComponent = new TextComponent(message);
		textComponent.setClickEvent(new ClickEvent(eventAction, value));
		componentList.add(textComponent);
		return this;
	}

	public ChatBuilder addMessage(String message, HoverEvent.Action eventAction, BaseComponent[] value) {
		TextComponent textComponent = new TextComponent(message);
		textComponent.setHoverEvent(new HoverEvent(eventAction, value));
		componentList.add(textComponent);
		return this;
	}

	public ChatBuilder addMessage(String message) {
		TextComponent textComponent = new TextComponent(message);
		componentList.add(textComponent);
		return this;
	}

	public BaseComponent[] build() {
		BaseComponent[] t = {};
		return componentList.toArray(t);
	}

	public ChatBuilder add(BaseComponent component) {
		componentList.add(component);
		return this;
	}

	public ChatBuilder addAll(BaseComponent... component) {
		componentList.addAll(Arrays.asList(component));
		return this;
	}

}
