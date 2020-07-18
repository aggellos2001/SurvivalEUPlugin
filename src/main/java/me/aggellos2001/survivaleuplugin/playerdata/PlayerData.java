package me.aggellos2001.survivaleuplugin.playerdata;

import me.aggellos2001.survivaleuplugin.SurvivalEUPlugin;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Data class for the plugin to store some info
 * Using Gson to serialize this in {@link PlayerDataEvent}
 */
public final class PlayerData {

	protected static final Map<UUID, PlayerData> PLAYER_DATA = new HashMap<>();

	protected static final PlayerData DEFAULT = new PlayerData();

	protected static final File Directory = new File(Path.of(SurvivalEUPlugin.instance.getDataFolder() + File.separator + "playerData").toUri());

	private boolean keepingInventory;
	private boolean sittingOnStairs;


	//no arg constructor for Gson
	private PlayerData() {
		this.keepingInventory = true;
		this.sittingOnStairs = false;
	}

	public boolean isKeepingInventory() {
		return this.keepingInventory;
	}

	public void setKeepingInventory(final boolean keepingInventory) {
		this.keepingInventory = keepingInventory;
	}

	public boolean isSittingOnStairs() {
		return this.sittingOnStairs;
	}

	public void setSittingOnStairs(final boolean sittingOnStairs) {
		this.sittingOnStairs = sittingOnStairs;
	}

	public static Map<UUID, PlayerData> getPlayerData() {
		return PLAYER_DATA;
	}
}
