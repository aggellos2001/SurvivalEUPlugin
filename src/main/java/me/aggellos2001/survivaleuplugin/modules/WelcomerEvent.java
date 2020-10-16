package me.aggellos2001.survivaleuplugin.modules;

import me.aggellos2001.survivaleuplugin.SurvivalEUPlugin;
import me.aggellos2001.survivaleuplugin.languages.Language;
import me.aggellos2001.survivaleuplugin.utils.PluginActivity;
import me.aggellos2001.survivaleuplugin.utils.Utilities;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public final class WelcomerEvent extends PluginActivity {

	@EventHandler(ignoreCancelled = true)
	private void onPlayerJoinTitle(final PlayerJoinEvent e) {

		e.getPlayer().sendTitle(Utilities.colorize("&6&lWelcome to SurvivalEU S3"), Utilities.colorize("&a&lHave a great time playing here!"), 25, 4 * 20, 40);

		SurvivalEUPlugin.instance.getServer().getScheduler().runTaskLater(SurvivalEUPlugin.instance, () -> {
			Utilities.sendMsg(e.getPlayer(), Language.WELCOME_MESSAGE.getTranslation(e.getPlayer()));
		}, 10 * 20);
	}
}



