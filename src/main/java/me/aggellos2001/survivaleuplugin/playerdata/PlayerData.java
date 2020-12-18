package me.aggellos2001.survivaleuplugin.playerdata;

import me.aggellos2001.survivaleuplugin.SurvivalEUPlugin;
import org.bukkit.entity.Player;

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

	private static final Map<UUID, PlayerData> PLAYER_DATA = new HashMap<>();

	protected static final PlayerData DEFAULT = new PlayerData();

	protected static final File Directory = new File(Path.of(SurvivalEUPlugin.instance.getDataFolder() + File.separator + "playerData").toUri());

	public boolean keepingInventory;
	public boolean sittingOnStairs;
	public boolean pvp;
	public int supportPIN;
	public String chatColor;


	//no arg constructor for Gson
	private PlayerData() {
		this.keepingInventory = true;
		this.sittingOnStairs = false;
		this.pvp = false;
		this.supportPIN = 0;
		this.chatColor = "f"; //white default chat color
	}


	public static PlayerData getPlayerData(final Player player) {
		return PLAYER_DATA.getOrDefault(player.getUniqueId(), PlayerData.DEFAULT);
	}

	public static void updatePlayerData(final Player player, final PlayerData data) {
		PLAYER_DATA.put(player.getUniqueId(), data);
	}

	public static void removePlayerData(final Player player) {
		PLAYER_DATA.remove(player.getUniqueId());
	}
}
