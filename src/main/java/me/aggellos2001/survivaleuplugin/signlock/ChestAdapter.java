package me.aggellos2001.survivaleuplugin.signlock;

import me.aggellos2001.survivaleuplugin.modules.PlayerFinder;
import me.aggellos2001.survivaleuplugin.utils.Utilities;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Player;
import org.bukkit.inventory.DoubleChestInventory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

public class ChestAdapter {

	public static String LOCK_LABEL = "[Locked]";
	public static String LOCK_LABEL_COLORIZED = "&b[&aLocked&b]";

	private final Chest chest;

	public ChestAdapter(final Chest chest) {
		this.chest = chest;
	}

	@SuppressWarnings("ConstantConditions")
	public ChestAdapter getLeftSide() {
		if (!(this.chest.getInventory() instanceof DoubleChestInventory))
			return null;
		var doubleChest = ((DoubleChestInventory) this.chest.getInventory());
		return new ChestAdapter(((Chest) doubleChest.getLeftSide().getLocation().getBlock().getState()));
	}

	@SuppressWarnings("ConstantConditions")
	public ChestAdapter getRightSide() {
		if (!(this.chest.getInventory() instanceof DoubleChestInventory))
			return null;
		var doubleChest = ((DoubleChestInventory) this.chest.getInventory());
		return new ChestAdapter(((Chest) doubleChest.getRightSide().getLocation().getBlock().getState()));
	}

	public Location getLocation() {
		return this.chest.getLocation();
	}

	public List<Block> getBlocksAroundChest() {
		final var result = new ArrayList<Block>();

		result.add(this.chest.getBlock().getRelative(BlockFace.EAST));
		result.add(this.chest.getBlock().getRelative(BlockFace.WEST));
		result.add(this.chest.getBlock().getRelative(BlockFace.NORTH));
		result.add(this.chest.getBlock().getRelative(BlockFace.SOUTH));

		return result;
	}

	public void addLockSign(final Player player, final String... morePlayers) {
		if (!Utilities.canBuildAt(player, chest.getLocation())) {
			Utilities.sendMsg(player, "&cYou cannot lock a chest if you can't break it!");
			return;
		}
		if (morePlayers.length > 2) {
			Utilities.sendMsg(player, "&cYou can add only up to 2 players!");
			return;
		}
		if (this.isChestLocked().size() != 0) {
			Utilities.sendMsg(player, "&cChest is locked by &e" + String.join(", ", this.isChestLocked()) + "&c!");
			return;
		}
		BlockFace faceToPlace = player.getFacing().getOppositeFace();
		var faceBlock = this.chest.getBlock().getRelative(faceToPlace);
		if (faceBlock.getType() != Material.AIR) return;
		else faceBlock.setType(Material.OAK_WALL_SIGN);
		final var sign = (Sign) this.chest.getBlock().getRelative(faceToPlace).getState();
		sign.setLine(0, Utilities.colorize(LOCK_LABEL_COLORIZED));
		sign.setLine(1, player.getName());
		var index = 2;
		for (final String extraPlayer : morePlayers) {
			if (!PlayerFinder.existsPlayerWithName(extraPlayer)) {
				Utilities.sendMsg(player, "&cPlayer &e" + extraPlayer + "&c does not exist!");
				this.chest.getBlock().getRelative(faceToPlace).setType(Material.AIR);
				return;
			}
			sign.setLine(index++, extraPlayer);
		}
		final var direction = ((Directional) sign.getBlock().getBlockData());
		direction.setFacing(faceToPlace);
		sign.setBlockData(direction);
		sign.update();
		Utilities.sendMsg(player, "&aYou locked this chest successfully!");
	}

	public ArrayList<String> isChestLocked() {
		var result = new ArrayList<String>();
		if (!(this.chest.getInventory() instanceof DoubleChestInventory)) {
			final var blocksAround = this.getBlocksAroundChest();
			for (final Block block : blocksAround) {
				if (!(block.getState() instanceof Sign)) continue;
				final var sign = ((Sign) block.getState());
				final var owners = this.getSignOwners(sign);
				if (owners.size() != 0)
					result.addAll(owners);
			}
		} else {
			var blocksAroundRight = this.getRightSide().getBlocksAroundChest();
			for (final Block block : blocksAroundRight) {
				if (!(block.getState() instanceof Sign)) continue;
				final var sign = ((Sign) block.getState());
				final var owners = ChestAdapter.getSignOwners(sign);
				if (owners.size() != 0)
					result.addAll(owners);
			}
			var blocksAroundLeft = this.getLeftSide().getBlocksAroundChest();
			for (final Block block : blocksAroundLeft) {
				if (!(block.getState() instanceof Sign)) continue;
				final var sign = ((Sign) block.getState());
				final var owners = ChestAdapter.getSignOwners(sign);
				if (owners.size() != 0)
					result.addAll(owners);
			}
		}
		Utilities.removeDuplicates(result);
		return result;
	}

	public static ArrayList<String> getSignOwners(final Sign sign) {
		final var result = new ArrayList<String>();
		if (!ChatColor.stripColor(sign.getLine(0)).equalsIgnoreCase(LOCK_LABEL))
			return result;
		for (final String line : sign.getLines()) {
			if (PlayerFinder.existsPlayerWithName(line)) {
				result.add(line);
			}
		}
		Utilities.removeDuplicates(result);
		return result;
	}
}
