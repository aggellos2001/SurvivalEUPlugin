package me.aggellos2001.survivaleuplugin.modules;


import me.aggellos2001.survivaleuplugin.utils.PluginActivity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public final class TeleportationProtectionEvent extends PluginActivity {

	@EventHandler(ignoreCancelled = true)
	private void onPlayerTp(final PlayerTeleportEvent e) {
		if (e.getCause().equals(PlayerTeleportEvent.TeleportCause.UNKNOWN)) return;
		e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 5 * 20, 5));
	}
}