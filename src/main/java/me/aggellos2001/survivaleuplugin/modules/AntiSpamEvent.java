package me.aggellos2001.survivaleuplugin.modules;

import me.aggellos2001.survivaleuplugin.utils.PluginActivity;
import me.aggellos2001.survivaleuplugin.utils.Utilities;
import org.apache.commons.text.similarity.JaroWinklerDistance;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;

public class AntiSpamEvent extends PluginActivity {

	private static final HashMap<Player, String> BEFORE_PREVIOUS_MESSAGE = new HashMap<>();
	private static final HashMap<Player, String> PREVIOUS_MESSAGE = new HashMap<>();

	@EventHandler(priority = EventPriority.LOW)
	private void stopSimilarMessages(final AsyncPlayerChatEvent e) {

		final var player = e.getPlayer();
		final var message = e.getMessage();
		final var previousMessage = PREVIOUS_MESSAGE.getOrDefault(player, null);
		if (previousMessage == null) {
			PREVIOUS_MESSAGE.put(player, message);
			return;
		}
		final var before_previous_message = BEFORE_PREVIOUS_MESSAGE.getOrDefault(player, null);
		final var levenshteinDistance = new LevenshteinDistance().apply(previousMessage, message);
		final var jaroWinkler = new JaroWinklerDistance().apply(previousMessage, message);
		if (levenshteinDistance < 2 || jaroWinkler >= 0.90) {
			Utilities.sendMsg(player, "&cPlease do not repeat the same message!");
			e.setCancelled(true);
			return;
		}
		if (before_previous_message != null) {
			final var levenshteinDistanceBefore = new LevenshteinDistance().apply(before_previous_message, message);
			final var jaroWinklerBefore = new JaroWinklerDistance().apply(before_previous_message, message);
			if (levenshteinDistanceBefore < 2 || jaroWinklerBefore >= 0.90) {
				Utilities.sendMsg(player, "&cPlease do not repeat the same message!");
				e.setCancelled(true);
				return;
			}
		}
		BEFORE_PREVIOUS_MESSAGE.put(player, PREVIOUS_MESSAGE.get(player));
		PREVIOUS_MESSAGE.put(player, message);
	}
}
