package me.aggellos2001.survivaleuplugin.modules;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import me.aggellos2001.survivaleuplugin.SurvivalEUPlugin;
import me.aggellos2001.survivaleuplugin.utils.PluginActivity;
import me.aggellos2001.survivaleuplugin.utils.Utilities;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@CommandAlias("snowfall")
public class ServerSnow extends PluginActivity {

	private static Set<Player> snowPlayers = new HashSet<>();
	private static ThreadLocalRandom random = ThreadLocalRandom.current();

	static {
		//snow spawning runnable
		var snowRunnable = new Runnable() {
			@Override
			public void run() {
				if (snowPlayers.size() == 0) return;
				for (Player player : snowPlayers) {
					if (player.getLocation().getBlockY() < player.getWorld().getHighestBlockYAt(player.getLocation())) {
						continue;
					}
					var x = player.getLocation().getX() + random.nextInt(-35,35);
					var y = player.getLocation().getY() + 10;
					var z = player.getLocation().getZ() + random.nextInt(-35,35);
					player.spawnParticle(Particle.FIREWORKS_SPARK, x, y, z,300,35,0,35,0.65);
				}
			}
		};
		Bukkit.getServer().getScheduler()
				.runTaskTimer(SurvivalEUPlugin.instance, snowRunnable, 1, 5);
	}

	@EventHandler
	private void spawnFakeSnow(PlayerJoinEvent event) {
		var player = event.getPlayer();
		snowPlayers.add(player);
	}

	@EventHandler
	private void removePlayer(PlayerQuitEvent event) {
		var player = event.getPlayer();
		snowPlayers.remove(player);
	}

	@Default
	private void onSnowFallCommand(Player player) {
		if (player == null) {
			return;
		}
		if (snowPlayers.contains(player)) {
			snowPlayers.remove(player);
			Utilities.sendMsg(player, "&cSnow was disabled successfully!");
		} else {
			snowPlayers.add(player);
			Utilities.sendMsg(player, "&aSnow was enabled successfully!");
		}
	}
}
