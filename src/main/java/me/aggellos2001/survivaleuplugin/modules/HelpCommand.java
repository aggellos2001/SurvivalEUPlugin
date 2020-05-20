package me.aggellos2001.survivaleuplugin.modules;

import co.aikar.commands.annotation.CommandAlias;
import me.aggellos2001.survivaleuplugin.utils.PluginActivity;
import me.aggellos2001.survivaleuplugin.utils.Utilities;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.world.ChunkLoadEvent;

@CommandAlias("help")
public final class HelpCommand extends PluginActivity {
	@Override
	public boolean hasEvents() {
		return true;
	}

	@Override
	public boolean hasCommands() {
		return false;
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
	private void onServerHelp(PlayerCommandPreprocessEvent e) {
		if (e.getMessage().toLowerCase().startsWith("/help")){
			e.setCancelled(true);
			Utilities.sendMsg(e.getPlayer(),"&6&lhttps://survivaleu.com/documentation/");
		}
	}
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
	private void onChunkLoad(ChunkLoadEvent e) {
	}


}
