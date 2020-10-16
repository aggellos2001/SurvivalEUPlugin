package me.aggellos2001.survivaleuplugin.modules;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Optional;
import me.aggellos2001.survivaleuplugin.SurvivalEUPlugin;
import me.aggellos2001.survivaleuplugin.languages.Language;
import me.aggellos2001.survivaleuplugin.utils.PluginActivity;
import me.aggellos2001.survivaleuplugin.utils.Utilities;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@CommandAlias("slowmode")
@CommandPermission("seu.slowmode")
public final class SlowModeCommandEvent extends PluginActivity {

	private final Map<UUID, Long> LAST_TIME_CHATTED = new HashMap<>();

	@EventHandler(ignoreCancelled = true)
	private void onPlayerChat(final AsyncPlayerChatEvent e) {

		final var UUID = e.getPlayer().getUniqueId();
		final var lastTimeChatted = this.LAST_TIME_CHATTED.getOrDefault(UUID, 0L);
		final var difference = System.currentTimeMillis() - lastTimeChatted;
		final var coolDown = (int) SurvivalEUPlugin.config.getValue("slowmode");

		if (lastTimeChatted != 0L && difference < coolDown * 1000 && coolDown > 0 && !e.getPlayer().hasPermission("seu.slowmode.bypass")) {
			e.setCancelled(true);
			Utilities.sendMsg(e.getPlayer(), String.format(Language.SLOW_MODE_COOLDOWN.getTranslation(e.getPlayer()), (coolDown - (difference / 1000))));
		} else {
			this.LAST_TIME_CHATTED.put(UUID, System.currentTimeMillis());
		}
	}

	@Default
	private void onCommand(final Player player, @Optional final Integer newDelay) {
		if (newDelay == null) {
			Utilities.sendMsg(player, "&eSlowmode:\n" +
					"&a/slowmode {seconds - 0 to disable}\n" +
					"&bCurrent: " + SurvivalEUPlugin.config.getValue("slowmode"));
		} else {
			Utilities.sendMsg(player, "&aNew cooldown for slowmode was set to " + newDelay + " successfully!");
			SurvivalEUPlugin.config.setValue("slowmode", newDelay);
		}
	}

}