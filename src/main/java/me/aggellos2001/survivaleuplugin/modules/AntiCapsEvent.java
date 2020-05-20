package me.aggellos2001.survivaleuplugin.modules;


import me.aggellos2001.survivaleuplugin.utils.PluginActivity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public final class AntiCapsEvent extends PluginActivity {

	@Override
	public boolean hasEvents() {
		return true;
	}

	@Override
	public boolean hasCommands() {
		return false;
	}

	private int capsInMessage(final String s) {
		var caps = 0;
		for (var i = 0; i < s.length(); i++) {
			final char c = s.charAt(i);
			if (Character.isUpperCase(c))
				caps++;
		}
		return caps;
	}

	@EventHandler(ignoreCancelled = true)
	private void onChatCheckCaps(final AsyncPlayerChatEvent e) {
		final var player = e.getPlayer();
		if (player.hasPermission("seu.caps.allow")) return;
		final var caps = capsInMessage(e.getMessage());
		if (caps > 5) {
			e.setMessage(e.getMessage().toLowerCase());
		}
	}
}
