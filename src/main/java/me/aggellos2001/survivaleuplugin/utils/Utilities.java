package me.aggellos2001.survivaleuplugin.utils;

import me.mattstudios.mfmsg.base.MessageOptions;
import me.mattstudios.mfmsg.base.internal.Format;
import me.mattstudios.mfmsg.bukkit.BukkitMessage;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;

public class Utilities {

	/**
	 * Simple way to get tick values from real time values.
	 */

	private static final BukkitMessage messageParser = BukkitMessage.create(MessageOptions.builder().removeFormat(Format.ITALIC, Format.LEGACY_ITALIC).build());

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

	public static String colorize(final String s, final Format... disableFormat) {
		final var parser = BukkitMessage.create(MessageOptions.builder().removeFormat(disableFormat).build());
		return parser.parse(s).toString();
	}

	public static String colorize(final String s) {
		return messageParser.parse(s).toString();
	}

	public static String colorize(final String s, final boolean addPrefix) {
		if (addPrefix) {
			return messageParser.parse("&6[&bSurvivalEU&6]&r " + s).toString();
		} else return colorize(s);
	}

	public static String[] colorize(final String... s) {
		for (int i = 0; i < s.length; i++) {
			s[i] = colorize(s[i]);
		}
		return s;
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

	public static void sendMsg(final CommandSender sender, final String message, final boolean addPrefix) {
		sender.sendMessage(colorize(message, addPrefix));
	}

	public static void sendMsg(final Collection<? extends Player> players, final String message) {
		for (final Player player : players) {
			player.sendMessage(colorize(message, true));
		}
	}

	public static void sendMsg(final Collection<? extends Player> players, final String message, final boolean addPrefix) {
		for (final Player player : players) {
			player.sendMessage(colorize(message, addPrefix));
		}
	}

	public static void sendPermissionMsg(final Player player, final String permission, final String message, final boolean addPrefix) {
		if (player.hasPermission(permission))
			player.sendMessage(colorize(message, addPrefix));
	}


	public static void sendPermissionMsg(final Collection<? extends Player> players, final String permission, final String message, final boolean addPrefix) {
		for (final Player player : players) {
			if (player.hasPermission(permission))
				player.sendMessage(colorize(message, addPrefix));
		}
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

	public static boolean isLiquid(final Material material) {
		switch (material) {
			case LAVA:
			case WATER:
				return true;
		}
		return false;
	}

	public static boolean isSafeLocation(final Location location) {

		// feet block and head block must be non solid (air) and non liquid (we dont want to be drawned to be burned)

		//block of half down of the player
		final var feetBlock = location.getBlock().getType();
		if (feetBlock.isSolid() || feetBlock == Material.TRIPWIRE || isLiquid(feetBlock))
			return false; //not safe if its solid or liquid (lava or water)


		//block half up of the player
		final var headBlock = location.getBlock().getRelative(BlockFace.UP).getType();
		if (headBlock.isSolid() || isLiquid(headBlock)) {
			return false; // not safe if its solid or liquid (lava or water)
		}
		//block under legs
		final var groundBlock = location.getBlock().getRelative(BlockFace.DOWN).getType();
		if (!groundBlock.isSolid())
			return false; //must be solid for player to stand

		/* also the ground block must also not be a pressure plate or a tripwire for example
		   so we can avoid traps. also no leaves so we do not spawn on top of a tree if we use
		   this in a (wild) command for example.
		 */
		switch (groundBlock) {
			case ACACIA_PRESSURE_PLATE:
			case BIRCH_PRESSURE_PLATE:
			case CRIMSON_PRESSURE_PLATE:
			case DARK_OAK_PRESSURE_PLATE:
			case HEAVY_WEIGHTED_PRESSURE_PLATE:
			case JUNGLE_PRESSURE_PLATE:
			case LIGHT_WEIGHTED_PRESSURE_PLATE:
			case OAK_PRESSURE_PLATE:
			case POLISHED_BLACKSTONE_PRESSURE_PLATE:
			case SPRUCE_PRESSURE_PLATE:
			case STONE_PRESSURE_PLATE:
			case WARPED_PRESSURE_PLATE:
			case LAVA:
			case WATER:
			case MAGMA_BLOCK:
			case ACACIA_LEAVES:
			case BIRCH_LEAVES:
			case DARK_OAK_LEAVES:
			case JUNGLE_LEAVES:
			case OAK_LEAVES:
			case SPRUCE_LEAVES:
				return false;
		}

		// return true if location provided (assuming is a block location probably) is safe to tp.
		return true;
	}

	public static String locationString(Location location){
		return "World: " + location.getWorld().getEnvironment().name() + ", x: " + location.getBlockX() + ", y: " + location.getBlockY() + ", z: " + location.getBlockZ();
	}


}
