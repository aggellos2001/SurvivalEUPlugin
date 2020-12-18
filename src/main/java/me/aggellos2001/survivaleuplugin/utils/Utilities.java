package me.aggellos2001.survivaleuplugin.utils;

import me.mattstudios.mfmsg.bukkit.BukkitMessage;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class Utilities {

	/**
	 * Simple way to get tick values from real time values.
	 */

	private static final BukkitMessage messageParser = BukkitMessage.create();

	public enum TicksDuration {

		SECOND(20),
		MINUTE(1200),
		HOUR(72_000),
		DAY(1_728_000),
		WEEK(12_096_000),
		YEAR(631_152_000);

		public final int ticks;

		TicksDuration(final int ticks) {
			this.ticks = ticks;
		}

		public int getTime(final int duration) {
			return this.ticks * duration;
		}
	}

	public static String colorize(final String s) {
		return messageParser.parse(s).toString();
	}

	public static String colorize(final String s, final boolean addPrefix) {
		if (addPrefix) {
			return messageParser.parse("&6[&bSurvivalEU&6]&r " + s).toString();
		} else return colorize(s);
	}

	@Nullable
	public static Integer toIntOrNull(final String s) {
		try {
			return Integer.parseInt(s);
		} catch (final NumberFormatException e) {
			return null;
		}
	}

	public static boolean isFull(final Inventory i) {
		return i.firstEmpty() == -1;
	}

	public static int getPlayerEmptySlots(final Player player) {
		final var contents = player.getInventory().getContents();
		var count = 0;
		for (final ItemStack content : contents) {
			if (content == null)
				count++;
		}
		return count;
	}

	public static boolean isPlayerName(final String player) {
		for (final Player onlinePlayer : Bukkit.getOnlinePlayers()) {
			if (onlinePlayer.getName().toLowerCase().equals(player.toLowerCase())) return true;
		}
		return false;
	}

	public static String emptyLine() {
		return "&r \n";
	}

	public static void sendMsg(final CommandSender sender, final String message) {
		sender.sendMessage(colorize(message, true));
	}

	public static ItemStack createRenamedItemStack(final Material material, final String displayName) {
		final var stack = new ItemStack(material);
		final ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(colorize(displayName));
		stack.setItemMeta(meta);
		return stack;
	}

	public static ItemStack createRenamedItemStack(final Material material, final String displayName, final String... lore) {
		final var stack = new ItemStack(material);
		final ItemMeta meta = stack.getItemMeta();
		if (displayName != null)
			meta.setDisplayName(colorize(displayName));
		if (lore != null) {
			var colorizedLore = new ArrayList<String>();
			for (final String line : lore) {
				if (line == null || line.equals("null")) {
					meta.addItemFlags(ItemFlag.values());
					colorizedLore = null;
					break;
				}
				colorizedLore.add(colorize(line));
			}
			meta.setLore(colorizedLore);
		} else {
			meta.addItemFlags(ItemFlag.values());
			meta.setLore(null);
		}
		stack.setItemMeta(meta);
		return stack;
	}

	public static String readableEnumName(String enumValue) {
		enumValue = enumValue.replace('_', ' ');
		enumValue = enumValue.toLowerCase();
		enumValue = StringUtils.capitalize(enumValue);
		return enumValue;
	}
}
