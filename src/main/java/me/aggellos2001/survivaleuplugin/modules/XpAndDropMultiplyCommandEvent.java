package me.aggellos2001.survivaleuplugin.modules;

import me.aggellos2001.survivaleuplugin.config.Config;
import me.aggellos2001.survivaleuplugin.utils.PluginActivity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public final class XpAndDropMultiplyCommandEvent extends PluginActivity {

	@EventHandler(ignoreCancelled = true)
	private void onEntityPlayerKill(final EntityDeathEvent e) {

		final var xpMultiplier = Config.getConfig().xpMultiplier;

		if (e.getEntity().getType().equals(EntityType.PLAYER)) return;

		e.setDroppedExp(e.getDroppedExp() * xpMultiplier);

		for (final ItemStack drop : e.getDrops()) {
			switch (drop.getType()) {
				case NETHER_STAR:
				case ARMOR_STAND:
				case SHULKER_BOX:
				case SKELETON_SKULL:
				case ZOMBIE_HEAD:
				case WITHER_SKELETON_SKULL:
				case PLAYER_HEAD:
					continue;
				default:
					final var dropAmount = drop.getAmount();
					if (dropAmount * xpMultiplier <= drop.getMaxStackSize()) {
						drop.setAmount(dropAmount * xpMultiplier);
					}
			}
		}
	}
}
