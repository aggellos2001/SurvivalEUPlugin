package me.aggellos2001.survivaleuplugin.signlock;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import me.aggellos2001.survivaleuplugin.utils.PluginActivity;
import me.aggellos2001.survivaleuplugin.utils.Utilities;
import org.bukkit.ChatColor;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

@CommandAlias("lockchest")
public class SignLockHandler extends PluginActivity {

	//adding a lock sign
	@Default
	private void onCommand(final Player player, final String... playerNames) {
		final var target = player.getTargetBlock(5);
		if (target == null) return;
		if (!(target.getState() instanceof Chest)) {
			Utilities.sendMsg(player, "&cYou must look a chest within 5 blocks range!");
			return;
		}
		final var chestAdapted = new ChestAdapter(((Chest) target.getState()));
		chestAdapted.addLockSign(player, playerNames);
	}

	@EventHandler(ignoreCancelled = true)
	@SuppressWarnings("ConstantConditions")
	private void onSignChangeEvent(final SignChangeEvent e) {
		if (!ChatColor.stripColor(e.getLine(0)).equalsIgnoreCase(ChestAdapter.LOCK_LABEL))
			return;
		final var oppositeFace = ((Directional) e.getBlock().getBlockData()).getFacing().getOppositeFace();
		final var blockBehindSign = e.getBlock().getRelative(oppositeFace);
		if (!(blockBehindSign.getState() instanceof Chest))
			return;
		Utilities.sendMsg(e.getPlayer(), "&cYou must use /lockchest command to lock this chest with a sign!");
		e.setCancelled(true);
		e.getBlock().breakNaturally();
	}

	@EventHandler(ignoreCancelled = true)
	private void onSignBreak(final BlockBreakEvent e) {
		final var block = e.getBlock();
		if (!(block.getState() instanceof Sign))
			return;
		final var player = e.getPlayer();
		final var sign = ((Sign) block.getState());
		if (!ChatColor.stripColor(sign.getLine(0)).equalsIgnoreCase(ChestAdapter.LOCK_LABEL))
			return;
		final var owners = ChestAdapter.getSignOwners(sign);
		if (!player.isSneaking()) {
			e.setCancelled(true);
			Utilities.sendMsg(player, "&cYou must sneak to break a lock sign!");
			return;
		}
		if (player.isOp() && !owners.get(0).equalsIgnoreCase(player.getName())) {
			Utilities.sendMsg(player, "&aLock removed! &e(Bypassed because you are a server operator)");
		} else if (owners.get(0).equalsIgnoreCase(player.getName())) {
			Utilities.sendMsg(player, "&aLock removed!");
		} else if (owners.stream().anyMatch(s -> s.equalsIgnoreCase(player.getName()))) {
			e.setCancelled(true);
			Utilities.sendMsg(player, "&cYou are not the lock owner! Ask &e" + owners.get(0) + "&c to break the lock!");
		} else {
			e.setCancelled(true);
			Utilities.sendMsg(player, "&cYou don't own this lock!");
		}
	}

	@EventHandler(ignoreCancelled = true)
	private void preventUnauthorizedAccess(final PlayerInteractEvent e) {
		if (!(e.getClickedBlock().getState() instanceof Chest))
			return;
		final var player = e.getPlayer();
		final var chestAdapted = new ChestAdapter(((Chest) e.getClickedBlock().getState()));
		final var owners = chestAdapted.isChestLocked();
		if (owners.isEmpty())
			return;
		final var isOwner = owners.stream().anyMatch(s -> s.equalsIgnoreCase(player.getName()));
		if (player.isOp()) {
			return;
		}
		if (!isOwner) {
			Utilities.sendMsg(player, "&cYou don't have access to this locked chest!");
			e.setCancelled(true);
		}
	}

	@EventHandler
	private void preventChestBreakFromTrusted(final BlockBreakEvent e) {
		final var block = e.getBlock();
		if (!(e.getBlock().getState() instanceof Chest))
			return;
		final var player = e.getPlayer();
		final var chestAdapted = new ChestAdapter(((Chest) block.getState()));
		final var owners = chestAdapted.isChestLocked();
		if (owners.isEmpty())
			return;
		final var isLockOwner = owners.get(0).equalsIgnoreCase(player.getName());
		if (!isLockOwner) {
			Utilities.sendMsg(player, "&cThis chest is locked by &e" + owners.get(0) + "&c!");
			e.setCancelled(true);
		}

	}

	@EventHandler(ignoreCancelled = true)
	private void preventHopperStealing(final InventoryMoveItemEvent e) {
		if (e.getSource().getLocation() == null) return;
		final var block = e.getSource().getLocation().getBlock();
		if (!(block.getState() instanceof Chest))
			return;
		final var chestAdapted = new ChestAdapter(((Chest) block.getState()));
		if (chestAdapted.isChestLocked().size() != 0)
			e.setCancelled(true);
	}


}
