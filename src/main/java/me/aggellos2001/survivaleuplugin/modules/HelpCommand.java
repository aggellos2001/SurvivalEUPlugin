package me.aggellos2001.survivaleuplugin.modules;

import co.aikar.commands.annotation.CommandAlias;
import me.aggellos2001.survivaleuplugin.utils.PluginActivity;
import me.aggellos2001.survivaleuplugin.utils.Utilities;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

@CommandAlias("help")
public final class HelpCommand extends PluginActivity {

	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
	private void onServerHelp(final PlayerCommandPreprocessEvent e) {
		if (e.getMessage().toLowerCase().startsWith("/help")) {
			e.setCancelled(true);
			Utilities.sendMsg(e.getPlayer(), "&6&lhttps://survivaleu.com/documentation/");
		}
	}
}
