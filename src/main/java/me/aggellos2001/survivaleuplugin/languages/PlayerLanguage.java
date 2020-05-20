package me.aggellos2001.survivaleuplugin.languages;

import me.aggellos2001.survivaleuplugin.SurvivalEUPlugin;
import me.aggellos2001.survivaleuplugin.utils.PluginActivity;
import me.aggellos2001.survivaleuplugin.utils.Utilities;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;

public final class PlayerLanguage extends PluginActivity {

	@Override
	public boolean hasEvents() {
		return true;
	}

	@Override
	public boolean hasCommands() {
		return false;
	}

	private static final Map<Player, String> LANGUAGE_MAP = new HashMap<>();

	@EventHandler(ignoreCancelled = true)
	private void addPlayerLanguage(final PlayerJoinEvent e) {
		SurvivalEUPlugin.instance.getServer().getScheduler().runTaskLater(SurvivalEUPlugin.instance, () -> {
			if (e.getPlayer().getLocale().toLowerCase().equals("el_gr")) {
				LANGUAGE_MAP.put(e.getPlayer(), "greek");
				Utilities.sendMsg(e.getPlayer(),"&aΗ γλώσσα ορίστηκε σε Ελληνικά!");
			} else {
				LANGUAGE_MAP.put(e.getPlayer(), "english");
				Utilities.sendMsg(e.getPlayer(),"&aYour language was set to English!");
			}
		}, 3 * 20L);
	}

	@EventHandler(ignoreCancelled = true)
	private void removePlayerLanguage(final PlayerQuitEvent e) {
		LANGUAGE_MAP.remove(e.getPlayer());
	}

	public static String getPlayerLanguage(final Player player) {
		return LANGUAGE_MAP.getOrDefault(player, "english");
	}
}
