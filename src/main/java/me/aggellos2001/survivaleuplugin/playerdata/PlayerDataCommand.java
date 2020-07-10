package me.aggellos2001.survivaleuplugin.playerdata;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import me.aggellos2001.survivaleuplugin.utils.PluginActivity;
import me.aggellos2001.survivaleuplugin.utils.Utilities;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;


@CommandAlias("settings")
public final class PlayerDataCommand extends PluginActivity {
	@Override
	public boolean hasEvents() {
		return false;
	}

	@Override
	public boolean hasCommands() {
		return true;
	}

	@Default
	private void onSettings(final Player player) {
		Utilities.sendMsg(player,
				ChatColor.of("#1c96a3") + "SurvivalEU Settings\n" +
						ChatColor.of("#e7ff0f") + "/settings keepinventory\n" +
						"/settings sitonstairs"
		);
	}

	@Subcommand("keepinventory")
	private void onToggleKeepInventory(final Player player) {
		final var data = PlayerData.getPlayerData();
		final var playerData = data.get(player.getUniqueId());
		final var isKeepingInv = playerData.isKeepingInventory();
		playerData.setKeepingInventory(!isKeepingInv);
		data.put(player.getUniqueId(), playerData);
		Utilities.sendMsg(player, ChatColor.of("#13d69f") + "Keeping inventory was set to: &l" + !isKeepingInv);
	}

	@Subcommand("sitonstairs")
	private void onToggleStairs(final Player player) {
		final var data = PlayerData.getPlayerData();
		final var playerData = data.get(player.getUniqueId());
		final var isSittingStairs = playerData.isSittingOnStairs();
		playerData.setSittingOnStairs(!isSittingStairs);
		data.put(player.getUniqueId(), playerData);
		Utilities.sendMsg(player, ChatColor.of("#13d69f") + "Sitting on stairs was set to: &l" + !isSittingStairs);
	}
}
