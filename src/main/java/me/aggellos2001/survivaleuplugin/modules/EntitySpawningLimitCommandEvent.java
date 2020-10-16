package me.aggellos2001.survivaleuplugin.modules;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Optional;
import com.destroystokyo.paper.event.entity.PreCreatureSpawnEvent;
import me.aggellos2001.survivaleuplugin.SurvivalEUPlugin;
import me.aggellos2001.survivaleuplugin.utils.PluginActivity;
import me.aggellos2001.survivaleuplugin.utils.Utilities;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.CreatureSpawnEvent;

@CommandAlias("entitylimit")
@CommandPermission("seu.entitylimit")
public final class EntitySpawningLimitCommandEvent extends PluginActivity {

	@EventHandler(ignoreCancelled = true)
	private void onEntitySpawnEvent(final CreatureSpawnEvent e) {
		if (!e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.EGG)) {
			return;
		}
		final var entityLimit = (int) SurvivalEUPlugin.config.getValue("entity-limit");
		final var allEntities = e.getLocation().getChunk().getEntities().length;
		var ignoredEntities = 0;
		for (final Entity entity : e.getLocation().getChunk().getEntities()) {
			if (!(entity instanceof Mob)) {
				ignoredEntities++;
			}
		}
		if (allEntities - ignoredEntities >= entityLimit && entityLimit != 0) {
			e.setCancelled(true);
		}
	}

	//ONLY FOR NATURAL OR SPAWNER
	@EventHandler(ignoreCancelled = true)
	private void onPreEntitySpawnEvent(final PreCreatureSpawnEvent e) {
		final var entityLimit = (int) SurvivalEUPlugin.config.getValue("entity-limit");
		final var allEntities = e.getSpawnLocation().getChunk().getEntities().length;
		var ignoredEntities = 0;
		for (final Entity entity : e.getSpawnLocation().getChunk().getEntities()) {
			if (!(entity instanceof Mob)) {
				ignoredEntities++;
			}
		}
		if (allEntities - ignoredEntities >= entityLimit && entityLimit != 0) {
			e.setCancelled(true);
		}
	}

	@Default
	private void onCommand(Player player, @Optional Integer newLimit) {
		if (newLimit == null) {
			int limit = (int) SurvivalEUPlugin.config.getValue("entity-limit");
			Utilities.sendMsg(player, "&b/entitylimit {newLimit}\n" +
					"&bCurrent limit &e: " + limit);
		} else {
				SurvivalEUPlugin.config.setValue("entity-limit", newLimit);
				Utilities.sendMsg(player,"&aEntity limiter was set to &e" + newLimit);
		}
	}
}
