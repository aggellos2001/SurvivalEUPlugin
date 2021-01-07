package me.aggellos2001.survivaleuplugin.modules;

import com.destroystokyo.paper.event.entity.PreCreatureSpawnEvent;
import me.aggellos2001.survivaleuplugin.config.Config;
import me.aggellos2001.survivaleuplugin.utils.PluginActivity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.CreatureSpawnEvent;

public final class EntitySpawningLimitCommandEvent extends PluginActivity {

	@EventHandler(ignoreCancelled = true)
	private void onEntitySpawnEvent(final CreatureSpawnEvent e) {
		if (e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)) {
			return;
		}
		final var entityLimit = Config.getConfig().entityLimit;
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
		final var entityLimit = Config.getConfig().entityLimit;
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
}
