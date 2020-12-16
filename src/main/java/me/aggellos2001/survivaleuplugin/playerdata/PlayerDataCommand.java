package me.aggellos2001.survivaleuplugin.playerdata;

import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import me.aggellos2001.survivaleuplugin.languages.Language;
import me.aggellos2001.survivaleuplugin.modules.DonationBenefitCommand;
import me.aggellos2001.survivaleuplugin.utils.PluginActivity;
import me.aggellos2001.survivaleuplugin.utils.Utilities;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static me.aggellos2001.survivaleuplugin.utils.Utilities.createRenamedItemStack;


@CommandAlias("settings")
public final class PlayerDataCommand extends PluginActivity {

	private static final ItemStack ENABLED_BUTTON = createRenamedItemStack(Material.GREEN_STAINED_GLASS_PANE, "&a&lON");
	private static final ItemStack DISABLED_BUTTON = createRenamedItemStack(Material.RED_STAINED_GLASS_PANE, "&c&lOFF");
	private static final Set<Inventory> settingsMenus = new HashSet<>();

	@Default
	private void openGUI(final Player player) {

		final var ui = Bukkit.createInventory(null, 9, Utilities.colorize("#02A122&lSurvivalEU &e&lSettings"));

		final var playerData = PlayerData.getPlayerData(player);

		{
			//keep inventory button
			final ItemStack keepInvButton;

			if (playerData.keepingInventory) {
				keepInvButton = ENABLED_BUTTON;
			} else {
				keepInvButton = DISABLED_BUTTON;
			}

			keepInvButton.setLore(Collections.singletonList(Utilities.colorize("&e&lKeep Inventory")));

			ui.setItem(3, keepInvButton);
		}

		{
			//sit on stairs button

			final ItemStack sitOnStairsButton;

			if (playerData.sittingOnStairs) {
				sitOnStairsButton = ENABLED_BUTTON;
			} else {
				sitOnStairsButton = DISABLED_BUTTON;
			}

			sitOnStairsButton.setLore(Collections.singletonList(Utilities.colorize("&e&lSit on stairs")));

			ui.setItem(4, sitOnStairsButton);

		}
		{
			//sit on stairs button

			final ItemStack pvp;

			if (playerData.pvp) {
				pvp = ENABLED_BUTTON;
			} else {
				pvp = DISABLED_BUTTON;
			}

			pvp.setLore(Collections.singletonList(Utilities.colorize("&e&lPvP")));

			ui.setItem(5, pvp);

		}

		{
			//close button
			ui.setItem(8, createRenamedItemStack(Material.BARRIER, "&c&lExit"));
		}

		player.openInventory(ui);

		settingsMenus.add(ui);

	}

	@EventHandler
	private void disableDrag(final InventoryDragEvent e) {
		if (settingsMenus.contains(e.getInventory())) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	private void removeInventory(final InventoryCloseEvent e) {
		settingsMenus.remove(e.getInventory());
	}


	// =========== ui handler =============
	@EventHandler
	private void menuHandler(final InventoryClickEvent e) {

		if (!settingsMenus.contains(e.getInventory())) {
			return;
		}

		e.setCancelled(true);


		final var itemStackClicked = e.getInventory().getItem(e.getRawSlot());

		if (itemStackClicked == null) {
			return;
		}

		final var player = ((Player) e.getWhoClicked());
		final var clickedMaterial = itemStackClicked.getType();
		final var playerData = PlayerData.getPlayerData(player);

		if (e.getRawSlot() == 3) {
			//compare to material clicked. if green glass then its ON (true) so !true == true = false
			playerData.keepingInventory = !clickedMaterial.equals(ENABLED_BUTTON.getType());
			PlayerData.updatePlayerData(player, playerData);
			openGUI(player);
		}

		if (e.getRawSlot() == 4) {
			playerData.sittingOnStairs = !clickedMaterial.equals(ENABLED_BUTTON.getType());
			PlayerData.updatePlayerData(player, playerData);
			openGUI(player);
		}

		if (e.getRawSlot() == 5) {
			if (DonationBenefitCommand.hasDonationPotions(player)) {
				Utilities.sendMsg(player, Language.PVP_DONATION_POTIONS_DENIED.getTranslation(player));
				return;
			}
			playerData.pvp = !clickedMaterial.equals(ENABLED_BUTTON.getType());
			PlayerData.updatePlayerData(player, playerData);
			openGUI(player);
		}

		if (e.getRawSlot() == 8) {
			player.closeInventory();
		}

	}

	@Subcommand("setsupportpin")
	private void setSupportPin(final Player player, final int supportPin) {
		if (supportPin < 1000 || supportPin > 9999) {
			Utilities.sendMsg(player, "&cSupport PIN must be a 4 digit number! (1000-9999)");
			return;
		}
		final var playerDat = PlayerData.getPlayerData(player);
		playerDat.supportPIN = supportPin;
		PlayerData.updatePlayerData(player, playerDat);
		Utilities.sendMsg(player, "&aSupport PIN set to &e" + supportPin + "&a!");
	}

	@Subcommand("getsupportpIN")
	@Conditions("ConsoleOrOp") //only OP or console can check someones PIN
	@CommandCompletion("@players @nothing")
	private void getSupportPIN(final CommandSender sender, final OnlinePlayer player) {
		final var dat = PlayerData.getPlayerData(player.getPlayer());
		if (dat == null || dat.supportPIN == 0) {
			Utilities.sendMsg(sender, "&cPlayer " + player.getPlayer().getName() + " has no PIN set!");
			return;
		}
		Utilities.sendMsg(sender, "&aPlayer " + player.getPlayer().getName() + " has support PIN: &e" + dat.supportPIN + "!");
	}
}
