package me.aggellos2001.survivaleuplugin.modules;


import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import me.aggellos2001.survivaleuplugin.languages.Language;
import me.aggellos2001.survivaleuplugin.utils.PluginActivity;
import me.aggellos2001.survivaleuplugin.utils.Utilities;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CommandAlias("imlagging")
public final class LagInfoCommand extends PluginActivity {

	@Override
	public boolean hasEvents() {
		return false;
	}

	@Override
	public boolean hasCommands() {
		return true;
	}

	@Default
	private void onCommand(Player player) {
		var tps = (int) Math.round(Bukkit.getTPS()[0]);
		var ping = player.spigot().getPing();
		if (tps >= 18) {
			if (ping >= 140) {
				Utilities.sendMsg(player,String.format(Language.LAGINFO_HIGH_PING.getTranslation(player), tps, ping));
			} else {
				Utilities.sendMsg(player, String.format(Language.LAGINFO_LOW_FPS.getTranslation(player),tps,ping));
			}
		} else {
			Utilities.sendMsg(player, String.format(Language.LAGINFO_LOW_TPS.getTranslation(player),tps,ping));
		}

	}

}
