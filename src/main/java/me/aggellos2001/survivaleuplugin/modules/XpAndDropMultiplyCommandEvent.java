package me.aggellos2001.survivaleuplugin.modules;

import co.aikar.commands.ConditionFailedException;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Optional;
import me.aggellos2001.survivaleuplugin.SurvivalEUPlugin;
import me.aggellos2001.survivaleuplugin.utils.PluginActivity;
import me.aggellos2001.survivaleuplugin.utils.Utilities;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

@CommandAlias("xpmultiplier")
@CommandPermission("seu.xpmultiplier")
public final class XpAndDropMultiplyCommandEvent extends PluginActivity {

	@EventHandler(ignoreCancelled = true)
	private void onEntityPlayerKill(final EntityDeathEvent e) {

		final var xpMultiplier = (int) SurvivalEUPlugin.config.getValue("xp-multiplier");

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

	@Default
	private void onCommand(final CommandSender sender, @Optional final Integer newValue) {
		final var xpMultiplier = (int) SurvivalEUPlugin.config.getValue("xp-multiplier");
		if (newValue == null) {
			Utilities.sendMsg(sender, "&cUsage: /xpmultiplier {amount}. &aCurrent: " + xpMultiplier);
		} else {
			if (newValue <= 0) {
				throw new ConditionFailedException("Value cannot be 0 or less!");
			}
			SurvivalEUPlugin.config.setValue("xp-multiplier", newValue);
			Utilities.sendMsg(sender, "&aΗ τιμή του xp/drop multipler άλλαξε σε &e" + newValue + "&a!");
		}
	}
}
