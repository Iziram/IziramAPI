package fr.iziram.API.Utils;

import fr.iziram.API.Internal.other.NMSUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ActionMessages {
	public static void sendTabTitle(Player player, String header, String footer) {

		header = header.replaceAll("%player%", player.getDisplayName());
		footer = footer.replaceAll("%player%", player.getDisplayName());
		try {
			Object tabHeader = NMSUtils.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + header + "\"}");
			Object tabFooter = NMSUtils.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + footer + "\"}");
			Constructor<?> titleConstructor = NMSUtils.getNMSClass("PacketPlayOutPlayerListHeaderFooter").getConstructor();
			Object packet = titleConstructor.newInstance();
			try {
				Field aField = packet.getClass().getDeclaredField("a");
				aField.setAccessible(true);
				aField.set(packet, tabHeader);
				Field bField = packet.getClass().getDeclaredField("b");
				bField.setAccessible(true);
				bField.set(packet, tabFooter);
			} catch (Exception e) {
				Field aField2 = packet.getClass().getDeclaredField("header");
				aField2.setAccessible(true);
				aField2.set(packet, tabHeader);
				Field bField2 = packet.getClass().getDeclaredField("footer");
				bField2.setAccessible(true);
				bField2.set(packet, tabFooter);
			}
			NMSUtils.sendPacket(player, packet);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void sendTitle(Player player, String title, String subtitle, int fadeInTime, int showTime, int fadeOutTime) {
		try {
			Object chatTitle = NMSUtils.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class)
					.invoke(null, "{\"text\": \"" + title + "\"}");
			Constructor<?> titleConstructor = NMSUtils.getNMSClass("PacketPlayOutTitle").getConstructor(
					NMSUtils.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], NMSUtils.getNMSClass("IChatBaseComponent"),
					int.class, int.class, int.class);
			Object packet = titleConstructor.newInstance(
					NMSUtils.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get(null), chatTitle,
					fadeInTime, showTime, fadeOutTime);

			Object chatsTitle = NMSUtils.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class)
					.invoke(null, "{\"text\": \"" + subtitle + "\"}");
			Constructor<?> timingTitleConstructor = NMSUtils.getNMSClass("PacketPlayOutTitle").getConstructor(
					NMSUtils.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], NMSUtils.getNMSClass("IChatBaseComponent"),
					int.class, int.class, int.class);
			Object timingPacket = timingTitleConstructor.newInstance(
					NMSUtils.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE").get(null), chatsTitle,
					fadeInTime, showTime, fadeOutTime);

			NMSUtils.sendPacket(player, packet);
			NMSUtils.sendPacket(player, timingPacket);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void sendActionBar(Player player, String message) {
		try {
			Class<?> craftPlayerClass = NMSUtils.getNMSClass("entity.CraftPlayer");
			Object craftPlayer = craftPlayerClass.cast(player);
			Object packet;
			Class<?> packetPlayOutChatClass = NMSUtils.getNMSClass("PacketPlayOutChat");
			Class<?> packetClass = NMSUtils.getNMSClass("Packet");
			if (Bukkit.getServer().getClass().getPackage().getName().equals("v1_8_R1")) {
				Class<?> chatSerializerClass = NMSUtils.getNMSClass("ChatSerializer");
				Class<?> iChatBaseComponentClass = NMSUtils.getNMSClass("IChatBaseComponent");
				Method m3 = chatSerializerClass.getDeclaredMethod("a", String.class);
				Object cbc = iChatBaseComponentClass.cast(m3.invoke(chatSerializerClass, "{\"text\": \"" + message + "\"}"));
				packet = packetPlayOutChatClass.getConstructor(new Class<?>[]{iChatBaseComponentClass, byte.class}).newInstance(cbc, (byte) 2);
			} else {
				Class<?> chatComponentTextClass = NMSUtils.getNMSClass("ChatComponentText");
				Class<?> iChatBaseComponentClass = NMSUtils.getNMSClass("IChatBaseComponent");
				try {
					Class<?> chatMessageTypeClass = NMSUtils.getNMSClass("ChatMessageType");
					Object[] chatMessageTypes = chatMessageTypeClass.getEnumConstants();
					Object chatMessageType = null;
					for (Object obj : chatMessageTypes) {
						if (obj.toString().equals("GAME_INFO")) {
							chatMessageType = obj;
						}
					}
					Object chatCompontentText = chatComponentTextClass.getConstructor(new Class<?>[]{String.class}).newInstance(message);
					packet = packetPlayOutChatClass.getConstructor(new Class<?>[]{iChatBaseComponentClass, chatMessageTypeClass}).newInstance(chatCompontentText, chatMessageType);
				} catch (ClassNotFoundException cnfe) {
					Object chatCompontentText = chatComponentTextClass.getConstructor(new Class<?>[]{String.class}).newInstance(message);
					packet = packetPlayOutChatClass.getConstructor(new Class<?>[]{iChatBaseComponentClass, byte.class}).newInstance(chatCompontentText, (byte) 2);
				}
			}
			Method craftPlayerHandleMethod = craftPlayerClass.getDeclaredMethod("getHandle");
			Object craftPlayerHandle = craftPlayerHandleMethod.invoke(craftPlayer);
			Field playerConnectionField = craftPlayerHandle.getClass().getDeclaredField("playerConnection");
			Object playerConnection = playerConnectionField.get(craftPlayerHandle);
			Method sendPacketMethod = playerConnection.getClass().getDeclaredMethod("sendPacket", packetClass);
			sendPacketMethod.invoke(playerConnection, packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String updateArrow(Player player, Location target) {

		Location location = player.getLocation();
		String arrow;
		location.setY(target.getY());
		Vector dirToMiddle = target.toVector().subtract(player.getEyeLocation().toVector()).normalize();

		int distance = 0;
		try {
			distance = (int) Math.round(target.distance(location));
		} catch (Exception ignored) {
		}

		Vector playerDirection = player.getEyeLocation().getDirection();
		double angle = dirToMiddle.angle(playerDirection);
		double det = dirToMiddle.getX() * playerDirection.getZ() - dirToMiddle.getZ() * playerDirection.getX();

		angle = angle * Math.signum(det);

		if (angle > -Math.PI / 8 && angle < Math.PI / 8) {
			arrow = "↑";
		} else if (angle < 3 * Math.PI / 8 && angle > Math.PI / 8) {
			arrow = "↖";
		} else if (angle > 3 * Math.PI / 8 && angle < 5 * Math.PI / 8) {
			arrow = "←";
		} else if (angle < -3 * Math.PI / 8 && angle > -5 * Math.PI / 8) {
			arrow = "→";
		} else if (angle < -5 * Math.PI / 8 && angle > -7 * Math.PI / 8) {
			arrow = "↘";
		} else if (angle > 5 * Math.PI / 8 && angle < 7 * Math.PI / 8) {
			arrow = "↙";
		} else arrow = "↓";

		return distance + " §l" + arrow;
	}
}
