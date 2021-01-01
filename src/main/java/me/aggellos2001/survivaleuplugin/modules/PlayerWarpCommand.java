package me.aggellos2001.survivaleuplugin.modules;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Single;
import co.aikar.commands.annotation.Subcommand;
import me.aggellos2001.survivaleuplugin.playerdata.PlayerWarp;
import me.aggellos2001.survivaleuplugin.utils.PluginActivity;
import me.aggellos2001.survivaleuplugin.utils.Utilities;
import me.mattstudios.mfgui.gui.components.ItemBuilder;
import me.mattstudios.mfgui.gui.guis.GuiItem;
import me.mattstudios.mfgui.gui.guis.PaginatedGui;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;

@CommandAlias("playerwarp|playerwarps|pwarp|pwarps")
public class PlayerWarpCommand extends PluginActivity {


	@Default
	private void playerWarpUI(final Player player) {

		final var playerWarpUI = new PaginatedGui(6, 45, Utilities.colorize("<#0230fa>Player Warps"));
		playerWarpUI.setDefaultClickAction(event -> event.setCancelled(true));

		//next btn
		playerWarpUI.setItem(6, 6, ItemBuilder.from(Material.LIME_DYE)
				.setName(Utilities.colorize("&a&lNext"))
				.asGuiItem(event -> {
					playerWarpUI.next();
				}));

		//previous btn
		playerWarpUI.setItem(6, 4, ItemBuilder.from(Material.GRAY_DYE)
				.setName(Utilities.colorize("&c&lPrevious"))
				.asGuiItem(event -> {
					playerWarpUI.previous();
				}));

		//exit btn
		playerWarpUI.setItem(6, 9, ItemBuilder.from(Material.BARRIER)
				.setName(Utilities.colorize("&4&lExit")).glow(true)
				.asGuiItem(event -> {
					event.getWhoClicked().closeInventory(InventoryCloseEvent.Reason.PLUGIN);
				})
		);

		playerWarpUI.getFiller().fillBottom(ItemBuilder.from(Material.GRAY_STAINED_GLASS_PANE).setName(" ").asGuiItem());


		for (final PlayerWarp warp : PlayerWarp.getPlayerWarps()) {
			final GuiItem warpItem;
			if (warp.getPlayerName().equalsIgnoreCase(player.getName())) {
				warpItem = ItemBuilder.from(Material.MAP)
						.setName(Utilities.colorize("<#45ad03>" + warp.getWarpName()))
						.setLore(Utilities.colorize("<#595957>Warp Owner: &e" + warp.getPlayerName()), Utilities.colorize("<#abaaa7>Right click: &cDelete"))
						.asGuiItem(event -> {
							if (event.getClick().isRightClick()) {
								deleteWarp(player, warp.getWarpName());
								playerWarpUI.close(player);
								return;
							}
							if (!Utilities.isSafeLocation(warp.getWarpLocation())) {
								Utilities.sendMsg(player, "&cTeleportation failed! Unsafe location!");
								return;
							}
							event.getWhoClicked().teleportAsync(warp.getWarpLocation())
									.thenAccept(aBoolean ->
											Utilities.sendMsg(player,
													"&aTeleported to warp &e" + warp.getWarpName() + " &asuccessfully!"));
						});
			} else {
				warpItem = ItemBuilder.from(Material.MAP)
						.setName(Utilities.colorize("<#45ad03>" + warp.getWarpName()))
						.setLore(Utilities.colorize("<#595957>Warp Owner: &e" + warp.getPlayerName()))
						.asGuiItem(event -> {
							if (!Utilities.isSafeLocation(warp.getWarpLocation())) {
								Utilities.sendMsg(player, "&cTeleportation failed! Unsafe location!");
								return;
							}
							event.getWhoClicked().teleportAsync(warp.getWarpLocation())
									.thenAccept(aBoolean ->
											Utilities.sendMsg(player,
													"&aTeleported to warp &e" + warp.getWarpName() + " &asuccessfully!"));
						});
			}
			playerWarpUI.addItem(warpItem);
		}


		playerWarpUI.open(player);
	}

	@Subcommand("addwarp")
	private void addWarp(final Player player, @Single final String warpName) {

		if (warpName.length() > 20) {
			Utilities.sendMsg(player, "&cWarp name must be up to 20 characters!");
			return;
		}

		int warpsOwned = 0;

		for (final PlayerWarp playerWarp : PlayerWarp.getPlayerWarps()) {
			if (playerWarp.getPlayerName().equalsIgnoreCase(player.getName())) {
				warpsOwned++;
			}
		}

		if (warpsOwned == 2) {
			Utilities.sendMsg(player, "&cYou cannot set more than 1 warps!");
			return;
		}

		final var warp = new PlayerWarp(warpName, player.getName(), player.getLocation());

		for (final PlayerWarp playerWarp : PlayerWarp.getPlayerWarps()) {
			if (playerWarp.getWarpName().equals(warpName)) {
				Utilities.sendMsg(player, "&cA warp with that name already exists!");
				return;
			}
		}

		PlayerWarp.addWarp(warp);
		Utilities.sendMsg(player, "&aWarp &e" + warpName + " &aadded!");

	}


	@Subcommand("deletewarp")
	private void deleteWarp(final Player player, @Single final String warpName) {

		for (final PlayerWarp playerWarp : PlayerWarp.getPlayerWarps()) {
			if (playerWarp.getWarpName().equalsIgnoreCase(warpName)) {
				if (playerWarp.getPlayerName().equalsIgnoreCase(player.getName())) {
					PlayerWarp.removeWarp(playerWarp);
					Utilities.sendMsg(player, "&aRemoved warp &e" + playerWarp.getWarpName() + " &asuccessfully!");
				} else {
					Utilities.sendMsg(player, "&cYou don't own that warp!");
				}
				return;
			}
		}
		Utilities.sendMsg(player, "&cWarp not found!");
	}

}
