package me.aggellos2001.survivaleuplugin.playerdata;

import me.aggellos2001.survivaleuplugin.utils.PluginActivity;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;

import java.io.*;
import java.util.HashSet;
import java.util.Set;


public class PlayerWarp extends PluginActivity implements Serializable {

	//TODO Use Gson in feature and also make it async?

	private final transient static Set<PlayerWarp> playerWarps = new HashSet<>(); //loads from file on startup

	//serialized fields
	private static final long serialVersionUID = 7095745514952482489L;
	private final String warpName;
	private final String player;
	private final String world;
	private final double x;
	private final double y;
	private final double z;

	public static void addWarp(final PlayerWarp warp) {
		playerWarps.add(warp);
	}

	public static void removeWarp(final PlayerWarp warps) {
		playerWarps.remove(warps);
	}

	public PlayerWarp(final String warpName, final String player, final Location warpLocation) {
		this.player = player;
		this.x = warpLocation.getX();
		this.y = warpLocation.getY();
		this.z = warpLocation.getZ();
		this.world = warpLocation.getWorld().getName();
		this.warpName = warpName;
	}

	public static Set<PlayerWarp> getPlayerWarps() {
		return playerWarps;
	}

	public static PlayerWarp getWarpByName(final String warpName) {
		for (final PlayerWarp playerWarp : playerWarps) {
			if (playerWarp.warpName.equalsIgnoreCase(warpName)) {
				return playerWarp;
			}
		}
		return null;
	}

	public String getPlayerName() {
		return this.player;
	}

	public Location getWarpLocation() {
		return new Location(Bukkit.getWorld(this.world), this.x, this.y, this.z);
	}

	public String getWarpName() {
		return this.warpName;
	}


	public static void loadPlayerWarps() {
		final var file = new File(PlayerDataEvent.Directory, "playerWarps.dat");
		if (!file.exists() || file.length() == 0) return;
		try (var objectInputStream = new ObjectInputStream(new FileInputStream(file))) {
			do {
				playerWarps.add(((PlayerWarp) objectInputStream.readObject()));
			} while (true);
		} catch (final Exception ignore) {
			// infinite loop above will exit once all objects are read so we ignore this exception.
		}
	}

	@EventHandler
	public static void savePlayerWarps() {
		final var file = new File(PlayerDataEvent.Directory, "playerWarps.dat");
		try (var objectOutputStream = new ObjectOutputStream(new FileOutputStream(file))) {
			for (final PlayerWarp playerWarp : playerWarps) {
				objectOutputStream.writeObject(playerWarp);
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return "Warp name: " + this.warpName + ", owningPlayer: " + this.player + ", x:" + this.x + ", y: " + this.y + ", z: " + this.z;
	}
}
