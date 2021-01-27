package me.aggellos2001.survivaleuplugin.modules;

import co.aikar.commands.annotation.CommandAlias;
import me.aggellos2001.survivaleuplugin.utils.PluginActivity;
import me.aggellos2001.survivaleuplugin.utils.Utilities;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

@CommandAlias("help")
public final class HelpCommand extends PluginActivity {

	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
	private void onServerHelp(final PlayerCommandPreprocessEvent e) {
		if (e.getMessage().equalsIgnoreCase("/help")) {
			e.setCancelled(true);
			Utilities.sendMsg(e.getPlayer(),"&e&lhttps://survivaleu.com/docs.html");
		}
	}
}
