package me.aggellos2001.survivaleuplugin.modules;

import me.aggellos2001.survivaleuplugin.SurvivalEUPlugin;
import me.aggellos2001.survivaleuplugin.languages.Language;
import me.aggellos2001.survivaleuplugin.utils.PluginActivity;
import me.aggellos2001.survivaleuplugin.utils.Utilities;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.HashMap;
import java.util.Map;

public final class CommandCoolDownEvent extends PluginActivity {

	@Override
	public boolean hasEvents() {
		return true;
	}

	@Override
	public boolean hasCommands() {
		return false;
	}

	private final static Map<Player, Long> LAST_TIME_EXECUTED_COMMAND = new HashMap<>();

	@EventHandler(priority = EventPriority.LOWEST)
	private void onCommand(final PlayerCommandPreprocessEvent e) {
		final var player = e.getPlayer();
		if (player.isOp()) return;
		if (player.hasPermission("seu.slowmode.bypass")) return;
		final var lastTimeCommand = LAST_TIME_EXECUTED_COMMAND.getOrDefault(player, 0L);
		final var diff = System.currentTimeMillis() - lastTimeCommand;
		if (diff == 0L) {
			LAST_TIME_EXECUTED_COMMAND.put(player, System.currentTimeMillis());
			return;
		}
		final var delay = (int) SurvivalEUPlugin.config.getValue("slowmode") * 1000; //make delay to milliseconds
		if (diff < delay) {
			e.setCancelled(true);
			Utilities.sendMsg(player, String.format(Language.SLOW_COMMAND.getTranslation(player), (delay - diff) / 1000));
		} else {
			LAST_TIME_EXECUTED_COMMAND.put(player, System.currentTimeMillis());
		}
	}
}
