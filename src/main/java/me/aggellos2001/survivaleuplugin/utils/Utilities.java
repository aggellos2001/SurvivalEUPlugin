package me.aggellos2001.survivaleuplugin.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

public class Utilities {

	/**
	 * Simple way to get tick values from real time values.
	 */
	public enum  TicksDuration {

		SECOND(20),
		MINUTE(1200),
		HOUR(72_000),
		DAY(1_728_000),
		WEEK(12_096_000),
		YEAR(631_152_000);

		public final int ticks;

		TicksDuration(int ticks) {
			this.ticks = ticks;
		}

		public int getTime(int duration){
			return this.ticks * duration;
		}
	}

	public static String colorize(final String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}

	public static String colorize(final String s, final boolean addPrefix) {
		if (addPrefix)
			return ChatColor.translateAlternateColorCodes('&', "&6[&bSurvivalEU&6]&r " + s);
		else return colorize(s);
	}

	/**	Get HEX colors
	 * @since MC 1.16
	 *  Uses chatcolor api from bungee to return the color
	 */
	public static net.md_5.bungee.api.ChatColor getHexColor(String hexColorCode){
		return net.md_5.bungee.api.ChatColor.of(hexColorCode);
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

	public static void sendMsg(CommandSender sender, String message){
		sender.sendMessage(colorize(message,true));
	}
}
