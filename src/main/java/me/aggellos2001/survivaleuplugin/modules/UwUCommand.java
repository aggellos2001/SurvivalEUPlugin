package me.aggellos2001.survivaleuplugin.modules;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import me.aggellos2001.survivaleuplugin.utils.PluginActivity;
import me.aggellos2001.survivaleuplugin.utils.Utilities;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CommandAlias("uwu")
public final class UwUCommand extends PluginActivity {

	@Default
	private void uwu(final Player player) {
		player.setHealth(0);
		Bukkit.broadcastMessage(Utilities.colorize(String.format("&bPlayer &6%s&b got uwued!", player.getName()), true));
	}
}
