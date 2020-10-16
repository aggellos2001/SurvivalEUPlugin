package me.aggellos2001.survivaleuplugin.modules;


import me.aggellos2001.survivaleuplugin.languages.Language;
import me.aggellos2001.survivaleuplugin.utils.PluginActivity;
import me.aggellos2001.survivaleuplugin.utils.Utilities;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public final class NoEmptyHomeEvent extends PluginActivity {

	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	private void onSetHomeCommand(final PlayerCommandPreprocessEvent e) {
		if (e.getMessage().equals("/sethome")) {
			Utilities.sendMsg(e.getPlayer(), Language.NO_EMPTY_HOME.getTranslation(e.getPlayer()));
			e.setCancelled(true);
		}
	}
}
