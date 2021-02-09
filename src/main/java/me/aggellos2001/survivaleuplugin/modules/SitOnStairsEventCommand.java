package me.aggellos2001.survivaleuplugin.modules;


import me.aggellos2001.survivaleuplugin.languages.Language;
import me.aggellos2001.survivaleuplugin.playerdata.PlayerDataEvent;
import me.aggellos2001.survivaleuplugin.utils.PluginActivity;
import me.aggellos2001.survivaleuplugin.utils.Utilities;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.spigotmc.event.entity.EntityDismountEvent;

import java.util.*;

//@CommandAlias("sitonstairs|sitdown")
public class SitOnStairsEventCommand extends PluginActivity {


	private static final Map<UUID, Long> LAST_TIME_SAT = new HashMap<>();
	private static final Set<Player> PLAYERS_ENABLED_SIT_ON_STAIRS = new HashSet<>();


	@EventHandler(ignoreCancelled = true)
	private void onRightClickStair(final PlayerInteractEvent e) {

		final var clickedBlock = e.getClickedBlock();
		final var player = e.getPlayer();

		if (clickedBlock == null) return;
		if (e.getHand() == null) return;
		if (e.getHand().equals(EquipmentSlot.OFF_HAND)) return;
		if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
		if (!(clickedBlock.getBlockData() instanceof Stairs)) return;


		final var playerData = PlayerDataEvent.getPlayerData(player);


		if (!playerData.sittingOnStairs) return;

		final var lastTimeSat = LAST_TIME_SAT.getOrDefault(player.getUniqueId(), 0L);
		final var difference = System.currentTimeMillis() - lastTimeSat;
		final var delay = 5000;
		if (lastTimeSat != 0L && difference < delay) {
			Utilities.sendMsg(player, String.format(Language.SIT_ON_STAIRS_DELAY.getTranslation(player), (delay - difference) / 1000));
			return;
		}
		final var stair = (Stairs) e.getClickedBlock().getBlockData();
		if (stair.getHalf().equals(Bisected.Half.BOTTOM) && (clickedBlock.getRelative(BlockFace.UP).getType().equals(Material.AIR) || clickedBlock.getRelative(BlockFace.UP).getType().equals(Material.CAVE_AIR))) {
			final var arrow = clickedBlock.getWorld().spawnEntity(clickedBlock.getLocation().add(0.5, 0.0, 0.5), EntityType.ARROW);
			arrow.addPassenger(player);
			LAST_TIME_SAT.put(player.getUniqueId(), System.currentTimeMillis());
			Utilities.sendMsg(player, Language.SIT_ON_STAIRS_NOTIFY.getTranslation(player));
		}
	}

	@EventHandler(ignoreCancelled = true)
	private void removeArrow(final EntityDismountEvent e) {
		if (e.getDismounted().getType().equals(EntityType.ARROW)) {
			e.getDismounted().remove();
		}
	}
}
