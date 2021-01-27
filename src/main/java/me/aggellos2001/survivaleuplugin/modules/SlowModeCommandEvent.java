package me.aggellos2001.survivaleuplugin.modules;

import me.aggellos2001.survivaleuplugin.config.Config;
import me.aggellos2001.survivaleuplugin.languages.Language;
import me.aggellos2001.survivaleuplugin.utils.PluginActivity;
import me.aggellos2001.survivaleuplugin.utils.Utilities;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class SlowModeCommandEvent extends PluginActivity {

	private final Map<UUID, Long> LAST_TIME_CHATTED = new HashMap<>();

	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOW) //run this before antispam to prevent issues!
	private void onPlayerChat(final AsyncPlayerChatEvent e) {

		final var UUID = e.getPlayer().getUniqueId();
		final var lastTimeChatted = this.LAST_TIME_CHATTED.getOrDefault(UUID, 0L);
		final var difference = System.currentTimeMillis() - lastTimeChatted;
		final var coolDown = Duration.ofSeconds(Config.getConfig().slowModeDelay).toMillis();

		if (lastTimeChatted != 0L && difference < coolDown && coolDown > 0 && !e.getPlayer().hasPermission("seu.slowmode.bypass")) {
			e.setCancelled(true);
			Utilities.sendMsg(e.getPlayer(), String.format(Language.SLOW_MODE_COOLDOWN.getTranslation(e.getPlayer()), (coolDown - difference) / 1000.0));
		} else {
			this.LAST_TIME_CHATTED.put(UUID, System.currentTimeMillis());
		}
	}

}