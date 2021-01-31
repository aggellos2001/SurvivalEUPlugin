package me.aggellos2001.survivaleuplugin.playerdata;

import co.aikar.commands.annotation.CommandAlias;
import me.aggellos2001.survivaleuplugin.utils.Utilities;
import me.mattstudios.mfgui.gui.components.ItemBuilder;
import me.mattstudios.mfgui.gui.guis.Gui;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

@CommandAlias("chatcolor")
public class ChatColorMenu {

	protected static void colorUI(final Player player, Gui settingsUI /*ask for parent UI to reopen incase we go back*/) {

		final var menu = new Gui(2, Utilities.colorize("<r:0.8:1.0>Chat Color Menu"));
		menu.setDefaultClickAction(event -> event.setCancelled(true));

		final var data = PlayerDataEvent.getPlayerData(player);

		for (final ChatColor color : ChatColor.values()) {
			if (!color.isColor()) continue;
			final var colorNameColored = "&" + color.getChar() + Utilities.readableEnumName(color.name());
			menu.addItem(ItemBuilder.from(getDyeFromColor(color))
					.setName(Utilities.colorize(colorNameColored))
					.asGuiItem(event -> {
						data.chatColor = Character.toString(color.getChar());
						PlayerDataEvent.setPlayerData(player, data);
						menu.close(player);
						Utilities.sendMsg(player, "<#37a346>&lChat color changed to: &r&l" + colorNameColored);
					})
			);
		}
		menu.setItem(17, ItemBuilder.from(Material.OAK_DOOR)
				.setName(Utilities.colorize("<#bbbd60>**Back**"))
				.glow(true)
				.asGuiItem(event -> settingsUI.open(event.getWhoClicked()))
		);

		menu.open(player);
	}

	private static Material getDyeFromColor(final ChatColor color) {
		switch (color) {
			case RED:
			case DARK_RED:
				return Material.RED_DYE;
			case AQUA:
				return Material.LIGHT_BLUE_DYE;
			case BLUE:
			case DARK_BLUE:
				return Material.BLUE_DYE;
			case GOLD:
				return Material.ORANGE_DYE;
			case GRAY:
			case DARK_GRAY:
				return Material.GRAY_DYE;
			case BLACK:
				return Material.BLACK_DYE;
			case DARK_AQUA:
				return Material.CYAN_DYE;
			case GREEN:
			case DARK_GREEN:
				return Material.GREEN_DYE;
			case WHITE:
				return Material.WHITE_DYE;
			case YELLOW:
				return Material.YELLOW_DYE;
			case DARK_PURPLE:
				return Material.PURPLE_DYE;
			case LIGHT_PURPLE:
				return Material.MAGENTA_DYE;

		}
		return ItemBuilder.from(Material.PAPER).setName(Utilities.colorize("&cUnknown color!")).glow(true).build().getType();
	}
}
