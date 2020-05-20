package me.aggellos2001.survivaleuplugin.modules;


import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import me.aggellos2001.survivaleuplugin.utils.PluginActivity;
import me.aggellos2001.survivaleuplugin.utils.Utilities;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CommandAlias("alert")
@CommandPermission("seu.alert")
public final class AlertCommand extends PluginActivity {

	@Override
	public boolean hasEvents() {
		return false;
	}

	@Override
	public boolean hasCommands() {
		return true;
	}

	@Default
	private void onCommand(final Player player, int seconds, String message) {
		for (final Player p : Bukkit.getOnlinePlayers()) {
			p.sendTitle(Utilities.colorize("&a&lAnnouncement"), Utilities.colorize(message), 20, seconds * 20, 40);
		}
	}
}
