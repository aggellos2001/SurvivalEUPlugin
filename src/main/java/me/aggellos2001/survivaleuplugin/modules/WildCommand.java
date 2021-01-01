package me.aggellos2001.survivaleuplugin.modules;


import co.aikar.commands.annotation.*;
import me.aggellos2001.survivaleuplugin.SurvivalEUPlugin;
import me.aggellos2001.survivaleuplugin.hooks.EssentialsXHook;
import me.aggellos2001.survivaleuplugin.languages.Language;
import me.aggellos2001.survivaleuplugin.utils.PluginActivity;
import me.aggellos2001.survivaleuplugin.utils.Utilities;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import static me.aggellos2001.survivaleuplugin.utils.Utilities.isSafeLocation;

@CommandAlias("wild")
public class WildCommand extends PluginActivity {

	private final ThreadLocalRandom random = ThreadLocalRandom.current();

	private static final List<Player> pendingTeleportation = new ArrayList<>();

	private static final Map<Player, Long> delayMap = new HashMap<>();

	private int randomX(final int maxDistance) {
		final var decideSign = this.random.nextInt(3);
		if (decideSign == 0) {
			return this.random.nextInt(0, maxDistance);
		} else {
			return this.random.nextInt(-maxDistance, 0);
		}

	}


	private int randomZ(final int maxDistance) {
		final var decideSign = this.random.nextInt(3);
		if (decideSign == 0) {
			return this.random.nextInt(0, maxDistance);
		} else {
			return this.random.nextInt(-maxDistance, 0);
		}
	}

// Now using Utilities.isSafeLocation(block.getLocation()) to determine if the teleportation is safe!
//	private boolean isSuitable(final Block block) {
//		final boolean isBlockSafe;
//		final boolean isBlockDownSafe;
//		final boolean isTwoBlockUpSafe;
//		final boolean isBlockUpSafe;
//		final boolean isThreeBlockUpSafe;
//
//
//		switch (block.getRelative(BlockFace.DOWN).getState().getType()) {
//			case AIR:
//			case CAVE_AIR:
//			case WATER:
//			case LAVA:
//			case MAGMA_BLOCK:
//			case WITHER_ROSE:
//			case POTTED_WITHER_ROSE:
//			case VOID_AIR:
//				isBlockDownSafe = false;
//				break;
//			default:
//				isBlockDownSafe = true;
//		}
//
//		isBlockUpSafe = block.getRelative(BlockFace.UP).getState().getType() == Material.AIR;
//
//		isTwoBlockUpSafe = block.getRelative(BlockFace.UP).getRelative(BlockFace.UP).getState().getType() == Material.AIR;
//
//		isBlockSafe = block.getType() == Material.AIR;
//
//		isThreeBlockUpSafe = block.getRelative(BlockFace.UP).getRelative(BlockFace.UP).getRelative(BlockFace.UP).getState().getType() == Material.AIR;
//
//		return isBlockDownSafe && isBlockUpSafe && isTwoBlockUpSafe && isBlockSafe && isThreeBlockUpSafe;
//	}

	private void teleportToRandomLocationAsync(final Player player, final int maxDistance, final int maxRetries) {
		if (maxRetries == 0) {
			Utilities.sendMsg(player, Language.WILD_WILD_UNSUCCESFUL.getTranslation(player));
			pendingTeleportation.remove(player);
			return;
		}
		final int x = randomX(maxDistance);
		final int z = randomZ(maxDistance);
		player.getWorld().getChunkAtAsync(x / 16, z / 16, true).thenAccept(chunk -> {
			//try various spots on same chunk first before checking another
			for (var i = 0; i < 15; i++) {
				final var randomXforChunk = this.random.nextInt(16);
				final var randomZforChunk = this.random.nextInt(16);
				final var y = chunk.getChunkSnapshot().getHighestBlockYAt(randomXforChunk, randomZforChunk);
				final var block = chunk.getBlock(randomXforChunk, y, randomZforChunk);
				if (/*isSuitable(block)*/ isSafeLocation(block.getLocation())) {
					player.teleportAsync(block.getLocation());
					Utilities.sendMsg(player, Language.WILD_SUCCESS_TP.getTranslation(player));
					final var wildCost = (int) SurvivalEUPlugin.config.getValue("wild-cost");
					if (wildCost > 0) {
						EssentialsXHook.subtractPlayerBalance(player, wildCost);
						Utilities.sendMsg(player, String.format(Language.WILD_MONEY_SUBTRACTED.getTranslation(player), wildCost));
						pendingTeleportation.remove(player);

					}
					return;
				}
			}
			final var remainingRetries = maxRetries - 1;
			teleportToRandomLocationAsync(player, maxDistance, remainingRetries);

		}).handle((result, throwable) -> {
			pendingTeleportation.remove(player);
			return null;
		});
	}

	private boolean hasDelay(final Player player) {
		final var lastTimeUsedCommand = delayMap.getOrDefault(player, 0L);
		final var diff = System.currentTimeMillis() - lastTimeUsedCommand;
		final var delay = (int) SurvivalEUPlugin.config.getValue("wild-delay");
		if (lastTimeUsedCommand != 0L && diff < delay) {
			Utilities.sendMsg(player, String.format(Language.WILD_COOLDOWN.getTranslation(player), (delay - diff) / 1000));
			return true;
		}
		return false;
	}

	@Default
	private void onCommand(final Player player) {

		final var wildCost = (int) SurvivalEUPlugin.config.getValue("wild-cost");
		final var maxDistance = (int) SurvivalEUPlugin.config.getValue("wild-distance");
		final var maxRetries = (int) SurvivalEUPlugin.config.getValue("wild-retries");
		final var hasEnough = EssentialsXHook.hasEnough(player, wildCost);

		if (!player.getWorld().getEnvironment().equals(World.Environment.NORMAL)) {
			Utilities.sendMsg(player, Language.WILD_UNSUPPORTED_ENVIRONMENT.getTranslation(player));
			return;
		}

		if (!hasEnough) {
			Utilities.sendMsg(player, Language.NO_MONEY.getTranslation(player));
			return;
		}
		if (pendingTeleportation.contains(player)) {
			Utilities.sendMsg(player, Language.WILD_PENDING_TP.getTranslation(player));
			return;
		}

		if (!player.isOp()) {
			if (hasDelay(player)) {
				return;
			}
		}
		Utilities.sendMsg(player, Language.WILD_SEARCHING_FOR_LOCATION.getTranslation(player));
		teleportToRandomLocationAsync(player, maxDistance, maxRetries);
		pendingTeleportation.add(player);
		delayMap.put(player, System.currentTimeMillis());
	}

	@Subcommand("setmaxdistance")
	@CommandPermission("seu.edit.wild")
	private void setMaxDistance(final Player player, @Optional final Integer newDistance) {
		if (newDistance != null) {
			SurvivalEUPlugin.config.setValue("wild-distance", newDistance);
			Utilities.sendMsg(player, "&aSuccessfully set new max distance to " + newDistance);
		} else {
			final var maxDistance = (int) SurvivalEUPlugin.config.getValue("wild-distance");
			Utilities.sendMsg(player, "&cSpecify the distance! Current: " + maxDistance);
		}

	}

	@Subcommand("setcost")
	@CommandPermission("seu.edit.wild")
	private void changeCost(final Player player, @Optional final Integer newCost) {
		if (newCost != null) {
			SurvivalEUPlugin.config.setValue("wild-cost", newCost);
			Utilities.sendMsg(player, "&aSuccessfully set new cost to " + newCost);
		} else {
			final var wildCost = (int) SurvivalEUPlugin.config.getValue("wild-cost");
			Utilities.sendMsg(player, "&cSpecify the cost! Current: " + wildCost);
		}
	}

	@Subcommand("setmaxretries")
	@CommandPermission("seu.edit.wild")
	private void maxRetries(final Player player, @Optional final Integer newRetries) {
		if (newRetries != null) {
			SurvivalEUPlugin.config.setValue("wild-retries", newRetries);
			Utilities.sendMsg(player, "&aSuccessfully set maxRetries to " + newRetries);
		} else {
			final var maxRetries = (int) SurvivalEUPlugin.config.getValue("wild-retries");
			Utilities.sendMsg(player, "&cSpecify the retries! Current: " + maxRetries);
		}
	}

	@Subcommand("setdelay")
	@CommandPermission("seu.edit.wild")
	private void delay(final Player player, @Optional final Integer newDelay) {
		if (newDelay != null) {
			SurvivalEUPlugin.config.setValue("wild-delay", newDelay);
			Utilities.sendMsg(player, "&aSuccessfully set delay to " + newDelay);
		} else {
			final var delay = (int) SurvivalEUPlugin.config.getValue("wild-delay");
			Utilities.sendMsg(player, "&cSpecify the delay in milliseconds! Current: " + delay);
		}
	}
}
