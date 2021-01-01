package me.aggellos2001.survivaleuplugin.playerdata;

import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import me.aggellos2001.survivaleuplugin.languages.Language;
import me.aggellos2001.survivaleuplugin.modules.DonationBenefitCommand;
import me.aggellos2001.survivaleuplugin.utils.PluginActivity;
import me.aggellos2001.survivaleuplugin.utils.Utilities;
import me.mattstudios.mfgui.gui.components.ItemBuilder;
import me.mattstudios.mfgui.gui.guis.Gui;
import me.mattstudios.mfgui.gui.guis.GuiItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static me.aggellos2001.survivaleuplugin.utils.Utilities.createRenamedItemStack;


@CommandAlias("settings")
public final class PlayerDataCommand extends PluginActivity {

	private static final ItemStack ENABLED_BUTTON = createRenamedItemStack(Material.GREEN_WOOL, "&a&lON");
	private static final ItemStack DISABLED_BUTTON = createRenamedItemStack(Material.RED_WOOL, "&c&lOFF");

	private static GuiItem keepInventoryButton(final boolean keepingInventory) {
		return ItemBuilder
				.from(keepingInventory ? ENABLED_BUTTON.getType() : DISABLED_BUTTON.getType())
				.setName(Utilities.colorize("&e&lKeep Inventory")).glow(true)
				.setLore(keepingInventory ? Utilities.colorize("&a&lON") : Utilities.colorize("&c&lOFF"),
						Utilities.colorize("<#8f8d8d>Toggle item dropping on death."))
				.asGuiItem();
	}

	private static GuiItem sitOnStairsButton(final boolean sittingOnStairs) {
		return ItemBuilder
				.from(sittingOnStairs ? ENABLED_BUTTON.getType() : DISABLED_BUTTON.getType())
				.setLore(sittingOnStairs ? Utilities.colorize("&a&lON") : Utilities.colorize("&c&lOFF"),
						Utilities.colorize("<#8f8d8d>Toggle \"sitting\" on a stair block when right clicking it."))
				.setName(Utilities.colorize("&e&lSit on stairs")).glow(true)
				.asGuiItem();
	}

	private static GuiItem pvpButton(final boolean pvp) {
		return ItemBuilder
				.from(pvp ? ENABLED_BUTTON.getType() : DISABLED_BUTTON.getType())
				.setLore(pvp ? Utilities.colorize("&a&lON") : Utilities.colorize("&c&lOFF"),
						Utilities.colorize("<#8f8d8d>Toggle if you want to fight with other players!"))
				.setName(Utilities.colorize("&e&lPvP")).glow(true)
				.asGuiItem();
	}

	@Default
	protected static void settingsUI(final Player player) {

		final var playerData = PlayerData.getPlayerData(player);

		final var settingsMenu = new Gui(1, Utilities.colorize("<g:#ff8000:#31bd1d>Settings Menu"));
		settingsMenu.setDefaultClickAction(event -> {
					event.setCancelled(true);
				}
		);

		//add slot actions outside itemset to update them on click
		settingsMenu.addSlotAction(2, event -> {
			playerData.keepingInventory = !playerData.keepingInventory;
			PlayerData.updatePlayerData(player, playerData);
			settingsMenu.updateItem(2, keepInventoryButton(playerData.keepingInventory));
		});

		settingsMenu.addSlotAction(3, event -> {
			playerData.sittingOnStairs = !playerData.sittingOnStairs;
			PlayerData.updatePlayerData(player, playerData);
			settingsMenu.updateItem(3, sitOnStairsButton(playerData.sittingOnStairs));
		});

		settingsMenu.addSlotAction(4, event -> {
			if (DonationBenefitCommand.hasDonationPotions(player)) {
				Utilities.sendMsg(player, Language.PVP_DONATION_POTIONS_DENIED.getTranslation(player));
				return;
			}
			PlayerData.updatePlayerData(player, playerData);
			playerData.pvp = !playerData.pvp;
			PlayerData.updatePlayerData(player, playerData);
			settingsMenu.updateItem(4, pvpButton(playerData.pvp));
		});

		//add items to menu
		settingsMenu.setItem(2, keepInventoryButton(playerData.keepingInventory));

		settingsMenu.setItem(3, sitOnStairsButton(playerData.sittingOnStairs));

		settingsMenu.setItem(4, pvpButton(playerData.pvp));

		settingsMenu.setItem(8, ItemBuilder.from(Material.BARRIER)
				.setName(Utilities.colorize("&4&lExit")).glow(true)
				.asGuiItem(event -> settingsMenu.close(event.getWhoClicked()))
		);

		settingsMenu.setItem(5, ItemBuilder.from(Material.PAPER)
				.setName(Utilities.colorize("<r>Change chat color"))
				.setLore(Utilities.colorize("<#848eab>&lCurrent color: &" + playerData.chatColor +
						Utilities.readableEnumName(ChatColor.getByChar(playerData.chatColor).name())))
				.glow(true)
				.asGuiItem(event -> {
					ChatColorMenu.colorUI(((Player) event.getWhoClicked()), settingsMenu);
				})
		);

		settingsMenu.open(player);
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
